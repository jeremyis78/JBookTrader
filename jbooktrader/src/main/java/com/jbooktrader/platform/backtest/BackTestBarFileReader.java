package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbar.PriceBar;
import com.jbooktrader.platform.marketbar.Snapshot;
import com.jbooktrader.platform.marketbook.*;
import com.jbooktrader.platform.model.*;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Reads and validates a data file containing historical OHLC records.
 * The data file is used for back testing and optimization of trading strategies.
 *
 * @author Jeremy Brooks
 */
@SuppressWarnings("unused")
public class BackTestBarFileReader extends BackTestFileReader {

    private String previousDateTimeWithoutSeconds;


    public BackTestBarFileReader(String fileName, SnapshotFilter filter) throws JBookTraderException {
        this.filter = filter;
        previousDateTimeWithoutSeconds = "";

        cacheKey = fileName;
        if (filter != null) {
            cacheKey += "," + filter.toString();
        }

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            fileSize = new File(fileName).length();
        } catch (FileNotFoundException fnf) {
            throw new JBookTraderException("Could not find file: " + fileName);
        }
    }

    @Override
    public List<String> getColumnHeaders() {
        return Arrays.asList("date","time","open","high","low","close","volume");
    }

    @Override
    public Snapshot toSnapshot(String line) throws JBookTraderException, ParseException {
        List<String> tokens = fastSplit(line);
        final int expectedNumColumns = getColumnHeaders().size();

        if (tokens.size() != expectedNumColumns) {
            String msg = "The line should contain exactly " + expectedNumColumns + " comma-separated " +
                    "columns (" + join(getColumnHeaders(),",") + "). Found " + tokens.size() + " columns";
            throw new JBookTraderException(msg);
        }

        String dateTime = tokens.get(0) + tokens.get(1);
        String dateTimeWithoutSeconds = dateTime.substring(0, 10);

        if (dateTimeWithoutSeconds.equals(previousDateTimeWithoutSeconds)) {
            // only seconds need to be set
            int milliSeconds = 1000 * Integer.parseInt(dateTime.substring(10));
            long previousMilliSeconds = previousTime % 60000;
            time = previousTime + (milliSeconds - previousMilliSeconds);
        } else {
            time = sdf.parse(dateTime).getTime();
            previousDateTimeWithoutSeconds = dateTimeWithoutSeconds;
        }

        if (time <= previousTime) {
            String msg = "Timestamp of this line is before or the same as the timestamp of the previous line.";
            throw new JBookTraderException(msg);
        }


        double open = Double.parseDouble(tokens.get(2));
        double high = Double.parseDouble(tokens.get(3));
        double low  = Double.parseDouble(tokens.get(4));
        double close = Double.parseDouble(tokens.get(5));
        //double adjClose = Double.parseDouble(tokens.get(6));
        int volume = Integer.parseInt(tokens.get(7));

        boolean isInvalidPriceBar = (open <= 0) || (high <= 0) || (low <= 0) || (close <= 0);

        if (isInvalidPriceBar) {
            throw new JBookTraderException("Open, High, Low, and Close must be greater than zero.");
        }

        if (low > high) {
            throw new JBookTraderException("Low must be less or equal to high.");
        }

        if (close < low || close > high) {
            throw new JBookTraderException("Close must be between low and high.");
        }

        if (open < low || open > high) {
            throw new JBookTraderException("Open must be between low and high.");
        }
        return new PriceBar(time, open, high, low, close, volume);
    }
}
