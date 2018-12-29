package com.jsystemtrader.platform;

import com.ib.client.*;

/**
 * Adapter pattern: provides empty implementation for all the methods in the
 * interface, so that the implementing classes can selectively override only
 * the needed methods.
 */
public class EWrapperAdapter implements EWrapper {
    public void error(Exception e) {}

    public void error(String str) {}

    public void error(int id, int errorCode, String errorMsg) {}

    public void connectionClosed() {}

    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {}

    public void tickSize(int tickerId, int field, int size) {}

    public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double modelPrice,
                                      double pvDividend) {}

    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId,
                            int parentId, double lastFillPrice, int clientId) {}

    public void openOrder(int orderId, Contract contract, Order order) {}

    public void updateAccountValue(String key, String value, String currency, String accountName) {}

    public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue,
                                double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {}

    public void updateAccountTime(String timeStamp) {}

    public void nextValidId(int orderId) {}

    public void contractDetails(ContractDetails contractDetails) {}

    public void bondContractDetails(ContractDetails contractDetails) {}

    public void execDetails(int orderId, Contract contract, Execution execution) {}

    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {}

    public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price,
                                 int size) {}

    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {}

    public void managedAccounts(String accountsList) {}

    public void receiveFA(int faDataType, String xml) {}

    public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume,
                               int count, double WAP, boolean hasGaps) {}

    public void scannerParameters(String xml) {}

    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark,
                            String projection) {}

    public void tickGeneric(int tickerId, int tickType, double value) {}

    public void tickString(int tickerId, int tickType, String value) {}

}
