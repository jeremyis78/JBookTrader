//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class AnyWrapperMsgGenerator {
    public AnyWrapperMsgGenerator() {
    }

    public static String error(Exception ex) {
        return "Error - " + ex;
    }

    public static String error(String str) {
        return str;
    }

    public static String error(int id, int errorCode, String errorMsg) {
        String err = Integer.toString(id);
        err = err + " | ";
        err = err + Integer.toString(errorCode);
        err = err + " | ";
        err = err + errorMsg;
        return err;
    }

    public static String connectionClosed() {
        return "Connection Closed";
    }
}
