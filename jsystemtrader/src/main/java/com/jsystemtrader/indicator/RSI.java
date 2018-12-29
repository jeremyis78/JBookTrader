package com.jsystemtrader.indicator;

import com.jsystemtrader.platform.*;


/**
 * Relative Strength Index.
 */
public class RSI extends Indicator {
    private final int periodLength;

    public RSI(long time, QuoteHistory qh, int periodLength) {
        super(time, qh);
        this.periodLength = periodLength;
    }

    public double calculate() {

        int lastBar = qh.size() - 1;
        int firstBar = lastBar - periodLength + 1;

        double aveGain = 0, aveLoss = 0;
        for (int bar = firstBar + 1; bar <= lastBar; bar++) {
            double change = qh.getPriceBar(bar).getClose() - qh.getPriceBar(bar - 1).getClose();
            if (change >= 0) {
                aveGain += change;
            } else {
                aveLoss += change;
            }
        }

        double rs = aveGain / Math.abs(aveLoss);
        double rsi = 100 - 100 / (1 + rs);

        value = rsi;
        return value;
    }
}
