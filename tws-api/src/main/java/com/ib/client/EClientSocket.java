//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

import com.ib.client.EClientErrors.CodeMsgPair;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class EClientSocket {
    public static final int CLIENT_VERSION = 53;
    private static final int SERVER_VERSION = 38;
    private static final byte[] EOL = new byte[]{0};
    private static final String BAG_SEC_TYPE = "BAG";
    public static final int GROUPS = 1;
    public static final int PROFILES = 2;
    public static final int ALIASES = 3;
    private static final int REQ_MKT_DATA = 1;
    private static final int CANCEL_MKT_DATA = 2;
    private static final int PLACE_ORDER = 3;
    private static final int CANCEL_ORDER = 4;
    private static final int REQ_OPEN_ORDERS = 5;
    private static final int REQ_ACCOUNT_DATA = 6;
    private static final int REQ_EXECUTIONS = 7;
    private static final int REQ_IDS = 8;
    private static final int REQ_CONTRACT_DATA = 9;
    private static final int REQ_MKT_DEPTH = 10;
    private static final int CANCEL_MKT_DEPTH = 11;
    private static final int REQ_NEWS_BULLETINS = 12;
    private static final int CANCEL_NEWS_BULLETINS = 13;
    private static final int SET_SERVER_LOGLEVEL = 14;
    private static final int REQ_AUTO_OPEN_ORDERS = 15;
    private static final int REQ_ALL_OPEN_ORDERS = 16;
    private static final int REQ_MANAGED_ACCTS = 17;
    private static final int REQ_FA = 18;
    private static final int REPLACE_FA = 19;
    private static final int REQ_HISTORICAL_DATA = 20;
    private static final int EXERCISE_OPTIONS = 21;
    private static final int REQ_SCANNER_SUBSCRIPTION = 22;
    private static final int CANCEL_SCANNER_SUBSCRIPTION = 23;
    private static final int REQ_SCANNER_PARAMETERS = 24;
    private static final int CANCEL_HISTORICAL_DATA = 25;
    private static final int REQ_CURRENT_TIME = 49;
    private static final int REQ_REAL_TIME_BARS = 50;
    private static final int CANCEL_REAL_TIME_BARS = 51;
    private static final int REQ_FUNDAMENTAL_DATA = 52;
    private static final int CANCEL_FUNDAMENTAL_DATA = 53;
    private static final int REQ_CALC_IMPLIED_VOLAT = 54;
    private static final int REQ_CALC_OPTION_PRICE = 55;
    private static final int CANCEL_CALC_IMPLIED_VOLAT = 56;
    private static final int CANCEL_CALC_OPTION_PRICE = 57;
    private static final int REQ_GLOBAL_CANCEL = 58;
    private static final int REQ_MARKET_DATA_TYPE = 59;
    private static final int MIN_SERVER_VER_REAL_TIME_BARS = 34;
    private static final int MIN_SERVER_VER_SCALE_ORDERS = 35;
    private static final int MIN_SERVER_VER_SNAPSHOT_MKT_DATA = 35;
    private static final int MIN_SERVER_VER_SSHORT_COMBO_LEGS = 35;
    private static final int MIN_SERVER_VER_WHAT_IF_ORDERS = 36;
    private static final int MIN_SERVER_VER_CONTRACT_CONID = 37;
    private static final int MIN_SERVER_VER_PTA_ORDERS = 39;
    private static final int MIN_SERVER_VER_FUNDAMENTAL_DATA = 40;
    private static final int MIN_SERVER_VER_UNDER_COMP = 40;
    private static final int MIN_SERVER_VER_CONTRACT_DATA_CHAIN = 40;
    private static final int MIN_SERVER_VER_SCALE_ORDERS2 = 40;
    private static final int MIN_SERVER_VER_ALGO_ORDERS = 41;
    private static final int MIN_SERVER_VER_EXECUTION_DATA_CHAIN = 42;
    private static final int MIN_SERVER_VER_NOT_HELD = 44;
    private static final int MIN_SERVER_VER_SEC_ID_TYPE = 45;
    private static final int MIN_SERVER_VER_PLACE_ORDER_CONID = 46;
    private static final int MIN_SERVER_VER_REQ_MKT_DATA_CONID = 47;
    private static final int MIN_SERVER_VER_REQ_CALC_IMPLIED_VOLAT = 49;
    private static final int MIN_SERVER_VER_REQ_CALC_OPTION_PRICE = 50;
    private static final int MIN_SERVER_VER_CANCEL_CALC_IMPLIED_VOLAT = 50;
    private static final int MIN_SERVER_VER_CANCEL_CALC_OPTION_PRICE = 50;
    private static final int MIN_SERVER_VER_SSHORTX_OLD = 51;
    private static final int MIN_SERVER_VER_SSHORTX = 52;
    private static final int MIN_SERVER_VER_REQ_GLOBAL_CANCEL = 53;
    private static final int MIN_SERVER_VER_HEDGE_ORDERS = 54;
    private static final int MIN_SERVER_VER_REQ_MARKET_DATA_TYPE = 55;
    private static final int MIN_SERVER_VER_OPT_OUT_SMART_ROUTING = 56;
    private static final int MIN_SERVER_VER_SMART_COMBO_ROUTING_PARAMS = 57;
    private static final int MIN_SERVER_VER_DELTA_NEUTRAL_CONID = 58;
    private AnyWrapper m_anyWrapper;
    private DataOutputStream m_dos;
    private boolean m_connected;
    private EReader m_reader;
    private int m_serverVersion = 0;
    private String m_TwsTime;

    public static String faMsgTypeName(int faDataType) {
        switch(faDataType) {
            case 1:
                return "GROUPS";
            case 2:
                return "PROFILES";
            case 3:
                return "ALIASES";
            default:
                return null;
        }
    }

    public int serverVersion() {
        return this.m_serverVersion;
    }

    public String TwsConnectionTime() {
        return this.m_TwsTime;
    }

    public AnyWrapper wrapper() {
        return this.m_anyWrapper;
    }

    public EReader reader() {
        return this.m_reader;
    }

    public EClientSocket(AnyWrapper anyWrapper) {
        this.m_anyWrapper = anyWrapper;
    }

    public boolean isConnected() {
        return this.m_connected;
    }

    public synchronized void eConnect(String host, int port, int clientId) {
        host = this.checkConnected(host);
        if(host != null) {
            try {
                Socket socket = new Socket(host, port);
                this.eConnect(socket, clientId);
            } catch (Exception var5) {
                this.eDisconnect();
                this.connectionError();
            }

        }
    }

    protected void connectionError() {
        this.m_anyWrapper.error(-1, EClientErrors.CONNECT_FAIL.code(), EClientErrors.CONNECT_FAIL.msg());
        this.m_reader = null;
    }

    protected String checkConnected(String host) {
        if(this.m_connected) {
            this.m_anyWrapper.error(-1, EClientErrors.ALREADY_CONNECTED.code(), EClientErrors.ALREADY_CONNECTED.msg());
            return null;
        } else {
            if(isNull(host)) {
                host = "127.0.0.1";
            }

            return host;
        }
    }

    public EReader createReader(EClientSocket socket, DataInputStream dis) {
        return new EReader(socket, dis);
    }

    public synchronized void eConnect(Socket socket, int clientId) throws IOException {
        this.m_dos = new DataOutputStream(socket.getOutputStream());
        this.send((int)53);
        this.m_reader = this.createReader(this, new DataInputStream(socket.getInputStream()));
        this.m_serverVersion = this.m_reader.readInt();
        System.out.println("Server Version:" + this.m_serverVersion);
        if(this.m_serverVersion >= 20) {
            this.m_TwsTime = this.m_reader.readStr();
            System.out.println("TWS Time at connection:" + this.m_TwsTime);
        }

        if(this.m_serverVersion < 38) {
            this.eDisconnect();
            this.m_anyWrapper.error(-1, EClientErrors.UPDATE_TWS.code(), EClientErrors.UPDATE_TWS.msg());
        } else {
            if(this.m_serverVersion >= 3) {
                this.send(clientId);
            }

            this.m_reader.start();
            this.m_connected = true;
        }
    }

    public synchronized void eDisconnect() {
        if(this.m_dos != null) {
            this.m_connected = false;
            this.m_serverVersion = 0;
            this.m_TwsTime = "";
            DataOutputStream dos = this.m_dos;
            this.m_dos = null;
            EReader reader = this.m_reader;
            this.m_reader = null;

            try {
                if(reader != null) {
                    reader.interrupt();
                }
            } catch (Exception var5) {
                ;
            }

            try {
                if(dos != null) {
                    dos.close();
                }
            } catch (Exception var4) {
                ;
            }

        }
    }

    public synchronized void cancelScannerSubscription(int tickerId) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 24) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support API scanner subscription.");
        } else {
            boolean var2 = true;

            try {
                this.send((int)23);
                this.send((int)1);
                this.send(tickerId);
            } catch (Exception var4) {
                this.error(tickerId, EClientErrors.FAIL_SEND_CANSCANNER, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void reqScannerParameters() {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 24) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support API scanner subscription.");
        } else {
            boolean var1 = true;

            try {
                this.send((int)24);
                this.send((int)1);
            } catch (Exception var3) {
                this.error(-1, EClientErrors.FAIL_SEND_REQSCANNERPARAMETERS, "" + var3);
                this.close();
            }

        }
    }

    public synchronized void reqScannerSubscription(int tickerId, ScannerSubscription subscription) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 24) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support API scanner subscription.");
        } else {
            boolean var3 = true;

            try {
                this.send((int)22);
                this.send((int)3);
                this.send(tickerId);
                this.sendMax(subscription.numberOfRows());
                this.send(subscription.instrument());
                this.send(subscription.locationCode());
                this.send(subscription.scanCode());
                this.sendMax(subscription.abovePrice());
                this.sendMax(subscription.belowPrice());
                this.sendMax(subscription.aboveVolume());
                this.sendMax(subscription.marketCapAbove());
                this.sendMax(subscription.marketCapBelow());
                this.send(subscription.moodyRatingAbove());
                this.send(subscription.moodyRatingBelow());
                this.send(subscription.spRatingAbove());
                this.send(subscription.spRatingBelow());
                this.send(subscription.maturityDateAbove());
                this.send(subscription.maturityDateBelow());
                this.sendMax(subscription.couponRateAbove());
                this.sendMax(subscription.couponRateBelow());
                this.send(subscription.excludeConvertible());
                if(this.m_serverVersion >= 25) {
                    this.send(subscription.averageOptionVolumeAbove());
                    this.send(subscription.scannerSettingPairs());
                }

                if(this.m_serverVersion >= 27) {
                    this.send(subscription.stockTypeFilter());
                }
            } catch (Exception var5) {
                this.error(tickerId, EClientErrors.FAIL_SEND_REQSCANNER, "" + var5);
                this.close();
            }

        }
    }

    public synchronized void reqMktData(int tickerId, Contract contract, String genericTickList, boolean snapshot) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 35 && snapshot) {
            this.error(tickerId, EClientErrors.UPDATE_TWS, "  It does not support snapshot market data requests.");
        } else if(this.m_serverVersion < 40 && contract.m_underComp != null) {
            this.error(tickerId, EClientErrors.UPDATE_TWS, "  It does not support delta-neutral orders.");
        } else if(this.m_serverVersion < 47 && contract.m_conId > 0) {
            this.error(tickerId, EClientErrors.UPDATE_TWS, "  It does not support conId parameter.");
        } else {
            boolean var5 = true;

            try {
                this.send((int)1);
                this.send((int)9);
                this.send(tickerId);
                if(this.m_serverVersion >= 47) {
                    this.send(contract.m_conId);
                }

                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                if(this.m_serverVersion >= 15) {
                    this.send(contract.m_multiplier);
                }

                this.send(contract.m_exchange);
                if(this.m_serverVersion >= 14) {
                    this.send(contract.m_primaryExch);
                }

                this.send(contract.m_currency);
                if(this.m_serverVersion >= 2) {
                    this.send(contract.m_localSymbol);
                }

                if(this.m_serverVersion >= 8 && "BAG".equalsIgnoreCase(contract.m_secType)) {
                    if(contract.m_comboLegs == null) {
                        this.send((int)0);
                    } else {
                        this.send(contract.m_comboLegs.size());

                        for(int i = 0; i < contract.m_comboLegs.size(); ++i) {
                            ComboLeg comboLeg = (ComboLeg)contract.m_comboLegs.get(i);
                            this.send(comboLeg.m_conId);
                            this.send(comboLeg.m_ratio);
                            this.send(comboLeg.m_action);
                            this.send(comboLeg.m_exchange);
                        }
                    }
                }

                if(this.m_serverVersion >= 40) {
                    if(contract.m_underComp != null) {
                        UnderComp underComp = contract.m_underComp;
                        this.send(true);
                        this.send(underComp.m_conId);
                        this.send(underComp.m_delta);
                        this.send(underComp.m_price);
                    } else {
                        this.send(false);
                    }
                }

                if(this.m_serverVersion >= 31) {
                    this.send(genericTickList);
                }

                if(this.m_serverVersion >= 35) {
                    this.send(snapshot);
                }
            } catch (Exception var8) {
                this.error(tickerId, EClientErrors.FAIL_SEND_REQMKT, "" + var8);
                this.close();
            }

        }
    }

    public synchronized void cancelHistoricalData(int tickerId) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 24) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support historical data query cancellation.");
        } else {
            boolean var2 = true;

            try {
                this.send((int)25);
                this.send((int)1);
                this.send(tickerId);
            } catch (Exception var4) {
                this.error(tickerId, EClientErrors.FAIL_SEND_CANHISTDATA, "" + var4);
                this.close();
            }

        }
    }

    public void cancelRealTimeBars(int tickerId) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 34) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support realtime bar data query cancellation.");
        } else {
            boolean var2 = true;

            try {
                this.send((int)51);
                this.send((int)1);
                this.send(tickerId);
            } catch (Exception var4) {
                this.error(tickerId, EClientErrors.FAIL_SEND_CANRTBARS, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void reqHistoricalData(int tickerId, Contract contract, String endDateTime, String durationStr, String barSizeSetting, String whatToShow, int useRTH, int formatDate) {
        if(!this.m_connected) {
            this.error(tickerId, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var9 = true;

            try {
                if(this.m_serverVersion < 16) {
                    this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support historical data backfill.");
                    return;
                }

                this.send((int)20);
                this.send((int)4);
                this.send(tickerId);
                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                this.send(contract.m_multiplier);
                this.send(contract.m_exchange);
                this.send(contract.m_primaryExch);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                if(this.m_serverVersion >= 31) {
                    this.send(contract.m_includeExpired?1:0);
                }

                if(this.m_serverVersion >= 20) {
                    this.send(endDateTime);
                    this.send(barSizeSetting);
                }

                this.send(durationStr);
                this.send(useRTH);
                this.send(whatToShow);
                if(this.m_serverVersion > 16) {
                    this.send(formatDate);
                }

                if("BAG".equalsIgnoreCase(contract.m_secType)) {
                    if(contract.m_comboLegs == null) {
                        this.send((int)0);
                    } else {
                        this.send(contract.m_comboLegs.size());

                        for(int i = 0; i < contract.m_comboLegs.size(); ++i) {
                            ComboLeg comboLeg = (ComboLeg)contract.m_comboLegs.get(i);
                            this.send(comboLeg.m_conId);
                            this.send(comboLeg.m_ratio);
                            this.send(comboLeg.m_action);
                            this.send(comboLeg.m_exchange);
                        }
                    }
                }
            } catch (Exception var12) {
                this.error(tickerId, EClientErrors.FAIL_SEND_REQHISTDATA, "" + var12);
                this.close();
            }

        }
    }

    public synchronized void reqRealTimeBars(int tickerId, Contract contract, int barSize, String whatToShow, boolean useRTH) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 34) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support real time bars.");
        } else {
            boolean var6 = true;

            try {
                this.send((int)50);
                this.send((int)1);
                this.send(tickerId);
                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                this.send(contract.m_multiplier);
                this.send(contract.m_exchange);
                this.send(contract.m_primaryExch);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                this.send(barSize);
                this.send(whatToShow);
                this.send(useRTH);
            } catch (Exception var8) {
                this.error(tickerId, EClientErrors.FAIL_SEND_REQRTBARS, "" + var8);
                this.close();
            }

        }
    }

    public synchronized void reqContractDetails(int reqId, Contract contract) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 4) {
            this.error(-1, EClientErrors.UPDATE_TWS.code(), EClientErrors.UPDATE_TWS.msg());
        } else if(this.m_serverVersion < 45 && (!IsEmpty(contract.m_secIdType) || !IsEmpty(contract.m_secId))) {
            this.error(reqId, EClientErrors.UPDATE_TWS, "  It does not support secIdType and secId parameters.");
        } else {
            boolean var3 = true;

            try {
                this.send((int)9);
                this.send((int)6);
                if(this.m_serverVersion >= 40) {
                    this.send(reqId);
                }

                if(this.m_serverVersion >= 37) {
                    this.send(contract.m_conId);
                }

                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                if(this.m_serverVersion >= 15) {
                    this.send(contract.m_multiplier);
                }

                this.send(contract.m_exchange);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                if(this.m_serverVersion >= 31) {
                    this.send(contract.m_includeExpired);
                }

                if(this.m_serverVersion >= 45) {
                    this.send(contract.m_secIdType);
                    this.send(contract.m_secId);
                }
            } catch (Exception var5) {
                this.error(-1, EClientErrors.FAIL_SEND_REQCONTRACT, "" + var5);
                this.close();
            }

        }
    }

    public synchronized void reqMktDepth(int tickerId, Contract contract, int numRows) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 6) {
            this.error(-1, EClientErrors.UPDATE_TWS.code(), EClientErrors.UPDATE_TWS.msg());
        } else {
            boolean var4 = true;

            try {
                this.send((int)10);
                this.send((int)3);
                this.send(tickerId);
                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                if(this.m_serverVersion >= 15) {
                    this.send(contract.m_multiplier);
                }

                this.send(contract.m_exchange);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                if(this.m_serverVersion >= 19) {
                    this.send(numRows);
                }
            } catch (Exception var6) {
                this.error(tickerId, EClientErrors.FAIL_SEND_REQMKTDEPTH, "" + var6);
                this.close();
            }

        }
    }

    public synchronized void cancelMktData(int tickerId) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var2 = true;

            try {
                this.send((int)2);
                this.send((int)1);
                this.send(tickerId);
            } catch (Exception var4) {
                this.error(tickerId, EClientErrors.FAIL_SEND_CANMKT, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void cancelMktDepth(int tickerId) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 6) {
            this.error(-1, EClientErrors.UPDATE_TWS.code(), EClientErrors.UPDATE_TWS.msg());
        } else {
            boolean var2 = true;

            try {
                this.send((int)11);
                this.send((int)1);
                this.send(tickerId);
            } catch (Exception var4) {
                this.error(tickerId, EClientErrors.FAIL_SEND_CANMKTDEPTH, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void exerciseOptions(int tickerId, Contract contract, int exerciseAction, int exerciseQuantity, String account, int override) {
        if(!this.m_connected) {
            this.error(tickerId, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var7 = true;

            try {
                if(this.m_serverVersion < 21) {
                    this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support options exercise from the API.");
                    return;
                }

                this.send((int)21);
                this.send((int)1);
                this.send(tickerId);
                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                this.send(contract.m_multiplier);
                this.send(contract.m_exchange);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                this.send(exerciseAction);
                this.send(exerciseQuantity);
                this.send(account);
                this.send(override);
            } catch (Exception var9) {
                this.error(tickerId, EClientErrors.FAIL_SEND_REQMKT, "" + var9);
                this.close();
            }

        }
    }

    public synchronized void placeOrder(int id, Contract contract, Order order) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 35 && (order.m_scaleInitLevelSize != 2147483647 || order.m_scalePriceIncrement != 1.7976931348623157E308D)) {
            this.error(id, EClientErrors.UPDATE_TWS, "  It does not support Scale orders.");
        } else {
            ComboLeg comboLeg;
            int i;
            if(this.m_serverVersion < 35 && !contract.m_comboLegs.isEmpty()) {
                for(i = 0; i < contract.m_comboLegs.size(); ++i) {
                    comboLeg = (ComboLeg)contract.m_comboLegs.get(i);
                    if(comboLeg.m_shortSaleSlot != 0 || !IsEmpty(comboLeg.m_designatedLocation)) {
                        this.error(id, EClientErrors.UPDATE_TWS, "  It does not support SSHORT flag for combo legs.");
                        return;
                    }
                }
            }

            if(this.m_serverVersion < 36 && order.m_whatIf) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support what-if orders.");
            } else if(this.m_serverVersion < 40 && contract.m_underComp != null) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support delta-neutral orders.");
            } else if(this.m_serverVersion < 40 && order.m_scaleSubsLevelSize != 2147483647) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support Subsequent Level Size for Scale orders.");
            } else if(this.m_serverVersion < 41 && !IsEmpty(order.m_algoStrategy)) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support algo orders.");
            } else if(this.m_serverVersion < 44 && order.m_notHeld) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support notHeld parameter.");
            } else if(this.m_serverVersion < 45 && (!IsEmpty(contract.m_secIdType) || !IsEmpty(contract.m_secId))) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support secIdType and secId parameters.");
            } else if(this.m_serverVersion < 46 && contract.m_conId > 0) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support conId parameter.");
            } else if(this.m_serverVersion < 52 && order.m_exemptCode != -1) {
                this.error(id, EClientErrors.UPDATE_TWS, "  It does not support exemptCode parameter.");
            } else {
                if(this.m_serverVersion < 52 && !contract.m_comboLegs.isEmpty()) {
                    for(i = 0; i < contract.m_comboLegs.size(); ++i) {
                        comboLeg = (ComboLeg)contract.m_comboLegs.get(i);
                        if(comboLeg.m_exemptCode != -1) {
                            this.error(id, EClientErrors.UPDATE_TWS, "  It does not support exemptCode parameter.");
                            return;
                        }
                    }
                }

                if(this.m_serverVersion < 54 && !IsEmpty(order.m_hedgeType)) {
                    this.error(id, EClientErrors.UPDATE_TWS, "  It does not support hedge orders.");
                } else if(this.m_serverVersion < 56 && order.m_optOutSmartRouting) {
                    this.error(id, EClientErrors.UPDATE_TWS, "  It does not support optOutSmartRouting parameter.");
                } else if(this.m_serverVersion < 58 && (order.m_deltaNeutralConId > 0 || !IsEmpty(order.m_deltaNeutralSettlingFirm) || !IsEmpty(order.m_deltaNeutralClearingAccount) || !IsEmpty(order.m_deltaNeutralClearingIntent))) {
                    this.error(id, EClientErrors.UPDATE_TWS, "  It does not support deltaNeutral parameters: ConId, SettlingFirm, ClearingAccount, ClearingIntent");
                } else {
                    int VERSION = this.m_serverVersion < 44?27:35;

                    try {
                        this.send((int)3);
                        this.send(VERSION);
                        this.send(id);
                        if(this.m_serverVersion >= 46) {
                            this.send(contract.m_conId);
                        }

                        this.send(contract.m_symbol);
                        this.send(contract.m_secType);
                        this.send(contract.m_expiry);
                        this.send(contract.m_strike);
                        this.send(contract.m_right);
                        if(this.m_serverVersion >= 15) {
                            this.send(contract.m_multiplier);
                        }

                        this.send(contract.m_exchange);
                        if(this.m_serverVersion >= 14) {
                            this.send(contract.m_primaryExch);
                        }

                        this.send(contract.m_currency);
                        if(this.m_serverVersion >= 2) {
                            this.send(contract.m_localSymbol);
                        }

                        if(this.m_serverVersion >= 45) {
                            this.send(contract.m_secIdType);
                            this.send(contract.m_secId);
                        }

                        this.send(order.m_action);
                        this.send(order.m_totalQuantity);
                        this.send(order.m_orderType);
                        this.send(order.m_lmtPrice);
                        this.send(order.m_auxPrice);
                        this.send(order.m_tif);
                        this.send(order.m_ocaGroup);
                        this.send(order.m_account);
                        this.send(order.m_openClose);
                        this.send(order.m_origin);
                        this.send(order.m_orderRef);
                        this.send(order.m_transmit);
                        if(this.m_serverVersion >= 4) {
                            this.send(order.m_parentId);
                        }

                        if(this.m_serverVersion >= 5) {
                            this.send(order.m_blockOrder);
                            this.send(order.m_sweepToFill);
                            this.send(order.m_displaySize);
                            this.send(order.m_triggerMethod);
                            if(this.m_serverVersion < 38) {
                                this.send(false);
                            } else {
                                this.send(order.m_outsideRth);
                            }
                        }

                        if(this.m_serverVersion >= 7) {
                            this.send(order.m_hidden);
                        }

                        int algoParamsCount;
                        if(this.m_serverVersion >= 8 && "BAG".equalsIgnoreCase(contract.m_secType)) {
                            if(contract.m_comboLegs == null) {
                                this.send((int)0);
                            } else {
                                this.send(contract.m_comboLegs.size());

                                for(algoParamsCount = 0; algoParamsCount < contract.m_comboLegs.size(); ++algoParamsCount) {
                                    comboLeg = (ComboLeg)contract.m_comboLegs.get(algoParamsCount);
                                    this.send(comboLeg.m_conId);
                                    this.send(comboLeg.m_ratio);
                                    this.send(comboLeg.m_action);
                                    this.send(comboLeg.m_exchange);
                                    this.send(comboLeg.m_openClose);
                                    if(this.m_serverVersion >= 35) {
                                        this.send(comboLeg.m_shortSaleSlot);
                                        this.send(comboLeg.m_designatedLocation);
                                    }

                                    if(this.m_serverVersion >= 51) {
                                        this.send(comboLeg.m_exemptCode);
                                    }
                                }
                            }
                        }

                        TagValue tagValue;
                        Vector algoParams;
                        if(this.m_serverVersion >= 57 && "BAG".equalsIgnoreCase(contract.m_secType)) {
                            algoParams = order.m_smartComboRoutingParams;
                            algoParamsCount = algoParams == null?0:algoParams.size();
                            this.send(algoParamsCount);
                            if(algoParamsCount > 0) {
                                for(i = 0; i < algoParamsCount; ++i) {
                                    tagValue = (TagValue)algoParams.get(i);
                                    this.send(tagValue.m_tag);
                                    this.send(tagValue.m_value);
                                }
                            }
                        }

                        if(this.m_serverVersion >= 9) {
                            this.send("");
                        }

                        if(this.m_serverVersion >= 10) {
                            this.send(order.m_discretionaryAmt);
                        }

                        if(this.m_serverVersion >= 11) {
                            this.send(order.m_goodAfterTime);
                        }

                        if(this.m_serverVersion >= 12) {
                            this.send(order.m_goodTillDate);
                        }

                        if(this.m_serverVersion >= 13) {
                            this.send(order.m_faGroup);
                            this.send(order.m_faMethod);
                            this.send(order.m_faPercentage);
                            this.send(order.m_faProfile);
                        }

                        if(this.m_serverVersion >= 18) {
                            this.send(order.m_shortSaleSlot);
                            this.send(order.m_designatedLocation);
                        }

                        if(this.m_serverVersion >= 51) {
                            this.send(order.m_exemptCode);
                        }

                        double lower;
                        double upper;
                        if(this.m_serverVersion >= 19) {
                            this.send(order.m_ocaType);
                            if(this.m_serverVersion < 38) {
                                this.send(false);
                            }

                            this.send(order.m_rule80A);
                            this.send(order.m_settlingFirm);
                            this.send(order.m_allOrNone);
                            this.sendMax(order.m_minQty);
                            this.sendMax(order.m_percentOffset);
                            this.send(order.m_eTradeOnly);
                            this.send(order.m_firmQuoteOnly);
                            this.sendMax(order.m_nbboPriceCap);
                            this.sendMax(order.m_auctionStrategy);
                            this.sendMax(order.m_startingPrice);
                            this.sendMax(order.m_stockRefPrice);
                            this.sendMax(order.m_delta);
                            lower = this.m_serverVersion == 26 && order.m_orderType.equals("VOL")?1.7976931348623157E308D:order.m_stockRangeLower;
                            upper = this.m_serverVersion == 26 && order.m_orderType.equals("VOL")?1.7976931348623157E308D:order.m_stockRangeUpper;
                            this.sendMax(lower);
                            this.sendMax(upper);
                        }

                        if(this.m_serverVersion >= 22) {
                            this.send(order.m_overridePercentageConstraints);
                        }

                        if(this.m_serverVersion >= 26) {
                            this.sendMax(order.m_volatility);
                            this.sendMax(order.m_volatilityType);
                            if(this.m_serverVersion < 28) {
                                this.send(order.m_deltaNeutralOrderType.equalsIgnoreCase("MKT"));
                            } else {
                                this.send(order.m_deltaNeutralOrderType);
                                this.sendMax(order.m_deltaNeutralAuxPrice);
                                if(this.m_serverVersion >= 58 && !IsEmpty(order.m_deltaNeutralOrderType)) {
                                    this.send(order.m_deltaNeutralConId);
                                    this.send(order.m_deltaNeutralSettlingFirm);
                                    this.send(order.m_deltaNeutralClearingAccount);
                                    this.send(order.m_deltaNeutralClearingIntent);
                                }
                            }

                            this.send(order.m_continuousUpdate);
                            if(this.m_serverVersion == 26) {
                                lower = order.m_orderType.equals("VOL")?order.m_stockRangeLower:1.7976931348623157E308D;
                                upper = order.m_orderType.equals("VOL")?order.m_stockRangeUpper:1.7976931348623157E308D;
                                this.sendMax(lower);
                                this.sendMax(upper);
                            }

                            this.sendMax(order.m_referencePriceType);
                        }

                        if(this.m_serverVersion >= 30) {
                            this.sendMax(order.m_trailStopPrice);
                        }

                        if(this.m_serverVersion >= 35) {
                            if(this.m_serverVersion >= 40) {
                                this.sendMax(order.m_scaleInitLevelSize);
                                this.sendMax(order.m_scaleSubsLevelSize);
                            } else {
                                this.send("");
                                this.sendMax(order.m_scaleInitLevelSize);
                            }

                            this.sendMax(order.m_scalePriceIncrement);
                        }

                        if(this.m_serverVersion >= 54) {
                            this.send(order.m_hedgeType);
                            if(!IsEmpty(order.m_hedgeType)) {
                                this.send(order.m_hedgeParam);
                            }
                        }

                        if(this.m_serverVersion >= 56) {
                            this.send(order.m_optOutSmartRouting);
                        }

                        if(this.m_serverVersion >= 39) {
                            this.send(order.m_clearingAccount);
                            this.send(order.m_clearingIntent);
                        }

                        if(this.m_serverVersion >= 44) {
                            this.send(order.m_notHeld);
                        }

                        if(this.m_serverVersion >= 40) {
                            if(contract.m_underComp != null) {
                                UnderComp underComp = contract.m_underComp;
                                this.send(true);
                                this.send(underComp.m_conId);
                                this.send(underComp.m_delta);
                                this.send(underComp.m_price);
                            } else {
                                this.send(false);
                            }
                        }

                        if(this.m_serverVersion >= 41) {
                            this.send(order.m_algoStrategy);
                            if(!IsEmpty(order.m_algoStrategy)) {
                                algoParams = order.m_algoParams;
                                algoParamsCount = algoParams == null?0:algoParams.size();
                                this.send(algoParamsCount);
                                if(algoParamsCount > 0) {
                                    for(i = 0; i < algoParamsCount; ++i) {
                                        tagValue = (TagValue)algoParams.get(i);
                                        this.send(tagValue.m_tag);
                                        this.send(tagValue.m_value);
                                    }
                                }
                            }
                        }

                        if(this.m_serverVersion >= 36) {
                            this.send(order.m_whatIf);
                        }
                    } catch (Exception var9) {
                        this.error(id, EClientErrors.FAIL_SEND_ORDER, "" + var9);
                        this.close();
                    }

                }
            }
        }
    }

    public synchronized void reqAccountUpdates(boolean subscribe, String acctCode) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var3 = true;

            try {
                this.send((int)6);
                this.send((int)2);
                this.send(subscribe);
                if(this.m_serverVersion >= 9) {
                    this.send(acctCode);
                }
            } catch (Exception var5) {
                this.error(-1, EClientErrors.FAIL_SEND_ACCT, "" + var5);
                this.close();
            }

        }
    }

    public synchronized void reqExecutions(int reqId, ExecutionFilter filter) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var3 = true;

            try {
                this.send((int)7);
                this.send((int)3);
                if(this.m_serverVersion >= 42) {
                    this.send(reqId);
                }

                if(this.m_serverVersion >= 9) {
                    this.send(filter.m_clientId);
                    this.send(filter.m_acctCode);
                    this.send(filter.m_time);
                    this.send(filter.m_symbol);
                    this.send(filter.m_secType);
                    this.send(filter.m_exchange);
                    this.send(filter.m_side);
                }
            } catch (Exception var5) {
                this.error(-1, EClientErrors.FAIL_SEND_EXEC, "" + var5);
                this.close();
            }

        }
    }

    public synchronized void cancelOrder(int id) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var2 = true;

            try {
                this.send((int)4);
                this.send((int)1);
                this.send(id);
            } catch (Exception var4) {
                this.error(id, EClientErrors.FAIL_SEND_CORDER, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void reqOpenOrders() {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var1 = true;

            try {
                this.send((int)5);
                this.send((int)1);
            } catch (Exception var3) {
                this.error(-1, EClientErrors.FAIL_SEND_OORDER, "" + var3);
                this.close();
            }

        }
    }

    public synchronized void reqIds(int numIds) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var2 = true;

            try {
                this.send((int)8);
                this.send((int)1);
                this.send(numIds);
            } catch (Exception var4) {
                this.error(-1, EClientErrors.FAIL_SEND_CORDER, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void reqNewsBulletins(boolean allMsgs) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var2 = true;

            try {
                this.send((int)12);
                this.send((int)1);
                this.send(allMsgs);
            } catch (Exception var4) {
                this.error(-1, EClientErrors.FAIL_SEND_CORDER, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void cancelNewsBulletins() {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var1 = true;

            try {
                this.send((int)13);
                this.send((int)1);
            } catch (Exception var3) {
                this.error(-1, EClientErrors.FAIL_SEND_CORDER, "" + var3);
                this.close();
            }

        }
    }

    public synchronized void setServerLogLevel(int logLevel) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var2 = true;

            try {
                this.send((int)14);
                this.send((int)1);
                this.send(logLevel);
            } catch (Exception var4) {
                this.error(-1, EClientErrors.FAIL_SEND_SERVER_LOG_LEVEL, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void reqAutoOpenOrders(boolean bAutoBind) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var2 = true;

            try {
                this.send((int)15);
                this.send((int)1);
                this.send(bAutoBind);
            } catch (Exception var4) {
                this.error(-1, EClientErrors.FAIL_SEND_OORDER, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void reqAllOpenOrders() {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var1 = true;

            try {
                this.send((int)16);
                this.send((int)1);
            } catch (Exception var3) {
                this.error(-1, EClientErrors.FAIL_SEND_OORDER, "" + var3);
                this.close();
            }

        }
    }

    public synchronized void reqManagedAccts() {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else {
            boolean var1 = true;

            try {
                this.send((int)17);
                this.send((int)1);
            } catch (Exception var3) {
                this.error(-1, EClientErrors.FAIL_SEND_OORDER, "" + var3);
                this.close();
            }

        }
    }

    public synchronized void requestFA(int faDataType) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 13) {
            this.error(-1, EClientErrors.UPDATE_TWS.code(), EClientErrors.UPDATE_TWS.msg());
        } else {
            boolean var2 = true;

            try {
                this.send((int)18);
                this.send((int)1);
                this.send(faDataType);
            } catch (Exception var4) {
                this.error(faDataType, EClientErrors.FAIL_SEND_FA_REQUEST, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void replaceFA(int faDataType, String xml) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 13) {
            this.error(-1, EClientErrors.UPDATE_TWS.code(), EClientErrors.UPDATE_TWS.msg());
        } else {
            boolean var3 = true;

            try {
                this.send((int)19);
                this.send((int)1);
                this.send(faDataType);
                this.send(xml);
            } catch (Exception var5) {
                this.error(faDataType, EClientErrors.FAIL_SEND_FA_REPLACE, "" + var5);
                this.close();
            }

        }
    }

    public synchronized void reqCurrentTime() {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 33) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support current time requests.");
        } else {
            boolean var1 = true;

            try {
                this.send((int)49);
                this.send((int)1);
            } catch (Exception var3) {
                this.error(-1, EClientErrors.FAIL_SEND_REQCURRTIME, "" + var3);
                this.close();
            }

        }
    }

    public synchronized void reqFundamentalData(int reqId, Contract contract, String reportType) {
        if(!this.m_connected) {
            this.error(reqId, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 40) {
            this.error(reqId, EClientErrors.UPDATE_TWS, "  It does not support fundamental data requests.");
        } else {
            boolean var4 = true;

            try {
                this.send((int)52);
                this.send((int)1);
                this.send(reqId);
                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_exchange);
                this.send(contract.m_primaryExch);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                this.send(reportType);
            } catch (Exception var6) {
                this.error(reqId, EClientErrors.FAIL_SEND_REQFUNDDATA, "" + var6);
                this.close();
            }

        }
    }

    public synchronized void cancelFundamentalData(int reqId) {
        if(!this.m_connected) {
            this.error(reqId, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 40) {
            this.error(reqId, EClientErrors.UPDATE_TWS, "  It does not support fundamental data requests.");
        } else {
            boolean var2 = true;

            try {
                this.send((int)53);
                this.send((int)1);
                this.send(reqId);
            } catch (Exception var4) {
                this.error(reqId, EClientErrors.FAIL_SEND_CANFUNDDATA, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void calculateImpliedVolatility(int reqId, Contract contract, double optionPrice, double underPrice) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 49) {
            this.error(reqId, EClientErrors.UPDATE_TWS, "  It does not support calculate implied volatility requests.");
        } else {
            boolean var7 = true;

            try {
                this.send((int)54);
                this.send((int)1);
                this.send(reqId);
                this.send(contract.m_conId);
                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                this.send(contract.m_multiplier);
                this.send(contract.m_exchange);
                this.send(contract.m_primaryExch);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                this.send(optionPrice);
                this.send(underPrice);
            } catch (Exception var9) {
                this.error(reqId, EClientErrors.FAIL_SEND_REQCALCIMPLIEDVOLAT, "" + var9);
                this.close();
            }

        }
    }

    public synchronized void cancelCalculateImpliedVolatility(int reqId) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 50) {
            this.error(reqId, EClientErrors.UPDATE_TWS, "  It does not support calculate implied volatility cancellation.");
        } else {
            boolean var2 = true;

            try {
                this.send((int)56);
                this.send((int)1);
                this.send(reqId);
            } catch (Exception var4) {
                this.error(reqId, EClientErrors.FAIL_SEND_CANCALCIMPLIEDVOLAT, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void calculateOptionPrice(int reqId, Contract contract, double volatility, double underPrice) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 50) {
            this.error(reqId, EClientErrors.UPDATE_TWS, "  It does not support calculate option price requests.");
        } else {
            boolean var7 = true;

            try {
                this.send((int)55);
                this.send((int)1);
                this.send(reqId);
                this.send(contract.m_conId);
                this.send(contract.m_symbol);
                this.send(contract.m_secType);
                this.send(contract.m_expiry);
                this.send(contract.m_strike);
                this.send(contract.m_right);
                this.send(contract.m_multiplier);
                this.send(contract.m_exchange);
                this.send(contract.m_primaryExch);
                this.send(contract.m_currency);
                this.send(contract.m_localSymbol);
                this.send(volatility);
                this.send(underPrice);
            } catch (Exception var9) {
                this.error(reqId, EClientErrors.FAIL_SEND_REQCALCOPTIONPRICE, "" + var9);
                this.close();
            }

        }
    }

    public synchronized void cancelCalculateOptionPrice(int reqId) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 50) {
            this.error(reqId, EClientErrors.UPDATE_TWS, "  It does not support calculate option price cancellation.");
        } else {
            boolean var2 = true;

            try {
                this.send((int)57);
                this.send((int)1);
                this.send(reqId);
            } catch (Exception var4) {
                this.error(reqId, EClientErrors.FAIL_SEND_CANCALCOPTIONPRICE, "" + var4);
                this.close();
            }

        }
    }

    public synchronized void reqGlobalCancel() {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 53) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support globalCancel requests.");
        } else {
            boolean var1 = true;

            try {
                this.send((int)58);
                this.send((int)1);
            } catch (Exception var3) {
                this.error(-1, EClientErrors.FAIL_SEND_REQGLOBALCANCEL, "" + var3);
                this.close();
            }

        }
    }

    public synchronized void reqMarketDataType(int marketDataType) {
        if(!this.m_connected) {
            this.error(-1, EClientErrors.NOT_CONNECTED, "");
        } else if(this.m_serverVersion < 55) {
            this.error(-1, EClientErrors.UPDATE_TWS, "  It does not support marketDataType requests.");
        } else {
            boolean var2 = true;

            try {
                this.send((int)59);
                this.send((int)1);
                this.send(marketDataType);
            } catch (Exception var4) {
                this.error(-1, EClientErrors.FAIL_SEND_REQMARKETDATATYPE, "" + var4);
                this.close();
            }

        }
    }

    protected synchronized void error(String err) {
        this.m_anyWrapper.error(err);
    }

    protected synchronized void error(int id, int errorCode, String errorMsg) {
        this.m_anyWrapper.error(id, errorCode, errorMsg);
    }

    protected void close() {
        this.eDisconnect();
        this.wrapper().connectionClosed();
    }

    private static boolean is(String str) {
        return str != null && str.length() > 0;
    }

    private static boolean isNull(String str) {
        return !is(str);
    }

    private void error(int id, CodeMsgPair pair, String tail) {
        this.error(id, pair.code(), pair.msg() + tail);
    }

    protected void send(String str) throws IOException {
        if(!IsEmpty(str)) {
            this.m_dos.write(str.getBytes());
        }

        this.sendEOL();
    }

    private void sendEOL() throws IOException {
        this.m_dos.write(EOL);
    }

    protected void send(int val) throws IOException {
        this.send(String.valueOf(val));
    }

    protected void send(char val) throws IOException {
        this.m_dos.write(val);
        this.sendEOL();
    }

    protected void send(double val) throws IOException {
        this.send(String.valueOf(val));
    }

    protected void send(long val) throws IOException {
        this.send(String.valueOf(val));
    }

    private void sendMax(double val) throws IOException {
        if(val == 1.7976931348623157E308D) {
            this.sendEOL();
        } else {
            this.send(String.valueOf(val));
        }

    }

    private void sendMax(int val) throws IOException {
        if(val == 2147483647) {
            this.sendEOL();
        } else {
            this.send(String.valueOf(val));
        }

    }

    protected void send(boolean val) throws IOException {
        this.send(val?1:0);
    }

    private static boolean IsEmpty(String str) {
        return Util.StringIsEmpty(str);
    }
}
