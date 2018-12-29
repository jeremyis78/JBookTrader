package com.jsystemtrader.platform;

/**
 * Adds a new bar to a quote history.
 */
public class TickCompressor extends Thread {
    private static final int MILLIS_IN_SECOND = 1000;

    private final QuoteHistory qh;
    private final Strategy strategy;
    private final long barSizeInMillis;
    private long nextBarTime, timeToNextBar;

    public TickCompressor(Strategy strategy) {
        this.strategy = strategy;
        barSizeInMillis = strategy.getBarSizeInSecs() * MILLIS_IN_SECOND;
        qh = strategy.getQuoteHistory();
        start();
    }

    public void run() {

        while (true) {
            long timeNow = strategy.getCalendar().getTimeInMillis();
            if (nextBarTime == 0) {
                nextBarTime = (timeNow / barSizeInMillis + 1) * barSizeInMillis;
            } else {
                nextBarTime += barSizeInMillis;
            }
            timeToNextBar = nextBarTime - timeNow;

            try {
                sleep(timeToNextBar + 500); // extra 500ms to get on the minute
                qh.addNextBar(nextBarTime);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }
    }
}
