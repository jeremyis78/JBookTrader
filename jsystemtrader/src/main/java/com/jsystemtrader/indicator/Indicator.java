package com.jsystemtrader.indicator;

import java.text.*;
import java.util.*;

import com.jsystemtrader.platform.*;

/**
 * Base class for all classes implementing technical indicators.
 */
public abstract class Indicator {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yy");

    protected final long time;
    protected final Date date;
    protected double value;
    protected QuoteHistory qh;

    abstract public double calculate(); // must be implemented in subclasses.

    public Indicator(long time, QuoteHistory qh) {
        this.time = time;
        this.date = new Date(time);
        this.qh = qh;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" date: " + getShortDate());
        sb.append(" value: " + value);

        return sb.toString();
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public long getTime() {
        return time;
    }


    public String getShortDate() {
        String formattedDate = dateFormat.format(date);
        return formattedDate.toString();
    }
}
