package com.jsystemtrader.platform;

import java.text.*;
import java.util.*;

import com.ib.client.*;

/**
 * Holds and validates the priceBar history for a strategy.
 */
public class QuoteHistory {
    public static final int EVENT_NONE = 0;
    public static final int EVENT_NEW_TICK = 1;
    public static final int EVENT_NEW_BAR = 2;
    public static HTMLLog eventLogger = Account.getLogger();
    private static final DecimalFormat df6 = (DecimalFormat) NumberFormat.getInstance();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yy");

    private final List<PriceBar> priceBars;
    private final List<String> validationMessages;
    private final String strategyName;
    private boolean isHistRequestCompleted;
    private int requiredHistorySize;
    private double bid, ask, last;
    private long timeOfLast;
    private final Strategy strategy;
    private boolean isForex;
    private PriceBar nextBar;
    private int event;

    public QuoteHistory(Strategy strategy) {
        df6.setMaximumFractionDigits(6);

        this.strategy = strategy;
        strategyName = strategy.getName();

        requiredHistorySize = strategy.getRequiredHistorySize();
        priceBars = new ArrayList<PriceBar> ();
        validationMessages = new ArrayList<String> ();
        String exchange = strategy.getContract().m_exchange;
        isForex = exchange.equalsIgnoreCase("IDEAL") || exchange.equalsIgnoreCase("IDEALPRO");
        event = EVENT_NONE;
    }

    public int getEvent() {
        return event;
    }

    public int resetEvent() {
        return event = EVENT_NONE;
    }

    public void update(int tickType, double price) throws ParseException {

        if (price <= 0) { // guard against a bad tick
            return;
        }

        switch (tickType) {
            case TickType.BID:
                bid = price;
                break;
            case TickType.ASK:
                ask = price;
                break;
            case TickType.LAST:
                last = price;
                break;
            default:
                return;
        }

        if (isForex) { // there is no "last" for Forex, so calculate a midpoint instead
            synchronized (df6) {
                String formattedValue = df6.format( (bid + ask) / 2);
                last = df6.parse(formattedValue).doubleValue();
            }
        }

        if (isForex || tickType == TickType.LAST) {
            timeOfLast = strategy.getCalendar().getTimeInMillis();
            updateNextBar();
        }

        synchronized (this) {
            event = EVENT_NEW_TICK;
            notifyAll();
        }
    }

    public long getTimeOfLast() {
        return timeOfLast;
    }

    public String getTimeOfLastAsString() {
        synchronized (dateFormat) {
            return dateFormat.format(new Date(timeOfLast));
        }
    }

    public void addNextBar(long barTime) {
        PriceBar priceBar = new PriceBar(nextBar);

        priceBar.setDate(barTime);
        priceBars.add(priceBar);
        nextBar = new PriceBar(last);

        synchronized (this) {
            event = EVENT_NEW_BAR;
            notifyAll();
        }

    }

    private void updateNextBar() {

        nextBar.setClose(last);

        if (nextBar.getOpen() == 0) {
            nextBar.setOpen(last);
        }

        if (last > nextBar.getHigh()) {
            nextBar.setHigh(last);
        }

        if (last < nextBar.getLow()) {
            nextBar.setLow(last);
        }

    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public double getLast() {
        return last;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (PriceBar priceBar : priceBars) {
            sb.append(priceBar).append("<br>");
        }

        return sb.toString();
    }

    public boolean isValid() {
        boolean isValid = true;

        validationMessages.clear();
        int currentSize = priceBars.size();

        if (currentSize < requiredHistorySize) {
            String msg = "Required history size: " + requiredHistorySize + ", Actual history size: " + currentSize;
            validationMessages.add(msg);
            return false;
        }

        return isValid;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }

    public int size() {
        return priceBars.size();
    }

    public void addHistoricalPriceBar(PriceBar priceBar) {
        if (bid == 0 || ask == 0) {
            bid = priceBar.getClose();
            ask = priceBar.getClose();
        }
        priceBars.add(priceBar);
    }

    public PriceBar getPriceBar(int index) {
        return priceBars.get(index);
    }

    public void setIsHistRequestCompleted(boolean isHistRequestCompleted) {
        this.isHistRequestCompleted = isHistRequestCompleted;
        if (isHistRequestCompleted) {
            nextBar = new PriceBar(getLastPriceBar().getClose());
        }
    }

    public boolean getIsHistRequestCompleted() {
        return isHistRequestCompleted;
    }

    public PriceBar getLastPriceBar() {
        return priceBars.get(priceBars.size() - 1);
    }

    public PriceBar getFirstPriceBar() {
        return priceBars.get(0);
    }
}
