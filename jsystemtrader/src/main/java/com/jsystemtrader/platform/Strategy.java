package com.jsystemtrader.platform;

import java.io.*;
import java.text.*;
import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.client.*;
import com.jsystemtrader.indicator.*;

/**
 * Base class for all classes that implement trading strategies.
 */
public abstract class Strategy {

    protected String[] HEADERS;

    // trading decisions
    public static final int DECISION_LONG = 0;
    public static final int DECISION_SHORT = 1;
    public static final int DECISION_FLAT = 2;

    /** indicates end of trading */
    public static final int DECISION_EXIT = 3;

    /** super strategy will make this decision if priceBar history is invalid or if
     * the current time is outside of the defined trading intervals
     */
    public static final int DECISION_DO_NOT_TRADE = 4;

    /** the specific strategy can decide what to do */
    public static final int DECISION_NONE = 5;


    private static boolean isBackTest, onlyRTHPriceBars;
    private Calendar backTestCalendar;


    protected QuoteHistory quoteHistory;
    private Contract contract;

    private int requiredHistorySize;
    protected int decision, ID;

    protected DecimalFormat nf2, nf4;
    protected HTMLLog strategyLog, eventLog;

    protected Trader trader;
    private String duration;
    private int barSize, barSizeInSecs;
    private static Calendar lastHistoricalDate;

    private String name = getClass().getSimpleName();

    private final TradingSchedule tradingSchedule;
    private final List<IndicatorHistory> indicators;
    private final List<OrderStatus> executions;
    private static int backTestMaxTrades;
    private boolean hasNewTick;
    private final PositionManager positionManager;


    abstract public void updateIndicators(); // must be implemented in the subclass

    abstract public void updateState(); // must be implemented in the subclass


    public Strategy() {

        tradingSchedule = new TradingSchedule(this);
        indicators = new ArrayList<IndicatorHistory> ();
        executions = new ArrayList<OrderStatus> ();

        positionManager = new PositionManager(this);
        nf2 = (DecimalFormat) NumberFormat.getNumberInstance();
        nf2.setMaximumFractionDigits(2);

        nf4 = (DecimalFormat) NumberFormat.getNumberInstance();
        nf4.setMaximumFractionDigits(4);

        decision = DECISION_NONE;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" " + name + " [");
        Contract contract = getContract();
        sb.append(contract.m_symbol + "-");
        sb.append(contract.m_secType + "-");
        sb.append(contract.m_exchange + "-");
        sb.append(PriceBar.barSizeToString(barSize) + "]");

        return sb.toString();
    }

    public boolean getHasNewTick() {
        return hasNewTick;
    }

    public void setHasNewTick(boolean hasNewTick) {
        this.hasNewTick = hasNewTick;
    }


    public static void setLastHistoricalDate(Calendar lastHistDate) {
        Strategy.lastHistoricalDate = lastHistDate;
    }


    public PositionManager getPositionManager() {
        return positionManager;
    }

    public TradingSchedule getTradingSchedule() {
        return tradingSchedule;
    }

    public void addTradingInterval(String openTime, String closeTime, int graceTime) throws JSystemTraderException {
        tradingSchedule.addTradingInterval(openTime, closeTime, graceTime);
    }


    protected void addIndicatorHistory(IndicatorHistory ih) {
        indicators.add(ih);
    }

    public List<IndicatorHistory> getIndicators() {
        return indicators;
    }

    public List<OrderStatus> getExecutions() {
        return executions;
    }


    public void setStrategy(Contract contract, int requiredHistorySize, int barSize, boolean onlyRTHPriceBars,
                            int quantityToTrade) throws JSystemTraderException {
        this.contract = contract;
        this.requiredHistorySize = requiredHistorySize;
        this.barSize = barSize;
        this.onlyRTHPriceBars = onlyRTHPriceBars;
        this.positionManager.setQuantityToTrade(quantityToTrade);
        this.barSizeInSecs = PriceBar.barSizeToSeconds(this.barSize);

        duration = requiredHistorySize * barSizeInSecs + " S";
    }


    public void init() throws IOException {
        trader = Account.getTrader();
        QuoteHistory qh = trader.getAssistant().getQuoteHistoryForStrategy(getID());
        setQuoteHistory(qh);

        backTestCalendar = Calendar.getInstance();

        String fileSep = System.getProperty("file.separator");
        strategyLog = new HTMLLog(1, JSystemTrader.getAppPath() + fileSep + "log" + fileSep + name + ".htm");
        strategyLog.writeHeaders(HEADERS);
        eventLog = Account.getLogger();
    }


    public void setQuoteHistory(QuoteHistory quoteHistory) {
        this.quoteHistory = quoteHistory;
    }

    public QuoteHistory getQuoteHistory() {
        return quoteHistory;
    }


    public static void setIsBackTest(boolean isBackTest) {
        Strategy.isBackTest = isBackTest;
    }

    public Calendar getCalendar() {
        return isBackTest ? backTestCalendar : Calendar.getInstance();
    }


    public static void setBackTestMaxTrades(int backTestMaxTrades) {
        Strategy.backTestMaxTrades = backTestMaxTrades;
    }

    public int getBarSize() {
        return barSize;
    }

    public int getBarSizeInSecs() {
        return barSizeInSecs;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public int getID() {
        return ID;
    }

    public Contract getContract() {
        return contract;
    }

    public String getDuration() {
        return duration;
    }

    public boolean getOnlyRTHPriceBars() {
        return onlyRTHPriceBars;
    }


    public int getDecision() {
        return decision;
    }

    public String getName() {
        String name = getClass().getSimpleName();
        return name;
    }

    public String getTicker() {
        return contract.m_symbol;
    }

    public PriceBar getLastPriceBar() {
        return quoteHistory.getLastPriceBar();
    }

    public int getRequiredHistorySize() {
        return requiredHistorySize;
    }


    public void makeDecision(int quoteHistoryEvent) {

        // Initialize every time, so that the previously made decision doesn't not carry over
        decision = DECISION_NONE;

        // before any decision is made, update the indicators
        boolean isQuoteHistoryValid = quoteHistory.isValid();
        if (isQuoteHistoryValid && (quoteHistoryEvent == QuoteHistory.EVENT_NEW_BAR)) {
            updateIndicators();
        }

        //boolean timeToExit = tradingSchedule.afterLastInterval();
        //if (timeToExit && position == POSITION_NONE) {
        //    decision = DECISION_EXIT;
        //    msg = "<b>Out of the last trading interval. End of the strategy run.</b><br>";
        //    return;
        //}

        if (isBackTest) {
            if (positionManager.getTrades() == backTestMaxTrades) {
                decision = DECISION_EXIT;
                String msg = "<b>End of strategy run after " + backTestMaxTrades + " trades.</b><br>";
                eventLog.write(getName() + ": " + msg, "Info", 1);
                return;
            }
            Calendar now = getCalendar();
            if (now.equals(lastHistoricalDate)) {
                decision = DECISION_EXIT;
                String msg = "<b>End of historical back test.</b><br>";
                eventLog.write(getName() + ": " + msg, "Info", 1);
                return;
            }
        }

        boolean timeToClose = tradingSchedule.isTimeToClose();
        if (timeToClose && (positionManager.getPosition() != PositionManager.POSITION_NONE)) {
            decision = DECISION_FLAT;
            String msg = "End of trading interval. Closing current position.";
            eventLog.write(getName() + ": " + msg, "Info", 1);
            return;
        }

        boolean timeToTrade = tradingSchedule.isTimeToTrade();
        if (!timeToTrade) {
            decision = DECISION_DO_NOT_TRADE;
            String msg =
                    "Out of the trading interval. No new positions will be taken until the start of the next trading interval.<br>";
            eventLog.write(getName() + ": " + msg, "Info", 1);
            return;
        }

        // If we are here, we can trade, unless the priceBar history is invalid.
        if (!isQuoteHistoryValid) {
            decision = DECISION_DO_NOT_TRADE;
            String msg = name + ": PriceBar history is invalid: " + quoteHistory.getValidationMessages();
            eventLog.write(getName() + ": " + msg, "Info", 1);
        }
    }
}
