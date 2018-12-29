package com.jsystemtrader.indicator;

import com.jsystemtrader.platform.*;

/**
 * Exponential Moving Average.
 */
public class EMA extends Indicator {
    private final int length;
    private final double multiplier;
    private final int endBar;

    public EMA(long time, QuoteHistory qh, int length, int endBar) {
        super(time, qh);
        this.length = length;
        multiplier = 2. / (length + 1.);
        this.endBar = endBar;
    }

    public EMA(long time, QuoteHistory qh, int length) {
        this(time, qh, length, qh.size() - 1);
    }

    public double calculate() {
        int startBar = Math.max(endBar - 2 * length, 0);
        double ema = qh.getPriceBar(startBar).getClose();

        for (int bar = startBar; bar <= endBar; bar++) {
            double barClose = qh.getPriceBar(bar).getClose();
            ema += (barClose - ema) * multiplier;
        }

        value = ema;
        return value;
    }
}
