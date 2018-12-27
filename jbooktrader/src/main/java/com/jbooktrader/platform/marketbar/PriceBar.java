package com.jbooktrader.platform.marketbar;

import com.jbooktrader.platform.model.JBookTraderException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulates the price bar information.
 */
public class PriceBar implements Snapshot {

    public static final int BAR_1_MINUTE = 1;
    public static final int BAR_2_MINUTE = 2;
    public static final int BAR_3_MINUTE = 3;
    public static final int BAR_5_MINUTE = 4;
    public static final int BAR_15_MINUTE = 5;
    public static final int BAR_30_MINUTE = 6;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yy");

    private long date;
    private double open, high, low, close;
    private int volume;


    public PriceBar(long date, double open, double high, double low, double close, int volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public PriceBar(double price) {
        this.open = this.high = this.low = this.close = price;
    }

    /** copy constructor */
    public PriceBar(PriceBar priceBar) {
        this.date = priceBar.date;
        this.open = priceBar.open;
        this.high = priceBar.high;
        this.low = priceBar.low;
        this.close = priceBar.close;
        this.volume = priceBar.volume;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" date: " + getShortDate());
        sb.append(" open: " + open);
        sb.append(" high: " + high);
        sb.append(" low: " + low);
        sb.append(" close: " + close);

        return sb.toString();
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getVolume() {
        return volume;
    }

    @Deprecated // use getTime() instead
    public long getDate() {
        return date;
    }

    synchronized public String getShortDate() {
        String shortDate = dateFormat.format(new Date(date));
        return shortDate.toString();
    }

    @Override
    public long getTime() {
        return date;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    /**
     * Not applicable to a non-market-book OHLC bar
     * @return
     */
    @Override
    public double getBalance() {
        throw new UnsupportedOperationException("OHLC bars do not have balance");
    }

    public static String barSizeToString(int barSizeSetting) throws RuntimeException {
        String barSize;

        switch (barSizeSetting) {
            case BAR_1_MINUTE:
                barSize = "1 min";
                break;
            case BAR_2_MINUTE:
                barSize = "2 mins";
                break;
            case BAR_3_MINUTE:
                barSize = "3 mins";
                break;
            case BAR_5_MINUTE:
                barSize = "5 mins";
                break;
            case BAR_15_MINUTE:
                barSize = "15 mins";
                break;
            case BAR_30_MINUTE:
                barSize = "30 mins";
                break;
            default:
                throw new RuntimeException("Bar size " + barSizeSetting + " is not supported");

        }

        return barSize;
    }


    public static int barSizeToSeconds(int barSize) throws JBookTraderException {
        int minutes;

        switch (barSize) {
            case BAR_1_MINUTE:
                minutes = 1;
                break;
            case BAR_2_MINUTE:
                minutes = 2;
                break;
            case BAR_3_MINUTE:
                minutes = 3;
                break;
            case BAR_5_MINUTE:
                minutes = 5;
                break;
            case BAR_15_MINUTE:
                minutes = 15;
                break;
            case BAR_30_MINUTE:
                minutes = 30;
                break;
            default:
                throw new JBookTraderException("Bar size " + barSize + " is not supported");

        }

        return minutes * 60;
    }
}
