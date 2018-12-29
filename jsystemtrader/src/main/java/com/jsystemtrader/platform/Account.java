package com.jsystemtrader.platform;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Acts as the model in the Model-View-Controller pattern. The interested views
 * may register themselves with the model to receive the updates.
 */
public class Account {
    public static int TWS_MODE = 0;
    public static int BACK_TEST_MODE = 1;

    private static final List<ModelListener> listeners = new ArrayList<ModelListener> ();
    private static HTMLLog eventLog;
    private static Trader trader;
    private static int mode;
    private static String backTestFileName;


    public static void setLogger(String eventLogFileName, int logVebosity) throws IOException {
        eventLog = new HTMLLog(logVebosity, eventLogFileName);
    }

    public static void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    public static Trader getTrader() {
        return trader;
    }

    public static HTMLLog getLogger() {
        return eventLog;
    }

    public static int getMode() {
        return mode;
    }

    public static String getBackestFileName() {
        return backTestFileName;
    }

    public static void setBackestFileName(String backTestFileName) {
        Account.backTestFileName = backTestFileName;
    }


    public static void logOff() {
        if (trader != null) {
            trader.getAssistant().disconnect();
        }
        System.exit(0);
    }

    public static void logOn(int mode) throws ParseException, JSystemTraderException {
        Account.mode = mode;
        trader = new Trader();
        String msg = (mode == Account.TWS_MODE) ? "Connected to TWS" : "Back Test";
        fireModelChanged(ModelListener.CONNECTED_TO_TWS, msg);
    }


    public static void fireModelChanged(int key, Object value) {
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            ModelListener listener = listeners.get(i);
            listener.modelChanged(key, value);
        }
    }

    public static void setActivity(String activity) {
        activity = activity.replaceAll("<br>", ". ");
        activity = activity.replaceAll("<b>", "");
        activity = activity.replaceAll("</b>", "");
        fireModelChanged(ModelListener.ACTIVITY, activity);
    }

}
