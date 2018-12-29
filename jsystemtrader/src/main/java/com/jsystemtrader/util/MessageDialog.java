package com.jsystemtrader.util;

import javax.swing.*;

import com.jsystemtrader.client.*;

/**
 * Utility class
 */
public class MessageDialog {

    public static void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, JSystemTrader.APP_NAME, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, JSystemTrader.APP_NAME, JOptionPane.ERROR_MESSAGE);
    }
}
