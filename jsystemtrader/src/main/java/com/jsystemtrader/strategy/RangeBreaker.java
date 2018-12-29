package com.jsystemtrader.strategy;

import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.indicator.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * This sample strategy trades Dow E-Mini futures using a range breakout system.
 * When the last bar high is above the 180-minute period high, it buys.
 * When the last bar low is below the 180-minute period low, it sells short.
 */
public class RangeBreaker extends Strategy {

    private final int periodLength = 180;
    private final IndicatorHistory periodLowHistory, periodHighHistory;
    private double periodHigh, periodLow;
    private boolean isHighBroken, isLowBroken;

    public RangeBreaker() throws JSystemTraderException {
        HEADERS = new String[] {"Total<br>Realized P&L", "Trades", "Close", "Position", "Avg Fill Price",
                  "This Trade<br>Realized P&L", "Period High", "Period Low"};

        Contract contract = new Contract();
        contract.m_secType = "FUT";
        contract.m_exchange = "ECBOT";
        contract.m_symbol = "YM";
        contract.m_expiry = MostLiquidContract.getMostLiquid();

        int minimumQuoteHistorySize = periodLength + 2;
        setStrategy(contract, minimumQuoteHistorySize, PriceBar.BAR_1_MINUTE, false, 1);

        /*
         Create indicator histories, so that they can be shown on the
         strategy performance chart for the subsequent analysis.
         */
        int subChart = 0; // same subchart as the price chart
        periodLowHistory = new IndicatorHistory("PeriodLow", subChart);
        periodHighHistory = new IndicatorHistory("PeriodHigh", subChart);
        addIndicatorHistory(periodLowHistory);
        addIndicatorHistory(periodHighHistory);

        // see javadocs for the TradingInterval class
        addTradingInterval("9:35", "15:55", 15);
    }

    public void makeDecision(int quoteHistoryEvent) {
        // First, let the super strategy decide if we can trade at all
        super.makeDecision(quoteHistoryEvent);

        // make decision when a new bar is added to quote history
        if (quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR) {
            if (decision == DECISION_NONE) { // the super strategy has no objections
                if (isHighBroken) {
                    decision = DECISION_LONG;
                }
                if (isLowBroken) {
                    decision = DECISION_SHORT;
                }
            }
        }

        // make decision when a TICK is updated in quote history
        if (quoteHistoryEvent == QuoteHistory.EVENT_NEW_TICK) {
            double lastTick = quoteHistory.getLast();
            // ...
        }

    }

    /**
     * Instance of NumberFormat is shared by multiple threads,
     * so the access must be synchronized.
     */
    public void updateState() {
        String msg = this.getName() + ": state updated" + "<br>";
        msg += "Last PriceBar:  " + quoteHistory.getLastPriceBar() + "<br>";
        msg += "periodHigh: " + nf4.format(periodHigh) + " periodLow: " + nf4.format(periodLow) + "<br>";
        eventLog.write(msg, "Info", 1);

        List<Object> columns = new ArrayList<Object> ();
        PositionManager positionManager = getPositionManager();
        columns.add(nf4.format(positionManager.getTotalProfitAndLoss()));
        columns.add(positionManager.getTrades());
        columns.add(quoteHistory.getLastPriceBar().getClose());
        columns.add(positionManager.getPositionAsString());
        columns.add(positionManager.getAvgFillPrice());
        columns.add(nf4.format(positionManager.getProfitAndLoss()));
        columns.add(nf4.format(periodHigh));
        columns.add(nf4.format(periodLow));
        strategyLog.write(columns, getCalendar(), "Info", 1);
    }

    /**
     * Called from the super class
     */
    public void updateIndicators() {
        int lastBar = quoteHistory.size() - 1;

        long date = quoteHistory.getLastPriceBar().getDate();

        // Have the period high or period low been broken?
        int periodEnd = lastBar - 1;
        int periodStart = periodEnd - periodLength;

        PeriodHigh periodHigh = new PeriodHigh(date, quoteHistory, periodStart, periodEnd);
        PeriodLow periodLow = new PeriodLow(date, quoteHistory, periodStart, periodEnd);

        double lastHigh = quoteHistory.getLastPriceBar().getHigh();
        isHighBroken = (lastHigh > periodHigh.calculate());

        double lastLow = quoteHistory.getLastPriceBar().getLow();
        isLowBroken = (lastLow < periodLow.calculate());

        periodLowHistory.addIndicator(periodLow);
        periodHighHistory.addIndicator(periodHigh);
    }
}
