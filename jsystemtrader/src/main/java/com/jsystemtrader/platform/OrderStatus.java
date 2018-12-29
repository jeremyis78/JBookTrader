package com.jsystemtrader.platform;

import java.util.*;

import com.ib.client.*;

/**
 * Encapsulates the execution information.
 */
public class OrderStatus {

    private final Order order;
    private final List<Execution> executions;
    private final Strategy strategy;
    private long date;
    private final int decision;

    public OrderStatus(Order order, Strategy strategy) {
        this.order = order;
        this.strategy = strategy;
        executions = new ArrayList<Execution> ();
        decision = strategy.getDecision();
    }

    public void add(Execution execution) {
        executions.add(execution);
        date = strategy.getCalendar().getTimeInMillis();
    }

    public void reset() {
        executions.clear();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public boolean isFilled() {
        int contractsFilled = 0;
        for (Execution execution : executions) {
            contractsFilled += execution.m_shares;
        }
        return contractsFilled == order.m_totalQuantity;
    }

    public double getAvgFillPrice() {
        int contractsFilled = 0;
        double avgFillPrice = 0;
        for (Execution execution : executions) {
            contractsFilled += execution.m_shares;
            avgFillPrice += execution.m_price * execution.m_shares;
        }
        avgFillPrice /= contractsFilled;

        return avgFillPrice;
    }

    public long getDate() {
        return date;
    }

    public int getDecision() {
        return decision;
    }
}
