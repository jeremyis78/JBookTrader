package com.jsystemtrader.platform;

import java.util.*;

public class TradingSchedule {
    private List<TradingInterval> tradingIntervals;
    private final Strategy strategy;

    public TradingSchedule(Strategy strategy) {
        this.strategy = strategy;
        tradingIntervals = new ArrayList<TradingInterval> ();
    }

    public void addTradingInterval(String openTime, String closeTime, int graceTime) throws JSystemTraderException {
        TradingInterval interval = new TradingInterval(openTime, closeTime, graceTime);
        checkForOverlap(interval);
        tradingIntervals.add(interval);
    }

    /**
     * Determines if the time of day represented by c1 is before the time of day
     * represented by c2.
     *
     * @param c1 Calendar
     * @param c2 Calendar
     * @return boolean
     */
    private boolean isBefore(Calendar c1, Calendar c2) {
        int c1Munutes = c1.get(Calendar.HOUR_OF_DAY) * 60 + c1.get(Calendar.MINUTE);
        int c2Munutes = c2.get(Calendar.HOUR_OF_DAY) * 60 + c2.get(Calendar.MINUTE);
        return (c1Munutes < c2Munutes);
    }

    /**
     * Determines if the time of day represented by c1 is after the time of day
     * represented by c2.
     *
     * @param c1 Calendar
     * @param c2 Calendar
     * @return boolean
     */
    private boolean isAfter(Calendar c1, Calendar c2) {

        int c1Munutes = c1.get(Calendar.HOUR_OF_DAY) * 60 + c1.get(Calendar.MINUTE);
        int c2Munutes = c2.get(Calendar.HOUR_OF_DAY) * 60 + c2.get(Calendar.MINUTE);
        return (c1Munutes >= c2Munutes);
    }


    public boolean isTimeToTrade() {
        if (tradingIntervals.size() == 0) {
            return true;
        }

        boolean isTimeToTrade = false;
        Calendar now = strategy.getCalendar();
        for (TradingInterval interval : tradingIntervals) {
            Calendar openTime = interval.getOpenTime();
            Calendar graceTime = interval.getGraceTime();
            // If we are before grace time within any of the intervals,
            // we can trade
            if (isAfter(now, openTime) && isBefore(now, graceTime)) {
                isTimeToTrade = true;
                break;
            }
        }
        return isTimeToTrade;
    }


    public boolean isTimeToClose() {
        if (tradingIntervals.size() == 0) {
            return false;
        }

        boolean isTimeToClose = true;
        Calendar now = strategy.getCalendar();
        for (TradingInterval interval : tradingIntervals) {
            Calendar openTime = interval.getOpenTime();
            Calendar closeTime = interval.getCloseTime();
            // If we are within any of the intervals, do not close
            if (isAfter(now, openTime) && isBefore(now, closeTime)) {
                isTimeToClose = false;
                break;
            }
        }
        return isTimeToClose;
    }


    public boolean afterLastInterval() {
        if (tradingIntervals.size() == 0) {
            return false;
        }

        boolean afterLastInterval = true;
        Calendar now = strategy.getCalendar();
        for (TradingInterval interval : tradingIntervals) {
            Calendar closeTime = interval.getCloseTime();
            if (isBefore(now, closeTime)) {
                afterLastInterval = false;
                break;
            }
        }
        return afterLastInterval;
    }


    public void waitForFirstTradingInterval() throws InterruptedException {
        long timeToSleepMillis = 5 * 1000; // 5 seconds
        while (!isTimeToTrade()) {
            Thread.sleep(timeToSleepMillis);
        }
    }

    private void checkForOverlap(TradingInterval candidate) throws JSystemTraderException {
        for (TradingInterval interval : tradingIntervals) {
            Calendar open = interval.getOpenTime();
            Calendar close = interval.getCloseTime();

            Calendar candidateOpen = candidate.getOpenTime();
            Calendar candidateClose = candidate.getCloseTime();

            boolean invalidOpen = isAfter(candidateOpen, open) && isBefore(candidateOpen, close);
            boolean invalidClose = isAfter(candidateClose, open) && isBefore(candidateClose, close);
            if (invalidOpen || invalidClose) {
                String msg = "Invalid trading interval " + candidate.toString();
                msg += ": overlaps with " + interval.toString(); ;
                throw new JSystemTraderException(msg);
            }
        }
    }
}
