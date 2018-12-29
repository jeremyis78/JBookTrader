package com.jsystemtrader.strategy;

import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.indicator.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * This sample strategy trades the S&P 500 E-mini futures (ES) using smoothed
 * rate of change as an indicator. When SmoothedROC is positive, it buys.
 * Otherwise, it sells short.
 */

public class SmoothedRoC extends Strategy {

    private final int maLength = 3;
    private final int lookBackLength = 65;
    private double smoothedROC;
    private final IndicatorHistory smoothedROCHistory;

    public SmoothedRoC() throws JSystemTraderException {
        HEADERS = new String[] {"Total<br>Realized P&L", "Trades", "Close", "Position", "Avg Fill Price",
                  "This Trade<br>Realized P&L", "smoothedROC"};

        Contract contract = new Contract();
        contract.m_secType = "FUT";
        contract.m_exchange = "GLOBEX";
        contract.m_symbol = "ES";
        contract.m_expiry = MostLiquidContract.getMostLiquid();

        int minimumQuoteHistorySize = maLength + lookBackLength + 1;
        setStrategy(contract, minimumQuoteHistorySize, PriceBar.BAR_5_MINUTE, false, 1);

        /*
         Create indicator histories, so that they can be shown on the
         strategy performance chart for the subsequent analysis.
         */
        int subChart = 1; // show indicator on a separate subchart
        smoothedROCHistory = new IndicatorHistory("SmoothedROC", subChart);
        addIndicatorHistory(smoothedROCHistory);

        // no trading interval defined: strategy may hold positions overnight
        //addTradingInterval("9:35", "15:55", 5);

    }

    public void makeDecision(int quoteHistoryEvent) {
        // First, let the super strategy decide if we can trade at all
        super.makeDecision(quoteHistoryEvent);

        // make decision when a new BAR is added to quote history
        if (quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR) {
            if (decision == DECISION_NONE) { // the super strategy has no objections
                if (smoothedROC > 0) {
                    decision = DECISION_LONG;
                }
                if (smoothedROC < 0) {
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
        msg += "smoothedROC: " + nf4.format(smoothedROC) + "<br>";
        eventLog.write(msg, "Info", 1);

        List<Object> columns = new ArrayList<Object> ();
        PositionManager positionManager = getPositionManager();
        columns.add(nf4.format(positionManager.getTotalProfitAndLoss()));
        columns.add(positionManager.getTrades());
        columns.add(quoteHistory.getLastPriceBar().getClose());
        columns.add(positionManager.getPositionAsString());
        columns.add(positionManager.getAvgFillPrice());
        columns.add(nf4.format(positionManager.getProfitAndLoss()));
        columns.add(nf4.format(smoothedROC));

        strategyLog.write(columns, getCalendar(), "Info", 1);
    }

    /**
     * Called from the super class
     */
    public void updateIndicators() {

        long date = quoteHistory.getLastPriceBar().getDate();
        SmoothedROC smoothedROCInd = new SmoothedROC(date, quoteHistory, maLength, lookBackLength);
        smoothedROC = smoothedROCInd.calculate();

        smoothedROCHistory.addIndicator(smoothedROCInd);
    }
}
