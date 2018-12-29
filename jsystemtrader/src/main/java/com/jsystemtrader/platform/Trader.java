package com.jsystemtrader.platform;

import java.text.*;
import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.backtest.*;

/**
 * This class acts as a "wrapper" in IB's API terminology.
 */
public class Trader extends EWrapperAdapter {
    private final HTMLLog eventLogger;
    static volatile boolean isPendingHistRequest;
    private final TraderAssistant traderAssitant;

    Trader() throws ParseException, JSystemTraderException {
        traderAssitant = (Account.getMode() == Account.TWS_MODE) ? new TraderAssistant(this) :
                         new BackTestTraderAssistant(this);
        eventLogger = Account.getLogger();
        traderAssitant.connect();
        traderAssitant.realAccountCheck();
    }

    public TraderAssistant getAssistant() {
        return traderAssitant;
    }


    @Override
    public void updateAccountValue(String key, String value, String currency, String accountName) {
        try {
            if (key.equalsIgnoreCase("AccountCode")) {
                traderAssitant.setAccountCode(value);
                synchronized (this) {
                    notifyAll();
                }
            }
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
    }

    @Override
    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
        String newsBulletin = "Msg ID: " + msgId + " Msg Type: " + msgType + " Msg: " + message + " Exchange: " +
                              origExchange;
        eventLogger.write(newsBulletin, "NewsBulletin", 1);
    }

    @Override
    public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume,
                               int count, double WAP, boolean hasGaps) {

        try {
            QuoteHistory qh = traderAssitant.getQuoteHistoryForStrategy(reqId);
            if (date.startsWith("finished")) {
                qh.setIsHistRequestCompleted(true);
                String msg = qh.getStrategyName() + ": Historical data request finished. ";
                msg += "New quote history size:  " + qh.size();

                eventLogger.write(msg, "Info", 1);
                synchronized (this) {
                    isPendingHistRequest = false;
                    notifyAll();
                }
            } else {
                long priceBarDate = Long.parseLong(date) * 1000;
                PriceBar priceBar = new PriceBar(priceBarDate, open, high, low, close, volume);
                qh.addHistoricalPriceBar(priceBar);
            }
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
    }

    @Override
    public void execDetails(int orderId, Contract contract, Execution execution) {
        try {
            Map<Integer, OrderStatus> orders = traderAssitant.getOrders();
            OrderStatus orderStatus = orders.get(orderId);

            if (orderStatus != null) {
                orderStatus.add(execution);

                if (orderStatus.isFilled()) {
                    Strategy strategy = orderStatus.getStrategy();

                    double avgFillPrice = orderStatus.getAvgFillPrice();
                    strategy.getExecutions().add(orderStatus);

                    String msg = strategy.getName() + ": " + "Order ID: " + orderId + " is filled.  Avg Fill Price: " +
                                 avgFillPrice;
                    orders.remove(orderId);
                    eventLogger.write(msg, "Info", 1);

                    PositionManager positionManager = strategy.getPositionManager();
                    synchronized (positionManager) {
                        positionManager.setAvgFillPrice(avgFillPrice);
                        positionManager.setOrderExecutionPending(false);
                        positionManager.notifyAll();
                    }
                }
            }
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
    }

    @Override
    public void tickPrice(int reqId, int tickType, double price, int canAutoExecute) {
        try {
            QuoteHistory qh = traderAssitant.getQuoteHistoryForStrategy(reqId);
            qh.update(tickType, price);
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
    }

    @Override
    public void error(Exception ex) {
        eventLogger.write(ex);
    }

    @Override
    public void error(String error) {
        eventLogger.write(error, "Error", 1);
    }

    @Override
    public void error(int id, int errorCode, String errorMsg) {
        try {
            String msg = id + " | " + errorCode + ": " + errorMsg;
            eventLogger.write(msg, "Info", 1);

            // Error 1101 is fired when connection between IB and TWS is restored
            // after a temporary connection loss and the data is "lost". In this case,
            // we need to resubscribe to market data.
            if (errorCode == 1101) {
                traderAssitant.resubscribeToMarketData();
            }

            // If either error 1101 or 1102 was fired, it means TWS was disconnected
            // from the IB server, so some orders could have been executed during
            // that time. In this case, we need to request executions.
            if (errorCode == 1101 || errorCode == 1102) {
                traderAssitant.resetOrders();
                traderAssitant.requestExecutions();
            }
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
    }

    @Override
    public void nextValidId(int orderID) {
        traderAssitant.setOrderID(orderID);
    }
}
