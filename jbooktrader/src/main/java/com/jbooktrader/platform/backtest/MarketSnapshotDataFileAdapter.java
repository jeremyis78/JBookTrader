package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbook.MarketSnapshot;
import com.jbooktrader.platform.startup.JBookTrader;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by jeremy on 5/13/18.
 */
public class MarketSnapshotDataFileAdapter implements DataFile<MarketSnapshot> {

    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String COLUMN_SEP = ",";


    public SortedSet<String> getColumnDefinitions(){
        SortedSet<String> set = new TreeSet<>();
        set.add("1. date in the MMddyy format");
        set.add("2. time in the HHmmss format");
        set.add("3. book balance");
        set.add("4. price");
        set.add("5. volume");
        return set;
    }

    public String getHeader(SimpleDateFormat dateFormat) {
        StringBuilder header = new StringBuilder();
        header.append("# This historical data file was created by ").append(JBookTrader.APP_NAME).append(LINE_SEP);
        header.append("# Each line represents a 1-second snapshot of the market containing columns: ").append(LINE_SEP);
        for(String columnDefinition: getColumnDefinitions()){
            header.append("# ").append(columnDefinition).append(LINE_SEP);
        }
        header.append(LINE_SEP);
        header.append("timeZone=").append(dateFormat.getTimeZone().getID()).append(LINE_SEP);
        return header.toString();
    }


    @Override
    public String toDataRow(MarketSnapshot snapshot, SimpleDateFormat dateFormat, DecimalFormat decimalFormat) {
        Objects.requireNonNull(snapshot);
        Objects.requireNonNull(dateFormat);
        Objects.requireNonNull(decimalFormat);

        StringBuilder sb = new StringBuilder();
        sb.append(dateFormat.format(snapshot.getTime())).append(COLUMN_SEP);
        sb.append(snapshot.getBalance()).append(COLUMN_SEP);
        sb.append(decimalFormat.format(snapshot.getPrice())).append(COLUMN_SEP);
        sb.append(snapshot.getVolume());
        return sb.toString();
    }
}
