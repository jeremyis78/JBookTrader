package com.jsystemtrader.platform;

public interface ModelListener {

    public static final int ACTIVITY = 0;
    public static final int CONNECTED_TO_TWS = 1;
    public static final int TRADING_DECISION = 2;
    public static final int STRATEGY_ADDED = 3;

    public void modelChanged(int key, Object value);
}
