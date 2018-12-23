package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbar.Snapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.SortedSet;

/**
 * Created by jeremy on 5/13/18.
 */
public interface DataFile<T extends Snapshot> {

    SortedSet<String> getColumnDefinitions();

    String getHeader(SimpleDateFormat dateFormat);

    String toDataRow(T snapshot, SimpleDateFormat dateFormat, DecimalFormat decimalFormat);
}
