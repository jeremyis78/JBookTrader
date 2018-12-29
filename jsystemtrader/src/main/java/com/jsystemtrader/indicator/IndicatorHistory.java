package com.jsystemtrader.indicator;

import java.util.*;

/**
 * Holds the history of a technical indicator.
 */
public class IndicatorHistory {
    private final List<Indicator> history;
    private final String name;
    private final int subChartNumber;

    public IndicatorHistory(String name, int subChartNumber) {
        this.name = name;
        this.subChartNumber = subChartNumber;
        this.history = new ArrayList<Indicator> ();
    }

    public String getName() {
        return name;
    }

    public int getSubChartNumber() {
        return subChartNumber;
    }

    /**
     * Synchronized so that the strategy performance chart can be accessed while
     * the priceBars are still being added to the priceBar history.
     */
    synchronized public void addIndicator(Indicator indicator) {
        history.add(indicator);
    }

    public List<Indicator> getHistory() {
        return history;
    }

}
