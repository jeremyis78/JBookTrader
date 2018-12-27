package com.jbooktrader.platform.marketbar;

import com.jbooktrader.platform.marketdepth.MarketDepth;

/**
 * Represents some form of market data.
 *
 * To be expanded/tweaked as needed going forward.
 */
public interface MarketData {

    Snapshot getSnapshot();

    void setSnapshot(Snapshot snapshot);

    boolean isGapping(Snapshot newSnapshot);

    /**
     * True if there is no snapshot to get from getSnapshot()
     * @return
     */
    boolean isEmpty();

    boolean isExchangeOpen();

    MarketDepth getMarketDepth();
}
