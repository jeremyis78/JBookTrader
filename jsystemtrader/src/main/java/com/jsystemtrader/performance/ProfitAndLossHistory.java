package com.jsystemtrader.performance;

import java.util.*;

/**
 * Holds P&L history.
 */
public class ProfitAndLossHistory {
    private final List<ProfitAndLoss> history;

    public ProfitAndLossHistory() {
        this.history = new ArrayList<ProfitAndLoss> ();
    }

    /**
     * Synchronized so that the strategy performance chart can be accessed while
     * the priceBars are still being added to the priceBar history.
     */
    synchronized public void add(ProfitAndLoss profitAndLoss) {
        history.add(profitAndLoss);
    }

    public List<ProfitAndLoss> getHistory() {
        return history;
    }

}
