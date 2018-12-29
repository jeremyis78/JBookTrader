package com.jsystemtrader.strategy;

import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.indicator.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

public class OptimizedRoC extends Strategy {
    private final int longPeriodLength = 72, shortPeriodLength = 36;
    private final IndicatorHistory rateOfChangeLongHistory, rateOfChangeShortHistory;
    private double rateOfChangeLong, rateOfChangeShort;


    public OptimizedRoC() throws JSystemTraderException {
        HEADERS = new String[] {"Total<br>Realized P&L", "Trades", "Last", "Position", "Avg Fill Price",
                  "This Trade<br>Realized P&L", "RoC-Long", "RoC-Short"};

        Contract contract = new Contract();
        contract.m_secType = "FUT";
        contract.m_exchange = "GLOBEX";
        contract.m_symbol = "ES";
        contract.m_expiry = MostLiquidContract.getMostLiquid();

        int minimumQuoteHistorySize = longPeriodLength + 1;

        setStrategy(contract, minimumQuoteHistorySize, PriceBar.BAR_5_MINUTE, false, 1);

        /*
         Create indicator histories, so that they can be shown on the
         strategy performance chart for the subsequent analysis.
         */
        int subChart = 1; // separate subchart from the price chart
        rateOfChangeLongHistory = new IndicatorHistory("RoC-Long", subChart);
        rateOfChangeShortHistory = new IndicatorHistory("RoC-Short", subChart);

        addIndicatorHistory(rateOfChangeLongHistory);
        addIndicatorHistory(rateOfChangeShortHistory);

        addTradingInterval("9:40", "15:55", 5);
    }

    public void makeDecision(int quoteHistoryEvent) {
        // First, let the super strategy decide if we can trade at all
        super.makeDecision(quoteHistoryEvent);

        // make decision when a new bar is added to quote history
        if (quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR) {
            if (decision == DECISION_NONE) { // the super strategy has no objections
                if (rateOfChangeLong > 0 && rateOfChangeShort > 0) {
                    decision = DECISION_LONG;
                } else if (rateOfChangeLong < 0 && rateOfChangeShort < 0) {
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
        msg += "RoC-Long: " + nf4.format(rateOfChangeLong) + " RoC-Short: " + nf4.format(rateOfChangeShort) + "<br>";
        eventLog.write(msg, "Info", 1);

        List<Object> columns = new ArrayList<Object> ();
        PositionManager positionManager = getPositionManager();
        columns.add(nf4.format(positionManager.getTotalProfitAndLoss()));
        columns.add(positionManager.getTrades());
        columns.add(quoteHistory.getLastPriceBar().getClose());
        columns.add(positionManager.getPositionAsString());
        columns.add(positionManager.getAvgFillPrice());
        columns.add(nf4.format(positionManager.getProfitAndLoss()));
        columns.add(nf4.format(rateOfChangeLong));
        columns.add(nf4.format(rateOfChangeShort));
        strategyLog.write(columns, getCalendar(), "Info", 1);
    }

    /**
     * Called from the super class
     */
    public void updateIndicators() {
        PriceBar lastPriceBar = quoteHistory.getLastPriceBar();
        long date = lastPriceBar.getDate();

        // calculate long and short rate of change indicators
        ROC longROC = new ROC(date, quoteHistory, longPeriodLength);
        ROC shortROC = new ROC(date, quoteHistory, shortPeriodLength);

        rateOfChangeLong = longROC.calculate();
        rateOfChangeShort = shortROC.calculate();

        rateOfChangeLongHistory.addIndicator(longROC);
        rateOfChangeShortHistory.addIndicator(shortROC);
    }
}
