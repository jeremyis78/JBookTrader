package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbar.Snapshot;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.SortedSet;

/**
 * Represents an adapter to write back test data
 *
 * Created by jeremy on 5/13/18.
 */
public interface BackTestData<T extends Snapshot> {

    SortedSet<String> getColumnDefinitions();

    String getHeader(DateTimeFormatter dateFormat);

    String toDataRow(T snapshot, DateTimeFormatter dateFormat, DecimalFormat decimalFormat);
}
