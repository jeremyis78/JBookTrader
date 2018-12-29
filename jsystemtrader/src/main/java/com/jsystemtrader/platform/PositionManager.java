package com.jsystemtrader.platform;

import java.text.*;

import com.ib.client.*;
import com.jsystemtrader.performance.*;


/**
 * Keeps track of current positions and P&L.
 */
public class PositionManager {
    public static final int POSITION_LONG = 0;
    public static final int POSITION_SHORT = 1;
    public static final int POSITION_NONE = 2;

    protected int position, previousPosition, trades;
    protected double profitAndLoss, totalProfitAndLoss;
    protected double avgFillPrice, previousTradeAvgFillPrice;
    private volatile boolean orderExecutionPending;
    private DecimalFormat nf4;
    protected final Strategy strategy;
    protected int quantityToTrade;
    private HTMLLog eventLog;
    private ProfitAndLossHistory profitAndLossHistory;

    public PositionManager(Strategy strategy) {
        this.strategy = strategy;

        profitAndLossHistory = new ProfitAndLossHistory();
        nf4 = (DecimalFormat) NumberFormat.getNumberInstance();
        nf4.setMaximumFractionDigits(4);

        position = previousPosition = POSITION_NONE;
        profitAndLoss = 0;
        trades = 0;
        eventLog = Account.getLogger();
    }

    public void setQuantityToTrade(int quantityToTrade) {
        this.quantityToTrade = quantityToTrade;

    }

    public int getTrades() {
        return trades;
    }

    public int getPosition() {
        return position;
    }

    public void setAvgFillPrice(double avgFillPrice) {
        this.avgFillPrice = avgFillPrice;
    }

    public double getAvgFillPrice() {
        return avgFillPrice;
    }

    public void setOrderExecutionPending(boolean orderExecutionPending) {
        this.orderExecutionPending = orderExecutionPending;
    }

    public double getProfitAndLoss() {
        return profitAndLoss;
    }

    public double getTotalProfitAndLoss() {
        return totalProfitAndLoss;
    }

    public ProfitAndLossHistory getProfitAndLossHistory() {
        return profitAndLossHistory;
    }

    public String getPositionAsString() {
        String pos = "None";

        switch (position) {
            case POSITION_LONG:
                pos = "Long";
                break;
            case POSITION_SHORT:
                pos = "Short";
                break;
            case POSITION_NONE:
                pos = "None";
                break;
        }

        return pos;
    }

    synchronized public void updatePosition() {
        try {
            while (orderExecutionPending) {
                // Wait for the full execution
                eventLog.write(strategy.getName() + ": waiting for order execution", "Info", 1);
                wait();
            }
        } catch (InterruptedException ie) {
            throw new RuntimeException(ie);
        }

        int decision = strategy.getDecision();
        switch (decision) {
            case Strategy.DECISION_LONG:
                position = POSITION_LONG;
                break;
            case Strategy.DECISION_SHORT:
                position = POSITION_SHORT;
                break;
            case Strategy.DECISION_FLAT:
                position = POSITION_NONE;
                break;
            case Strategy.DECISION_NONE:
            case Strategy.DECISION_DO_NOT_TRADE:
                break;
        }

        String msg;
        if (position != previousPosition) {
            trades++;

            if (trades > 1 && previousPosition != POSITION_NONE) {
                if (previousPosition == POSITION_LONG) {
                    profitAndLoss = avgFillPrice - previousTradeAvgFillPrice;
                }
                if (previousPosition == POSITION_SHORT) {
                    profitAndLoss = previousTradeAvgFillPrice - avgFillPrice;
                }

                totalProfitAndLoss += profitAndLoss;

                long time = strategy.getCalendar().getTimeInMillis();
                profitAndLossHistory.add(new ProfitAndLoss(time, totalProfitAndLoss));

                String result = (profitAndLoss > 0) ? "gain" : "loss";
                msg = "This trade P&L: " + nf4.format(profitAndLoss) + " points " + result + ".<br>";
                eventLog.write(strategy.getName() + ": " + msg, "Info", 1);

            }

            previousTradeAvgFillPrice = avgFillPrice;
        }

        if (position == previousPosition) {
            msg = "Decision: holding position " + getPositionAsString() + "<br>";
            eventLog.write(strategy.getName() + ": " + msg, "Info", 1);
        } else {
            msg = "<b>" + " Position changed to: " + getPositionAsString() + "</b><br>";
            eventLog.write(strategy.getName() + ": " + msg, "Info", 1);
        }

        previousPosition = position;
    }

    public boolean trade(int decision) throws JSystemTraderException {
        int quantity = 0;
        String action = null;
        boolean traded = false;

        switch (decision) {
            case Strategy.DECISION_NONE:
            case Strategy.DECISION_DO_NOT_TRADE:
            case Strategy.DECISION_EXIT:

                // do nothing
                break;
            case Strategy.DECISION_FLAT:
                if (position != POSITION_NONE) {
                    quantity = quantityToTrade;

                    if (position == POSITION_LONG) {
                        action = "SELL";
                    }
                    if (position == POSITION_SHORT) {
                        action = "BUY";
                    }
                }
                break;
            case Strategy.DECISION_LONG:
                if (position != POSITION_LONG) {
                    action = "BUY";
                    if (position == POSITION_SHORT) {
                        quantity = quantityToTrade * 2;
                    } else {
                        quantity = quantityToTrade;
                    }
                }
                break;
            case Strategy.DECISION_SHORT:
                if (position != POSITION_SHORT) {
                    action = "SELL";
                    if (position == POSITION_LONG) {
                        quantity = quantityToTrade * 2;
                    } else if (position == POSITION_NONE) {
                        quantity = quantityToTrade;
                    }
                }
                break;
            default:
                throw new JSystemTraderException("Decision type " + decision + " is not supported");
        }

        if (action != null) {
            Contract contract = strategy.getContract();

            Account.getTrader().getAssistant().placeMarketOrder(contract, quantity, action, strategy);
            traded = true;
        }

        return traded;
    }
}
