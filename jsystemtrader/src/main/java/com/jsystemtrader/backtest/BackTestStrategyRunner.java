package com.jsystemtrader.backtest;

import java.util.*;

import com.jsystemtrader.platform.*;

/**
 * Runs a trading strategy in the backtesting mode using a data file containing
 * historical price bars. There is a one-to-one map between the strategy class
 * and the strategy runner. That is, if 5 strategies are selected to run,
 * there will be 5 instances of the StrategyRunner created.
 */
public class BackTestStrategyRunner extends Thread {
    private static Calendar lastDateInFile;

    private final Trader trader;
    private final Strategy strategy;
    private final HTMLLog eventlogger;
    private BackTestTraderAssistant backTestAssistant;

    public BackTestStrategyRunner(Strategy strategy) throws JSystemTraderException {
        this.strategy = strategy;
        eventlogger = Account.getLogger();
        trader = Account.getTrader();
        trader.getAssistant().addStrategy(strategy);

        backTestAssistant = (BackTestTraderAssistant) trader.getAssistant();

        PriceBar priceBar = backTestAssistant.priceBars.get(backTestAssistant.priceBars.size() - 1);
        priceBar.getDate();
        lastDateInFile = Calendar.getInstance();
        lastDateInFile.setTimeInMillis(priceBar.getDate());

        eventlogger.write("Started monitoring " + strategy.getName(), "Info", 1);
        Account.fireModelChanged(ModelListener.STRATEGY_ADDED, strategy);

        start(); // kick off the thread right from the constructor
    }


    public void run() {
        try {
            strategy.init();
            QuoteHistory qh = strategy.getQuoteHistory();
            PositionManager positionManager = strategy.getPositionManager();

            int currentBar = 0;
            while (strategy.getDecision() != Strategy.DECISION_EXIT) {
                PriceBar priceBar = backTestAssistant.priceBars.get(currentBar);
                qh.addHistoricalPriceBar(priceBar);

                Calendar calendar = strategy.getCalendar();
                long date = priceBar.getDate();
                calendar.setTimeInMillis(date);

                strategy.makeDecision(QuoteHistory.EVENT_NEW_BAR);
                positionManager.trade(strategy.getDecision());
                positionManager.updatePosition();
                strategy.updateState();
                Account.fireModelChanged(ModelListener.TRADING_DECISION, strategy);

                currentBar++;
            }

            eventlogger.write(strategy.getName() + ": is now inactive.", "Info", 1);

        }

        catch (Throwable t) {
            /* Exceptions should never happen. If an exception of any type
             * occurs, it would indicate a serious JSystemTrader bug, and there
             * is nothing we can do to recover at runtime. Log the error for the
             * "after-run" analysis.
             */
            eventlogger.write(t);
        }
    }
}
