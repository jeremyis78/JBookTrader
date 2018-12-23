package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbar.Snapshot;
import com.jbooktrader.platform.model.*;
import com.jbooktrader.platform.util.format.*;

import java.io.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Writes historical market data to a file which is used for
 * back testing and optimization of trading strategies.
 *
 * @author Eugene Kononov
 */
public class BackTestFileWriter {
    private final DecimalFormat decimalFormat;
    private final DateTimeFormatter dateFormat;
    private BackTestData backTestData;
    private PrintWriter writer;

    public BackTestFileWriter(String strategyName, TimeZone timeZone, BackTestData adapter) throws JBookTraderException {
        decimalFormat = NumberFormatterFactory.getNumberFormatter(5);
        dateFormat = DateTimeFormatter.ofPattern("MMddyy,HHmmss").withZone(timeZone.toZoneId());
        backTestData = adapter;

        String fileName = Dispatcher.getInstance().getMarketDataDir() + strategyName + ".txt";
        try {
            boolean fileExisted = new File(fileName).exists();
            writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            if (!fileExisted) {
                final String header = backTestData.getHeader(dateFormat);
                writer.println(header);
            }
        } catch (IOException ioe) {
            throw new JBookTraderException("Could not write to file " + fileName);
        }
    }


    public void write(Snapshot marketSnapshot) {
        final String dataRow = backTestData.toDataRow(marketSnapshot, dateFormat, decimalFormat);
        writer.println(dataRow);
        writer.flush();
    }

}
