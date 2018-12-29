package com.jsystemtrader.client;

import java.io.*;

import javax.swing.*;
import javax.swing.plaf.*;

import com.birosoft.liquid.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * Application starter
 */
public class JSystemTrader {
    public static final String APP_NAME = "JSystemTrader";
    private static final String CUSTOM_LOOK_AND_FEEL = "com.birosoft.liquid.LiquidLookAndFeel";
    private static final int LOG_VERBOSITY = 1;
    private static String appPath;

    /**
     * Instantiates the neccessary parts of the application: the application model,
     * views, and controller.
     */
    public JSystemTrader(String appPath) throws JSystemTraderException, FileNotFoundException, IOException {
        try {
            LiquidLookAndFeel.setLiquidDecorations(true, "mac");
            UIManager.setLookAndFeel(CUSTOM_LOOK_AND_FEEL);
        } catch (Throwable t) {
            String msg = t.getMessage() + ": Unable to set custom look & feel. The default L&F will be used.";
            MessageDialog.showMessage(msg);
        }

        this.appPath = appPath;

        // Set the color scheme explicitly
        ColorUIResource color = new ColorUIResource(102, 102, 153);
        UIManager.put("Label.foreground", color);
        UIManager.put("TitledBorder.titleColor", color);

        String fileSep = System.getProperty("file.separator");
        String eventLogFileName = appPath + fileSep + "Log" + fileSep + "EventLog.htm";
        Account.setLogger(eventLogFileName, LOG_VERBOSITY);

        new Controller();
    }

    /**
     * Starts JSystemTrader application.
     */
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new JSystemTraderException("Usage: JSystemTrader <JSystemTrader Directory>");
            }
            new JSystemTrader(args[0]);
        } catch (Throwable t) {
            MessageDialog.showError(t.toString());
            Account.getLogger().write(t);
        }
    }

    public static String getAppPath() {
        return appPath;
    }

}
