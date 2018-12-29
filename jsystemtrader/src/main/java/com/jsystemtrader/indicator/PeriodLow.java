package com.jsystemtrader.indicator;

import com.jsystemtrader.platform.*;

/**
 * Lowest low of the period.
 */
public class PeriodLow extends Indicator {
    private final int periodStart, periodEnd;

    public PeriodLow(long time, QuoteHistory qh, int periodStart, int periodEnd) {
        super(time, qh);
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public double calculate() {
        double low = qh.getPriceBar(periodStart).getLow();

        for (int bar = periodStart + 1; bar <= periodEnd; bar++) {
            double barLow = qh.getPriceBar(bar).getLow();
            if (barLow < low) {
                low = barLow;
            }
        }

        value = low;
        return value;
    }
}
