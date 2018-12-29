package com.jsystemtrader.strategy;

import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.indicator.*;
import com.jsystemtrader.platform.*;

/**
 * This sample strategy trades the EUR.USD cash on IDEALPRO using moving average
 * crossover as an indicator. When the fast EMA is above the slow EMA, it buys.
 * Otherwise, it sells short. This is a trend following type of a system.
 * The moving averages are calculated from the priceBar history of the 1-minute bars.
 */
public class ForexTrend extends Strategy {
    private final int maFastLength = 45, maSlowLength = 180;
    private final IndicatorHistory fastEMAHistory, slowEMAHistory;
    private double fastEMA, slowEMA;

    public ForexTrend() throws JSystemTraderException {
        HEADERS = new String[] {"Total<br>Realized P&L", "Trades", "Last", "Position", "Avg Fill Price",
                  "This Trade<br>Realized P&L", "fastEMA", "slowEMA"};

        Contract contract = new Contract();
        contract.m_secType = "CASH";
        contract.m_exchange = "IDEALPRO";
        contract.m_symbol = "EUR";
        contract.m_currency = "USD";

        int minimumQuoteHistorySize = 2 * maSlowLength + 1;
        setStrategy(contract, minimumQuoteHistorySize, PriceBar.BAR_1_MINUTE, false, 25000);

        /*
         Create indicator histories, so that they can be shown on the
         strategy performance chart for the subsequent analysis.
         */
        int subChart = 0; // same subchart as the price chart
        fastEMAHistory = new IndicatorHistory("Fast EMA", subChart);
        slowEMAHistory = new IndicatorHistory("Slow EMA", subChart);
        addIndicatorHistory(fastEMAHistory);
        addIndicatorHistory(slowEMAHistory);

        // see javadocs for the TradingInterval class
        addTradingInterval("1:00", "23:50", 5); // trades about 23 hours/day
    }

    public void makeDecision(int quoteHistoryEvent) {
        // First, let the super strategy decide if we can trade at all
        super.makeDecision(quoteHistoryEvent);

        // make decision when a new bar is added to quote history
        if (quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR) {
            if (decision == DECISION_NONE) { // the super strategy has no objections
                if (fastEMA > slowEMA) {
                    decision = DECISION_LONG;
                }
                if (fastEMA < slowEMA) {
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
        msg += "fastEMA: " + nf4.format(fastEMA) + " slowEMA: " + nf4.format(slowEMA) + "<br>";
        eventLog.write(msg, "Info", 1);

        List<Object> columns = new ArrayList<Object> ();
        PositionManager positionManager = getPositionManager();
        columns.add(nf4.format(positionManager.getTotalProfitAndLoss()));
        columns.add(positionManager.getTrades());
        columns.add(quoteHistory.getLastPriceBar().getClose());
        columns.add(positionManager.getPositionAsString());
        columns.add(positionManager.getAvgFillPrice());
        columns.add(nf4.format(positionManager.getProfitAndLoss()));
        columns.add(nf4.format(fastEMA));
        columns.add(nf4.format(slowEMA));
        strategyLog.write(columns, getCalendar(), "Info", 1);
    }

    /**
     * Called from the super class
     */
    public void updateIndicators() {
        long date = quoteHistory.getLastPriceBar().getDate();

        // calculate fast and slow EMAs
        EMA fastEMAInd = new EMA(date, quoteHistory, maFastLength);
        EMA slowEMAInd = new EMA(date, quoteHistory, maSlowLength);
        fastEMA = fastEMAInd.calculate();
        slowEMA = slowEMAInd.calculate();

        fastEMAHistory.addIndicator(fastEMAInd);
        slowEMAHistory.addIndicator(slowEMAInd);
    }
}
