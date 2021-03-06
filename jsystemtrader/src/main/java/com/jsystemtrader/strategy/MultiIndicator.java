package com.jsystemtrader.strategy;

import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.indicator.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * Demonstrates how multiple indicators can be plotted in different subcharts
 */
public class MultiIndicator extends Strategy {

    private final int maFastLength = 50, maSlowLength = 150;
    private final IndicatorHistory fastEMAHistory, slowEMAHistory, rocHistory, rsiFastHistory, rsiSlowHistory;
    private double fastEMA, slowEMA;

    public MultiIndicator() throws JSystemTraderException {
        HEADERS = new String[] {"Total<br>Realized P&L", "Trades", "Close", "Position", "Avg Fill Price",
                  "This Trade<br>Realized P&L", "fastEMA", "slowEMA"};

        Contract contract = new Contract();
        contract.m_secType = "FUT";
        contract.m_exchange = "GLOBEX";
        contract.m_symbol = "ES";
        contract.m_expiry = MostLiquidContract.getMostLiquid();

        int minimumQuoteHistorySize = 2 * maSlowLength + 1;
        setStrategy(contract, minimumQuoteHistorySize, PriceBar.BAR_1_MINUTE, false, 1);

        /*
         Create indicator histories, so that they can be shown on the
         strategy performance chart for the subsequent analysis.
         */
        int subChart = 0; // same subchart as the price chart
        fastEMAHistory = new IndicatorHistory("Fast EMA", subChart);
        slowEMAHistory = new IndicatorHistory("Slow EMA", subChart);

        subChart++; // plot the next two indicator in a separate subchart
        rsiFastHistory = new IndicatorHistory("Fast RSI", subChart);
        rsiSlowHistory = new IndicatorHistory("Slow RSI", subChart);

        subChart++; // plot the next one indicator in a separate subchart
        rocHistory = new IndicatorHistory("ROC", subChart);

        addIndicatorHistory(fastEMAHistory);
        addIndicatorHistory(slowEMAHistory);
        addIndicatorHistory(rsiFastHistory);
        addIndicatorHistory(rsiSlowHistory);
        addIndicatorHistory(rocHistory);

        // see javadocs for the TradingInterval class
        addTradingInterval("9:35", "15:55", 15);
    }

    public void makeDecision(int quoteHistoryEvent) {
        // First, let the super strategy decide if we can trade at all
        super.makeDecision(quoteHistoryEvent);

        // make decision when a new bar is added to quote history
        if (quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR) {
            if (decision == DECISION_NONE) { // the super strategy has no objections

                if (fastEMA < slowEMA) {
                    decision = DECISION_LONG;
                }
                if (fastEMA > slowEMA) {
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

        // calculate rate of change indicator
        ROC roc = new ROC(date, quoteHistory, maSlowLength);
        roc.calculate();

        // calculate fast and slow RSI indicators
        RSI fastRSI = new RSI(date, quoteHistory, maFastLength);
        RSI slowRSI = new RSI(date, quoteHistory, maSlowLength);
        fastRSI.calculate();
        slowRSI.calculate();

        fastEMAHistory.addIndicator(fastEMAInd);
        slowEMAHistory.addIndicator(slowEMAInd);
        rocHistory.addIndicator(roc);
        rsiFastHistory.addIndicator(fastRSI);
        rsiSlowHistory.addIndicator(slowRSI);
    }
}
