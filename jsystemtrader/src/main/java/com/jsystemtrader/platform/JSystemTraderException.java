package com.jsystemtrader.platform;

public class JSystemTraderException extends Exception {
    public JSystemTraderException(String message) {
        super(message);
    }

    public JSystemTraderException(Exception e) {
        super(e);
    }

    public JSystemTraderException(String message, Throwable cause) {
        super(message, cause);
    }
}
