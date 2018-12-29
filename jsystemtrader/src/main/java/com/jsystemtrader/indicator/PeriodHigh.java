package com.jsystemtrader.indicator;

import com.jsystemtrader.platform.*;

/**
 * Highest high of the period.
 */
public class PeriodHigh extends Indicator {
    private final int periodStart, periodEnd;

    public PeriodHigh(long time, QuoteHistory qh, int periodStart, int periodEnd) {
        super(time, qh);
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public double calculate() {
        double high = qh.getPriceBar(periodStart).getHigh();

        for (int bar = periodStart + 1; bar <= periodEnd; bar++) {
            double barHigh = qh.getPriceBar(bar).getHigh();
            if (barHigh > high) {
                high = barHigh;
            }
        }

        value = high;
        return value;
    }
}
