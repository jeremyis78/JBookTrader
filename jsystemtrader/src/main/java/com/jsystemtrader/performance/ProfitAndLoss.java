package com.jsystemtrader.performance;

import java.text.*;
import java.util.*;

/**
 * Encapsulates P&L information.
 */
public class ProfitAndLoss {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yy");

    private final long date;
    private final double value;

    public ProfitAndLoss(long date, double value) {
        this.date = date;
        this.value = value;
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

    public long getDate() {
        return date;
    }

    public String getShortDate() {
        String formattedDate = dateFormat.format(new Date(date));
        return formattedDate.toString();
    }
}
