package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbar.PriceBar;
import com.jbooktrader.platform.startup.JBookTrader;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by jeremy on 5/13/18.
 */
public class PriceBarBackTestDataAdapter implements BackTestData<PriceBar> {

    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String COLUMN_SEP = ",";


    public SortedSet<String> getColumnDefinitions(){
        SortedSet<String> set = new TreeSet<>();
        set.add("1. date in the yyyyMMdd format");
        set.add("2. time in the HHmmss format");
        set.add("3. opening price");
        set.add("4. high price");
        set.add("5. low price");
        set.add("6. closing price");
        set.add("7. volume");
        return set;
    }

    public String getHeader(DateTimeFormatter dateFormat) {
        StringBuilder header = new StringBuilder();
        header.append("# This historical data file was created by ").append(JBookTrader.APP_NAME).append(LINE_SEP);
        header.append("# Each line represents one bar of the market containing columns: ").append(LINE_SEP);
        for(String columnDefinition: getColumnDefinitions()){
            header.append("# ").append(columnDefinition).append(LINE_SEP);
        }
        header.append(LINE_SEP);
        header.append("timeZone=").append(dateFormat.getZone()).append(LINE_SEP);
        return header.toString();
    }


    @Override
    public String toDataRow(PriceBar snapshot, DateTimeFormatter dateFormat, DecimalFormat decimalFormat) {
        Objects.requireNonNull(snapshot);
        Objects.requireNonNull(dateFormat);
        Objects.requireNonNull(decimalFormat);

        StringBuilder sb = new StringBuilder();
        sb.append(dateFormat.format(Instant.ofEpochMilli(snapshot.getTime()))).append(COLUMN_SEP);
        sb.append(decimalFormat.format(snapshot.getOpen())).append(COLUMN_SEP);
        sb.append(decimalFormat.format(snapshot.getHigh())).append(COLUMN_SEP);
        sb.append(decimalFormat.format(snapshot.getLow())).append(COLUMN_SEP);
        sb.append(decimalFormat.format(snapshot.getClose())).append(COLUMN_SEP);
        sb.append(snapshot.getVolume());
        return sb.toString();
    }
}
