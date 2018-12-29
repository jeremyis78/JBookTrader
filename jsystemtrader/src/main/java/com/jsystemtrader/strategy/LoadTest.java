package com.jsystemtrader.strategy;

import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * This is a totally mindless strategy whose sole purpose is to test the system.
 * This strategy will buy and sell every one minute to maximize the number of
 * trades so that the potential JSystemTrader bugs/problems can be uncovered.
 */
public class LoadTest extends Strategy {

    public LoadTest() throws JSystemTraderException {
        HEADERS = new String[] {"Total<br>Realized P&L", "Trades", "Close", "Position", "Avg Fill Price",
                  "This Trade<br>Realized P&L"};

        Contract contract = new Contract();
        contract.m_secType = "FUT";
        contract.m_exchange = "GLOBEX";
        contract.m_symbol = "ES";
        contract.m_expiry = MostLiquidContract.getMostLiquid();

        setStrategy(contract, 10, PriceBar.BAR_1_MINUTE, false, 25000);

    }

    public void makeDecision(int quoteHistoryEvent) {
        // First, let the super strategy decide if we can trade at all
        super.makeDecision(quoteHistoryEvent);

        // make trading decision when a new bar is added to quote history
        if (quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR) {
            if (decision == DECISION_NONE) { // the super strategy has no objections
                // see class level coments for explanation of this strategy
                PositionManager positionManager = getPositionManager();
                int position = positionManager.getPosition();
                if (position == PositionManager.POSITION_NONE || position == PositionManager.POSITION_SHORT) {
                    decision = DECISION_LONG;
                }

                if (position == PositionManager.POSITION_LONG) {
                    decision = DECISION_SHORT;
                }
            }
        }

    }

    /**
     * Instance of NumberFormat is shared by multiple threads,
     * so the access must be synchronized.
     */
    public void updateState() {
        String msg = this.getName() + ": state updated" + "<br>";
        eventLog.write(msg, "Info", 1);

        List<Object> columns = new ArrayList<Object> ();
        PositionManager positionManager = getPositionManager();
        columns.add(nf4.format(positionManager.getTotalProfitAndLoss()));
        columns.add(positionManager.getTrades());
        columns.add(quoteHistory.getLastPriceBar().getClose());
        columns.add(positionManager.getPositionAsString());
        columns.add(positionManager.getAvgFillPrice());
        columns.add(nf4.format(positionManager.getProfitAndLoss()));

        strategyLog.write(columns, getCalendar(), "Info", 1);
    }

    /**
     * Called from the super class
     */
    public void updateIndicators() {
        // nothing to do
    }
}
