package com.jsystemtrader.platform;

import java.text.*;
import java.util.*;

import javax.swing.*;

import com.ib.client.*;
import com.jsystemtrader.client.*;
import com.jsystemtrader.util.*;

public class TraderAssistant {
    private final String host = "";
    private final int port = 7496, clientID = 0;

    private EClientSocket socket;
    private final Map<Integer, QuoteHistory> quoteHistories;
    private final Map<Integer, Strategy> strategies;
    protected final Map<Integer, OrderStatus> orders;
    protected final HTMLLog eventLogger;
    protected static int strategyID, orderID;
    private String accountCode; // used to determine if TWS is running against real or paper trading account
    private int serverVersion;
    protected final Trader trader;


    public TraderAssistant(Trader trader) throws ParseException, JSystemTraderException {
        this.trader = trader;

        eventLogger = Account.getLogger();

        // contains historical data for multiple strategies
        quoteHistories = Collections.synchronizedMap(new HashMap<Integer, QuoteHistory> ());
        // maps IDs to strategies
        strategies = new HashMap<Integer, Strategy> ();
        // maps IDs to orders statuses
        orders = new HashMap<Integer, OrderStatus> ();

    }

    public Map<Integer, OrderStatus> getOrders() {
        return orders;
    }

    public void connect() throws ParseException, JSystemTraderException {
        eventLogger.write("Connecting to TWS", "Info", 1);
        socket = new EClientSocket(trader);

        socket.eConnect(host, port, clientID);
        if (!socket.isConnected()) {
            throw new JSystemTraderException("Could not connect to TWS. See log for details.");
        }

        // Make sure that system clock is the same as TWS clock
        TimeSyncChecker.timeCheck(socket.TwsConnectionTime());

        // IB Log levels: 1=SYSTEM 2=ERROR 3=WARNING 4=INFORMATION 5=DETAIL
        socket.setServerLogLevel(2);
        socket.reqNewsBulletins(true);
        serverVersion = socket.serverVersion();

        eventLogger.write("Connected to TWS", "Info", 1);

    }

    public int getServerVersion() {
        return serverVersion;
    }


    public void disconnect() {
        if (socket != null && socket.isConnected()) {
            socket.cancelNewsBulletins();
            socket.eDisconnect();
        }
    }

    public void resetOrders() {
        eventLogger.write("Resetting order statuses.", "Info", 1);
        for (Map.Entry mapEntry : orders.entrySet()) {
            OrderStatus orderStatus = (OrderStatus) mapEntry.getValue();
            orderStatus.reset();
        }
        eventLogger.write("Order statuses reset.", "Info", 1);
    }


    public QuoteHistory getQuoteHistoryForStrategy(int strategyID) {
        QuoteHistory qh = quoteHistories.get(strategyID);
        return qh;
    }


    public void getMarketData(int id, Contract contract) {
        try {
            socket.reqMktData(id, contract, null);
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
    }

    public void resubscribeToMarketData() {
        eventLogger.write("Re-subscribing to market data.", "Info", 1);
        try {
            for (Map.Entry mapEntry : strategies.entrySet()) {
                int strategyID = (Integer) mapEntry.getKey();
                Strategy strategy = (Strategy) mapEntry.getValue();
                Contract contract = strategy.getContract();
                socket.cancelMktData(strategyID);
                socket.reqMktData(strategyID, contract, null);
            }
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
        eventLogger.write("Re-subscribed to market data.", "Info", 1);
    }

    public void requestExecutions() {
        try {
            eventLogger.write("Requested executions.", "Info", 1);
            socket.reqExecutions(new ExecutionFilter());
        } catch (Throwable t) {
            // Do not allow exceptions come back to the socket -- it will cause disconnects
            eventLogger.write(t);
        }
    }

    public void getHistoricalData(int strategyID, Contract contract, String endDateTime, String duration,
                                  String barSize, String whatToShow, int useRTH, int formatDate) throws
            InterruptedException {

        // Only one historical data request can hit the socket at a time, so
        // we wait here until notified that no historical requests are pending.
        synchronized (trader) {
            while (trader.isPendingHistRequest) {
                trader.wait();
            }

            trader.isPendingHistRequest = true;
            QuoteHistory qh = getQuoteHistoryForStrategy(strategyID);
            qh.setIsHistRequestCompleted(false);
            String msg = qh.getStrategyName() + ": " + "Requested Historical data. ";
            msg += "End time: " + endDateTime + " Duration: " + duration;
            eventLogger.write(msg, "Info", 1);
        }

        socket.reqHistoricalData(strategyID, contract, endDateTime, duration, barSize, whatToShow, useRTH, formatDate);

    }

    synchronized public void addStrategy(Strategy strategy) {
        strategyID++;
        strategy.setID(strategyID);
        QuoteHistory qh = new QuoteHistory(strategy);
        quoteHistories.put(strategyID, qh);
        strategies.put(strategyID, strategy);
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    synchronized public void placeOrder(Contract contract, Order order, Strategy strategy) {
        orderID++;
        orders.put(orderID, new OrderStatus(order, strategy));
        strategy.getPositionManager().setOrderExecutionPending(true);
        socket.placeOrder(orderID, contract, order);
        String msg = strategy.getName() + ": Placed order " + orderID;
        eventLogger.write(msg, "Info", 1);
    }

    public void placeMarketOrder(Contract contract, int quantity, String action, Strategy strategy) {
        Order order = new Order();
        order.m_action = action;
        order.m_totalQuantity = quantity;
        order.m_orderType = "MKT";
        placeOrder(contract, order, strategy);
    }


    public void setOrderID(int orderID) {
        TraderAssistant.orderID = orderID;
    }

    public void realAccountCheck() throws JSystemTraderException {
        socket.reqAccountUpdates(true, "");

        try {
            synchronized (trader) {
                while (accountCode == null) {
                    trader.wait();
                }
            }
        } catch (InterruptedException ie) {
            throw new JSystemTraderException(ie);
        }

        socket.reqAccountUpdates(false, "");
        if (!accountCode.startsWith("D")) {
            String lineSep = System.getProperty("line.separator");
            String warning = "Connected to the real (not simulated) IB account. Running " + JSystemTrader.APP_NAME +
                             " against the real" + lineSep;
            warning += "account may cause significant losses in your account. Are you sure you want to proceed?";
            int response = JOptionPane.showConfirmDialog(null, warning, JSystemTrader.APP_NAME,
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.NO_OPTION) {
                disconnect();
            }
        }
    }

}
