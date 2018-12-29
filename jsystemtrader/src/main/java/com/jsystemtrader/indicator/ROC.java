package com.jsystemtrader.indicator;

import com.jsystemtrader.platform.*;

/**
 * Rate of Price Change.
 */
public class ROC extends Indicator {
    private final int lookBackLength;

    public ROC(long time, QuoteHistory qh, int lookBackLength) {
        super(time, qh);
        this.lookBackLength = lookBackLength;
    }

    public double calculate() {
        double priceNow = qh.getLastPriceBar().getClose();
        double priceThen = qh.getPriceBar(qh.size() - 1 - lookBackLength).getClose();

        double rateOfChange = ( (priceNow - priceThen) / priceThen) * 100;

        value = rateOfChange;
        return value;
    }
}
