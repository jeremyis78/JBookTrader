package com.jsystemtrader.platform;

import java.text.*;
import java.util.*;

import com.ib.client.*;

/**
 * Runs a trading strategy. There is a one-to-one map between the strategy class
 * and the strategy runner. That is, if 5 strategies are selected to run,
 * there will be 5 instances of the StrategyRunner created.
 */
public class StrategyRunner extends Thread {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private final Trader trader;
    private final Strategy strategy;
    private final HTMLLog eventlogger;
    private QuoteHistory qh;

    public StrategyRunner(Strategy strategy) throws JSystemTraderException {
        this.strategy = strategy;
        eventlogger = Account.getLogger();
        trader = Account.getTrader();
        trader.getAssistant().addStrategy(strategy);
        qh = trader.getAssistant().getQuoteHistoryForStrategy(strategy.getID());
        strategy.setQuoteHistory(qh);

        eventlogger.write("Started monitoring " + strategy.getName(), "Info", 1);
        Account.fireModelChanged(ModelListener.STRATEGY_ADDED, strategy);

        start(); // kick off the thread right from the constructor
    }

    private void getMarketData() {
        new TickCompressor(strategy);
        Contract contract = strategy.getContract();
        int strategyID = strategy.getID();
        trader.getAssistant().getMarketData(strategyID, contract);
    }

    private void getHistoricalData() throws InterruptedException, JSystemTraderException {

        String duration = strategy.getDuration();
        int onlyRTHPriceBars = strategy.getOnlyRTHPriceBars() ? 1 : 0;
        String barSize = PriceBar.barSizeToString(strategy.getBarSize());
        Contract contract = strategy.getContract();
        int strategyID = strategy.getID();
        String timeNow;

        Calendar cal = strategy.getCalendar();

        synchronized (dateFormat) {
            timeNow = dateFormat.format(cal.getTime());
        }

        String exchange = contract.m_exchange;
        boolean isForex = exchange.equalsIgnoreCase("IDEAL") || exchange.equalsIgnoreCase("IDEALPRO");
        String priceType = isForex ? "MIDPOINT" : "TRADES";

        trader.getAssistant().getHistoricalData(strategyID, contract, timeNow, duration, barSize, priceType,
                                                onlyRTHPriceBars, 2);
        synchronized (trader) {
            while (!qh.getIsHistRequestCompleted()) {
                // wait until the entire price bar history is returned
                trader.wait();
            }
        }
    }

    public void run() {
        try {
            strategy.init();
            PositionManager positionManager = strategy.getPositionManager();
            int quoteHistoryEvent;

            if (!strategy.getTradingSchedule().isTimeToTrade()) {
                Account.getLogger().write(strategy.getName() + ": Waiting for the start of the first trading interval.",
                                          "Info", 1);
                strategy.getTradingSchedule().waitForFirstTradingInterval();
                Account.getLogger().write(strategy.getName() + ": Trading interval started.", "Info", 1);
            }

            getHistoricalData();
            getMarketData();

            while (strategy.getDecision() != Strategy.DECISION_EXIT) {
                synchronized (qh) {
                    while (qh.getEvent() == QuoteHistory.EVENT_NONE) {
                        qh.wait();
                    }
                }

                quoteHistoryEvent = qh.getEvent();
                qh.resetEvent();
                strategy.makeDecision(quoteHistoryEvent);

                boolean hasTraded = positionManager.trade(strategy.getDecision());

                if (hasTraded || quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR) {
                    positionManager.updatePosition();
                    strategy.updateState();
                    Account.fireModelChanged(ModelListener.TRADING_DECISION, strategy);
                }

            }

            eventlogger.write(strategy.getName() + ": is now inactive.", "Info", 1);

        } catch (Throwable t) {
            /* Exceptions should never happen. If an exception of any type
             * occurs, it would indicate a serious JSystemTrader bug, and there
             * is nothing we can do to recover at runtime. Log the error for the
             * "after-run" analysis.
             */
            eventlogger.write(t);
        }
    }
}
