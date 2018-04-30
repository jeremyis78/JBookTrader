//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class EClientErrors {
    static final int NO_VALID_ID = -1;
    static final EClientErrors.CodeMsgPair ALREADY_CONNECTED = new EClientErrors.CodeMsgPair(501, "Already connected.");
    static final EClientErrors.CodeMsgPair CONNECT_FAIL = new EClientErrors.CodeMsgPair(502, "Couldn't connect to TWS.  Confirm that \"Enable ActiveX and Socket Clients\" is enabled on the TWS \"Configure->API\" menu.");
    static final EClientErrors.CodeMsgPair UPDATE_TWS = new EClientErrors.CodeMsgPair(503, "The TWS is out of date and must be upgraded.");
    static final EClientErrors.CodeMsgPair NOT_CONNECTED = new EClientErrors.CodeMsgPair(504, "Not connected");
    static final EClientErrors.CodeMsgPair UNKNOWN_ID = new EClientErrors.CodeMsgPair(505, "Fatal Error: Unknown message id.");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQMKT = new EClientErrors.CodeMsgPair(510, "Request Market Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANMKT = new EClientErrors.CodeMsgPair(511, "Cancel Market Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_ORDER = new EClientErrors.CodeMsgPair(512, "Order Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_ACCT = new EClientErrors.CodeMsgPair(513, "Account Update Request Sending Error -");
    static final EClientErrors.CodeMsgPair FAIL_SEND_EXEC = new EClientErrors.CodeMsgPair(514, "Request For Executions Sending Error -");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CORDER = new EClientErrors.CodeMsgPair(515, "Cancel Order Sending Error -");
    static final EClientErrors.CodeMsgPair FAIL_SEND_OORDER = new EClientErrors.CodeMsgPair(516, "Request Open Order Sending Error -");
    static final EClientErrors.CodeMsgPair UNKNOWN_CONTRACT = new EClientErrors.CodeMsgPair(517, "Unknown contract. Verify the contract details supplied.");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQCONTRACT = new EClientErrors.CodeMsgPair(518, "Request Contract Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQMKTDEPTH = new EClientErrors.CodeMsgPair(519, "Request Market Depth Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANMKTDEPTH = new EClientErrors.CodeMsgPair(520, "Cancel Market Depth Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_SERVER_LOG_LEVEL = new EClientErrors.CodeMsgPair(521, "Set Server Log Level Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_FA_REQUEST = new EClientErrors.CodeMsgPair(522, "FA Information Request Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_FA_REPLACE = new EClientErrors.CodeMsgPair(523, "FA Information Replace Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQSCANNER = new EClientErrors.CodeMsgPair(524, "Request Scanner Subscription Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANSCANNER = new EClientErrors.CodeMsgPair(525, "Cancel Scanner Subscription Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQSCANNERPARAMETERS = new EClientErrors.CodeMsgPair(526, "Request Scanner Parameter Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQHISTDATA = new EClientErrors.CodeMsgPair(527, "Request Historical Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANHISTDATA = new EClientErrors.CodeMsgPair(528, "Request Historical Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQRTBARS = new EClientErrors.CodeMsgPair(529, "Request Real-time Bar Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANRTBARS = new EClientErrors.CodeMsgPair(530, "Cancel Real-time Bar Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQCURRTIME = new EClientErrors.CodeMsgPair(531, "Request Current Time Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQFUNDDATA = new EClientErrors.CodeMsgPair(532, "Request Fundamental Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANFUNDDATA = new EClientErrors.CodeMsgPair(533, "Cancel Fundamental Data Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQCALCIMPLIEDVOLAT = new EClientErrors.CodeMsgPair(534, "Request Calculate Implied Volatility Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQCALCOPTIONPRICE = new EClientErrors.CodeMsgPair(535, "Request Calculate Option Price Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANCALCIMPLIEDVOLAT = new EClientErrors.CodeMsgPair(536, "Cancel Calculate Implied Volatility Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_CANCALCOPTIONPRICE = new EClientErrors.CodeMsgPair(537, "Cancel Calculate Option Price Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQGLOBALCANCEL = new EClientErrors.CodeMsgPair(538, "Request Global Cancel Sending Error - ");
    static final EClientErrors.CodeMsgPair FAIL_SEND_REQMARKETDATATYPE = new EClientErrors.CodeMsgPair(539, "Request Market Data Type Sending Error - ");

    public EClientErrors() {
    }

    public static class CodeMsgPair {
        int m_errorCode;
        String m_errorMsg;

        public int code() {
            return this.m_errorCode;
        }

        public String msg() {
            return this.m_errorMsg;
        }

        public CodeMsgPair(int i, String errString) {
            this.m_errorCode = i;
            this.m_errorMsg = errString;
        }
    }
}
