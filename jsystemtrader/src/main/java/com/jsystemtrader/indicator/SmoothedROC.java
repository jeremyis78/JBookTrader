package com.jsystemtrader.indicator;

import com.jsystemtrader.platform.*;

/**
 * Smoothed Rate of Price Change.
 */
public class SmoothedROC extends Indicator {
    private final int maLength, lookBackLength;

    public SmoothedROC(long time, QuoteHistory qh, int maLength, int lookBackLength) {
        super(time, qh);
        this.maLength = maLength;
        this.lookBackLength = lookBackLength;
    }

    public double calculate() {
        int lastBar = qh.size() - 1;
        double emaNow = new EMA(time, qh, maLength, lastBar).calculate();
        double emaThen = new EMA(time, qh, maLength, lastBar - lookBackLength).calculate();

        double smoothedRateOfChange = ( (emaNow - emaThen) / emaThen) * 100;

        value = smoothedRateOfChange;
        return value;
    }
}
