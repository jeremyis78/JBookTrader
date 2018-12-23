package com.jbooktrader.platform.marketbar;


/**
 * A snapshot of market data -- price, volume, and balance (if any) -- occurring at a given time.
 * <p>
 * Implementations decide what a snapshot means; if it means anything other than market depth
 * (the JBookTrader default), such as a window of time like an OHLC bar, then implementations
 * choose what price getPrice returns (close, open, etc) as well as what time getTime()
 * returns (the start of the bar, close of the bar, etc).
 *
 * Created by jeremy on 5/12/18.
 */
public interface Snapshot {

    long getTime();

    double getPrice();

    int getVolume();

    default double getBalance() {
        throw new UnsupportedOperationException("override balance if you want to use it");
    }

}
