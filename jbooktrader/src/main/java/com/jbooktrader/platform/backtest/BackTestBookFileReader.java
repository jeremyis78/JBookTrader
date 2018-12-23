package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbar.Snapshot;
import com.jbooktrader.platform.marketbook.*;
import com.jbooktrader.platform.model.*;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Reads and validates a data file containing historical market depth records.
 * The data file is used for back testing and optimization of trading strategies.
 *
 * @author Eugene Kononov
 */
public class BackTestBookFileReader extends BackTestFileReader {

    private String previousDateTimeWithoutSeconds;


    public BackTestBookFileReader(String fileName, SnapshotFilter filter) throws JBookTraderException {
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
        return Arrays.asList("date","time","balance","price","volume");
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

        double balance = Double.parseDouble(tokens.get(2));
        double price = Double.parseDouble(tokens.get(3));
        int volume = Integer.parseInt(tokens.get(4));
        return new MarketSnapshot(time, balance, price, volume);
    }
}
