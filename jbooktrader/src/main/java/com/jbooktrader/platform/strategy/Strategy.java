package com.jbooktrader.platform.strategy;

import com.ib.client.Contract;
import com.jbooktrader.platform.indicator.IndicatorManager;
import com.jbooktrader.platform.marketbar.MarketData;
import com.jbooktrader.platform.performance.PerformanceManager;
import com.jbooktrader.platform.position.PositionManager;
import com.jbooktrader.platform.schedule.TradingSchedule;

/**
 * Represents a trading strategy.
 * NOTE: still in a lot of flux as I work on adding candle-based trading strategies.
 */
public interface Strategy {

    /**
     * Identifies the name of this strategy
     * @return the name of the strategy
     */
    String getName();

    /**
     * The IB-specific contract this strategy will trade.
     * @return
     */
    Contract getContract();

    /**
     * Returns the schedule on which this strategy will trade.
     * @return
     */
    TradingSchedule getTradingSchedule();

    /**
     * Gets a fixed bid-ask spread for this strategy's instrument.
     * "fixed" because we trade highly liquid instruments here.
     * Use getMarket() to access an actual bid ask spread, if it provides a bid-ask spread.
     * @return
     */
    double getBidAskSpread();

    /**
     * Returns some type of data about the market being traded with this strategy.
     * For example, an order book, the top-of-the-book quote history (bid, ask, last, etc),
     * or even some fundamental data.
     *
     * NOTE: I'm not entirely sure this should be in the interface, but for now it is.
     * Maybe it should be passed into the processInstant() method or whatever method
     * that runs the trading logic is eventually called.
     *
     * @return market data for the instrument being traded
     */
    MarketData getMarket();

    /**
     * Runs trading logic (goLong, goShort, goFlat, etc) given whether we are within our trading schedule.
     *
     * @param isInSchedule indicates we are within our trading schedule
     */
    void processInstant(boolean isInSchedule);

    /**
     * Disables this strategy
     */
    void disable();

    boolean isOkToTrade();

    void goFlat();

    void closePosition();

    StrategyReportManager getStrategyReportManager();

    PositionManager getPositionManager();

    PerformanceManager getPerformanceManager();

    void setIndicatorManager(IndicatorManager indicatorManager);

    void setIndicators();

    IndicatorManager getIndicatorManager();

}
