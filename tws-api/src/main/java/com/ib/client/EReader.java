//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
// This required a bit of manual work as the decompiled class didn't compile.

package com.ib.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

public class EReader extends Thread {
    static final int TICK_PRICE = 1;
    static final int TICK_SIZE = 2;
    static final int ORDER_STATUS = 3;
    static final int ERR_MSG = 4;
    static final int OPEN_ORDER = 5;
    static final int ACCT_VALUE = 6;
    static final int PORTFOLIO_VALUE = 7;
    static final int ACCT_UPDATE_TIME = 8;
    static final int NEXT_VALID_ID = 9;
    static final int CONTRACT_DATA = 10;
    static final int EXECUTION_DATA = 11;
    static final int MARKET_DEPTH = 12;
    static final int MARKET_DEPTH_L2 = 13;
    static final int NEWS_BULLETINS = 14;
    static final int MANAGED_ACCTS = 15;
    static final int RECEIVE_FA = 16;
    static final int HISTORICAL_DATA = 17;
    static final int BOND_CONTRACT_DATA = 18;
    static final int SCANNER_PARAMETERS = 19;
    static final int SCANNER_DATA = 20;
    static final int TICK_OPTION_COMPUTATION = 21;
    static final int TICK_GENERIC = 45;
    static final int TICK_STRING = 46;
    static final int TICK_EFP = 47;
    static final int CURRENT_TIME = 49;
    static final int REAL_TIME_BARS = 50;
    static final int FUNDAMENTAL_DATA = 51;
    static final int CONTRACT_DATA_END = 52;
    static final int OPEN_ORDER_END = 53;
    static final int ACCT_DOWNLOAD_END = 54;
    static final int EXECUTION_DATA_END = 55;
    static final int DELTA_NEUTRAL_VALIDATION = 56;
    static final int TICK_SNAPSHOT_END = 57;
    static final int MARKET_DATA_TYPE = 58;
    private EClientSocket m_parent;
    private DataInputStream m_dis;

    protected EClientSocket parent() {
        return this.m_parent;
    }

    private EWrapper eWrapper() {
        return (EWrapper)this.parent().wrapper();
    }

    public EReader(EClientSocket parent, DataInputStream dis) {
        this("EReader", parent, dis);
    }

    protected EReader(String name, EClientSocket parent, DataInputStream dis) {
        this.setName(name);
        this.m_parent = parent;
        this.m_dis = dis;
    }

    public void run() {
        while(true) {
            try {
                if(!this.isInterrupted() && this.processMsg(this.readInt())) {
                    continue;
                }
            } catch (Exception var2) {
                if(this.parent().isConnected()) {
                    this.eWrapper().error(var2);
                }
            }

            if(this.parent().isConnected()) {
                this.m_parent.close();
            }

            return;
        }
    }

    protected boolean processMsg(int msgId) throws IOException {
        if(msgId == -1) {
            return false;
        } else {
            int version;
            int reqId;
            int position;
            double marketPrice;
            int i;
            double marketValue;
            double impliedFuturesPrice;
            double averageCost;
            int parentId;
            double unrealizedPNL;
            double high;
            double low;
            double vega;
            double undPrice;
            String key;
            String endDateStr;
            Contract contract;
            ContractDetails contractDetails;
            String status;
            String completedIndicator;
            int algoParamsCount;
            int ctr;
            int size;
            String benchmark;
            int permId;
            String legsStr;
            double realizedPNL;
            String accountName;
            long time;
            switch(msgId) {
                case 1:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    marketPrice = this.readDouble();
                    size = 0;
                    if(version >= 2) {
                        size = this.readInt();
                    }

                    ctr = 0;
                    if(version >= 3) {
                        ctr = this.readInt();
                    }

                    this.eWrapper().tickPrice(reqId, position, marketPrice, ctr);
                    if(version >= 2) {
                        int sizeTickType = -1;
                        switch(position) {
                            case 1:
                                sizeTickType = 0;
                                break;
                            case 2:
                                sizeTickType = 3;
                            case 3:
                            default:
                                break;
                            case 4:
                                sizeTickType = 5;
                        }

                        if(sizeTickType != -1) {
                            this.eWrapper().tickSize(reqId, sizeTickType, size);
                        }
                    }
                    break;
                case 2:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    algoParamsCount = this.readInt();
                    this.eWrapper().tickSize(reqId, position, algoParamsCount);
                    break;
                case 3:
                    version = this.readInt();
                    reqId = this.readInt();
                    status = this.readStr();
                    algoParamsCount = this.readInt();
                    i = this.readInt();
                    marketValue = this.readDouble();
                    permId = 0;
                    if(version >= 2) {
                        permId = this.readInt();
                    }

                    parentId = 0;
                    if(version >= 3) {
                        parentId = this.readInt();
                    }

                    unrealizedPNL = 0.0D;
                    if(version >= 4) {
                        unrealizedPNL = this.readDouble();
                    }

                    int clientId = 0;
                    if(version >= 5) {
                        clientId = this.readInt();
                    }

                    String whyHeld = null;
                    if(version >= 6) {
                        whyHeld = this.readStr();
                    }

                    this.eWrapper().orderStatus(reqId, status, algoParamsCount, i, marketValue, permId, parentId, unrealizedPNL, clientId, whyHeld);
                    break;
                case 4:
                    version = this.readInt();
                    if(version < 2) {
                        key = this.readStr();
                        this.m_parent.error(key);
                    } else {
                        reqId = this.readInt();
                        position = this.readInt();
                        endDateStr = this.readStr();
                        this.m_parent.error(reqId, position, endDateStr);
                    }
                    break;
                case 5:
                    version = this.readInt();
                    Order order = new Order();
                    order.m_orderId = this.readInt();
                    contract = new Contract();
                    if(version >= 17) {
                        contract.m_conId = this.readInt();
                    }

                    contract.m_symbol = this.readStr();
                    contract.m_secType = this.readStr();
                    contract.m_expiry = this.readStr();
                    contract.m_strike = this.readDouble();
                    contract.m_right = this.readStr();
                    contract.m_exchange = this.readStr();
                    contract.m_currency = this.readStr();
                    if(version >= 2) {
                        contract.m_localSymbol = this.readStr();
                    }

                    order.m_action = this.readStr();
                    order.m_totalQuantity = this.readInt();
                    order.m_orderType = this.readStr();
                    order.m_lmtPrice = this.readDouble();
                    order.m_auxPrice = this.readDouble();
                    order.m_tif = this.readStr();
                    order.m_ocaGroup = this.readStr();
                    order.m_account = this.readStr();
                    order.m_openClose = this.readStr();
                    order.m_origin = this.readInt();
                    order.m_orderRef = this.readStr();
                    if(version >= 3) {
                        order.m_clientId = this.readInt();
                    }

                    if(version >= 4) {
                        order.m_permId = this.readInt();
                        if(version < 18) {
                            this.readBoolFromInt();
                        } else {
                            order.m_outsideRth = this.readBoolFromInt();
                        }

                        order.m_hidden = this.readInt() == 1;
                        order.m_discretionaryAmt = this.readDouble();
                    }

                    if(version >= 5) {
                        order.m_goodAfterTime = this.readStr();
                    }

                    if(version >= 6) {
                        this.readStr();
                    }

                    if(version >= 7) {
                        order.m_faGroup = this.readStr();
                        order.m_faMethod = this.readStr();
                        order.m_faPercentage = this.readStr();
                        order.m_faProfile = this.readStr();
                    }

                    if(version >= 8) {
                        order.m_goodTillDate = this.readStr();
                    }

                    if(version >= 9) {
                        order.m_rule80A = this.readStr();
                        order.m_percentOffset = this.readDouble();
                        order.m_settlingFirm = this.readStr();
                        order.m_shortSaleSlot = this.readInt();
                        order.m_designatedLocation = this.readStr();
                        if(this.m_parent.serverVersion() == 51) {
                            this.readInt();
                        } else if(version >= 23) {
                            order.m_exemptCode = this.readInt();
                        }

                        order.m_auctionStrategy = this.readInt();
                        order.m_startingPrice = this.readDouble();
                        order.m_stockRefPrice = this.readDouble();
                        order.m_delta = this.readDouble();
                        order.m_stockRangeLower = this.readDouble();
                        order.m_stockRangeUpper = this.readDouble();
                        order.m_displaySize = this.readInt();
                        if(version < 18) {
                            this.readBoolFromInt();
                        }

                        order.m_blockOrder = this.readBoolFromInt();
                        order.m_sweepToFill = this.readBoolFromInt();
                        order.m_allOrNone = this.readBoolFromInt();
                        order.m_minQty = this.readInt();
                        order.m_ocaType = this.readInt();
                        order.m_eTradeOnly = this.readBoolFromInt();
                        order.m_firmQuoteOnly = this.readBoolFromInt();
                        order.m_nbboPriceCap = this.readDouble();
                    }

                    if(version >= 10) {
                        order.m_parentId = this.readInt();
                        order.m_triggerMethod = this.readInt();
                    }

                    if(version >= 11) {
                        order.m_volatility = this.readDouble();
                        order.m_volatilityType = this.readInt();
                        if(version == 11) {
                            algoParamsCount = this.readInt();
                            order.m_deltaNeutralOrderType = algoParamsCount == 0?"NONE":"MKT";
                        } else {
                            order.m_deltaNeutralOrderType = this.readStr();
                            order.m_deltaNeutralAuxPrice = this.readDouble();
                            if(version >= 27 && !Util.StringIsEmpty(order.m_deltaNeutralOrderType)) {
                                order.m_deltaNeutralConId = this.readInt();
                                order.m_deltaNeutralSettlingFirm = this.readStr();
                                order.m_deltaNeutralClearingAccount = this.readStr();
                                order.m_deltaNeutralClearingIntent = this.readStr();
                            }
                        }

                        order.m_continuousUpdate = this.readInt();
                        if(this.m_parent.serverVersion() == 26) {
                            order.m_stockRangeLower = this.readDouble();
                            order.m_stockRangeUpper = this.readDouble();
                        }

                        order.m_referencePriceType = this.readInt();
                    }

                    if(version >= 13) {
                        order.m_trailStopPrice = this.readDouble();
                    }

                    if(version >= 14) {
                        order.m_basisPoints = this.readDouble();
                        order.m_basisPointsType = this.readInt();
                        contract.m_comboLegsDescrip = this.readStr();
                    }

                    TagValue tagValue;
                    if(version >= 26) {
                        algoParamsCount = this.readInt();
                        if(algoParamsCount > 0) {
                            order.m_smartComboRoutingParams = new Vector(algoParamsCount);

                            for(i = 0; i < algoParamsCount; ++i) {
                                tagValue = new TagValue();
                                tagValue.m_tag = this.readStr();
                                tagValue.m_value = this.readStr();
                                order.m_smartComboRoutingParams.add(tagValue);
                            }
                        }
                    }

                    if(version >= 15) {
                        if(version >= 20) {
                            order.m_scaleInitLevelSize = this.readIntMax();
                            order.m_scaleSubsLevelSize = this.readIntMax();
                        } else {
                            this.readIntMax();
                            order.m_scaleInitLevelSize = this.readIntMax();
                        }

                        order.m_scalePriceIncrement = this.readDoubleMax();
                    }

                    if(version >= 24) {
                        order.m_hedgeType = this.readStr();
                        if(!Util.StringIsEmpty(order.m_hedgeType)) {
                            order.m_hedgeParam = this.readStr();
                        }
                    }

                    if(version >= 25) {
                        order.m_optOutSmartRouting = this.readBoolFromInt();
                    }

                    if(version >= 19) {
                        order.m_clearingAccount = this.readStr();
                        order.m_clearingIntent = this.readStr();
                    }

                    if(version >= 22) {
                        order.m_notHeld = this.readBoolFromInt();
                    }

                    if(version >= 20 && this.readBoolFromInt()) {
                        UnderComp underComp = new UnderComp();
                        underComp.m_conId = this.readInt();
                        underComp.m_delta = this.readDouble();
                        underComp.m_price = this.readDouble();
                        contract.m_underComp = underComp;
                    }

                    if(version >= 21) {
                        order.m_algoStrategy = this.readStr();
                        if(!Util.StringIsEmpty(order.m_algoStrategy)) {
                            algoParamsCount = this.readInt();
                            if(algoParamsCount > 0) {
                                order.m_algoParams = new Vector(algoParamsCount);

                                for(i = 0; i < algoParamsCount; ++i) {
                                    tagValue = new TagValue();
                                    tagValue.m_tag = this.readStr();
                                    tagValue.m_value = this.readStr();
                                    order.m_algoParams.add(tagValue);
                                }
                            }
                        }
                    }

                    OrderState orderState = new OrderState();
                    if(version >= 16) {
                        order.m_whatIf = this.readBoolFromInt();
                        orderState.m_status = this.readStr();
                        orderState.m_initMargin = this.readStr();
                        orderState.m_maintMargin = this.readStr();
                        orderState.m_equityWithLoan = this.readStr();
                        orderState.m_commission = this.readDoubleMax();
                        orderState.m_minCommission = this.readDoubleMax();
                        orderState.m_maxCommission = this.readDoubleMax();
                        orderState.m_commissionCurrency = this.readStr();
                        orderState.m_warningText = this.readStr();
                    }

                    this.eWrapper().openOrder(order.m_orderId, contract, order, orderState);
                    break;
                case 6:
                    version = this.readInt();
                    key = this.readStr();
                    status = this.readStr();
                    endDateStr = this.readStr();
                    completedIndicator = null;
                    if(version >= 2) {
                        completedIndicator = this.readStr();
                    }

                    this.eWrapper().updateAccountValue(key, status, endDateStr, completedIndicator);
                    break;
                case 7:
                    version = this.readInt();
                    contract = new Contract();
                    if(version >= 6) {
                        contract.m_conId = this.readInt();
                    }

                    contract.m_symbol = this.readStr();
                    contract.m_secType = this.readStr();
                    contract.m_expiry = this.readStr();
                    contract.m_strike = this.readDouble();
                    contract.m_right = this.readStr();
                    if(version >= 7) {
                        contract.m_multiplier = this.readStr();
                        contract.m_primaryExch = this.readStr();
                    }

                    contract.m_currency = this.readStr();
                    if(version >= 2) {
                        contract.m_localSymbol = this.readStr();
                    }

                    position = this.readInt();
                    marketPrice = this.readDouble();
                    marketValue = this.readDouble();
                    averageCost = 0.0D;
                    unrealizedPNL = 0.0D;
                    realizedPNL = 0.0D;
                    if(version >= 3) {
                        averageCost = this.readDouble();
                        unrealizedPNL = this.readDouble();
                        realizedPNL = this.readDouble();
                    }

                    accountName = null;
                    if(version >= 4) {
                        accountName = this.readStr();
                    }

                    if(version == 6 && this.m_parent.serverVersion() == 39) {
                        contract.m_primaryExch = this.readStr();
                    }

                    this.eWrapper().updatePortfolio(contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL, accountName);
                    break;
                case 8:
                    version = this.readInt();
                    key = this.readStr();
                    this.eWrapper().updateAccountTime(key);
                    break;
                case 9:
                    version = this.readInt();
                    reqId = this.readInt();
                    this.eWrapper().nextValidId(reqId);
                    break;
                case 10:
                    version = this.readInt();
                    reqId = -1;
                    if(version >= 3) {
                        reqId = this.readInt();
                    }

                    contractDetails = new ContractDetails();
                    contractDetails.m_summary.m_symbol = this.readStr();
                    contractDetails.m_summary.m_secType = this.readStr();
                    contractDetails.m_summary.m_expiry = this.readStr();
                    contractDetails.m_summary.m_strike = this.readDouble();
                    contractDetails.m_summary.m_right = this.readStr();
                    contractDetails.m_summary.m_exchange = this.readStr();
                    contractDetails.m_summary.m_currency = this.readStr();
                    contractDetails.m_summary.m_localSymbol = this.readStr();
                    contractDetails.m_marketName = this.readStr();
                    contractDetails.m_tradingClass = this.readStr();
                    contractDetails.m_summary.m_conId = this.readInt();
                    contractDetails.m_minTick = this.readDouble();
                    contractDetails.m_summary.m_multiplier = this.readStr();
                    contractDetails.m_orderTypes = this.readStr();
                    contractDetails.m_validExchanges = this.readStr();
                    if(version >= 2) {
                        contractDetails.m_priceMagnifier = this.readInt();
                    }

                    if(version >= 4) {
                        contractDetails.m_underConId = this.readInt();
                    }

                    if(version >= 5) {
                        contractDetails.m_longName = this.readStr();
                        contractDetails.m_summary.m_primaryExch = this.readStr();
                    }

                    if(version >= 6) {
                        contractDetails.m_contractMonth = this.readStr();
                        contractDetails.m_industry = this.readStr();
                        contractDetails.m_category = this.readStr();
                        contractDetails.m_subcategory = this.readStr();
                        contractDetails.m_timeZoneId = this.readStr();
                        contractDetails.m_tradingHours = this.readStr();
                        contractDetails.m_liquidHours = this.readStr();
                    }

                    this.eWrapper().contractDetails(reqId, contractDetails);
                    break;
                case 11:
                    version = this.readInt();
                    reqId = -1;
                    if(version >= 7) {
                        reqId = this.readInt();
                    }

                    position = this.readInt();
                    contract = new Contract();
                    if(version >= 5) {
                        contract.m_conId = this.readInt();
                    }

                    contract.m_symbol = this.readStr();
                    contract.m_secType = this.readStr();
                    contract.m_expiry = this.readStr();
                    contract.m_strike = this.readDouble();
                    contract.m_right = this.readStr();
                    contract.m_exchange = this.readStr();
                    contract.m_currency = this.readStr();
                    contract.m_localSymbol = this.readStr();
                    Execution exec = new Execution();
                    exec.m_orderId = position;
                    exec.m_execId = this.readStr();
                    exec.m_time = this.readStr();
                    exec.m_acctNumber = this.readStr();
                    exec.m_exchange = this.readStr();
                    exec.m_side = this.readStr();
                    exec.m_shares = this.readInt();
                    exec.m_price = this.readDouble();
                    if(version >= 2) {
                        exec.m_permId = this.readInt();
                    }

                    if(version >= 3) {
                        exec.m_clientId = this.readInt();
                    }

                    if(version >= 4) {
                        exec.m_liquidation = this.readInt();
                    }

                    if(version >= 6) {
                        exec.m_cumQty = this.readInt();
                        exec.m_avgPrice = this.readDouble();
                    }

                    if(version >= 8) {
                        exec.m_orderRef = this.readStr();
                    }

                    this.eWrapper().execDetails(reqId, contract, exec);
                    break;
                case 12:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    algoParamsCount = this.readInt();
                    i = this.readInt();
                    marketValue = this.readDouble();
                    permId = this.readInt();
                    this.eWrapper().updateMktDepth(reqId, position, algoParamsCount, i, marketValue, permId);
                    break;
                case 13:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    endDateStr = this.readStr();
                    i = this.readInt();
                    size = this.readInt();
                    impliedFuturesPrice = this.readDouble();
                    parentId = this.readInt();
                    this.eWrapper().updateMktDepthL2(reqId, position, endDateStr, i, size, impliedFuturesPrice, parentId);
                    break;
                case 14:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    endDateStr = this.readStr();
                    completedIndicator = this.readStr();
                    this.eWrapper().updateNewsBulletin(reqId, position, endDateStr, completedIndicator);
                    break;
                case 15:
                    version = this.readInt();
                    key = this.readStr();
                    this.eWrapper().managedAccounts(key);
                    break;
                case 16:
                    version = this.readInt();
                    reqId = this.readInt();
                    status = this.readStr();
                    this.eWrapper().receiveFA(reqId, status);
                    break;
                case 17:
                    version = this.readInt();
                    reqId = this.readInt();
                    completedIndicator = "finished";
                    if(version >= 2) {
                        status = this.readStr();
                        endDateStr = this.readStr();
                        completedIndicator = completedIndicator + "-" + status + "-" + endDateStr;
                    }

                    size = this.readInt();

                    for(ctr = 0; ctr < size; ++ctr) {
                        benchmark = this.readStr();
                        double open = this.readDouble();
                        high = this.readDouble();
                        low = this.readDouble();
                        double close = this.readDouble();
                        int volume = this.readInt();
                        undPrice = this.readDouble();
                        String hasGaps = this.readStr();
                        int barCount = -1;
                        if(version >= 3) {
                            barCount = this.readInt();
                        }

                        this.eWrapper().historicalData(reqId, benchmark, open, high, low, close, volume, barCount, undPrice, Boolean.valueOf(hasGaps).booleanValue());
                    }

                    this.eWrapper().historicalData(reqId, completedIndicator, -1.0D, -1.0D, -1.0D, -1.0D, -1, -1, -1.0D, false);
                    break;
                case 18:
                    version = this.readInt();
                    reqId = -1;
                    if(version >= 3) {
                        reqId = this.readInt();
                    }

                    contractDetails = new ContractDetails();
                    contractDetails.m_summary.m_symbol = this.readStr();
                    contractDetails.m_summary.m_secType = this.readStr();
                    contractDetails.m_cusip = this.readStr();
                    contractDetails.m_coupon = this.readDouble();
                    contractDetails.m_maturity = this.readStr();
                    contractDetails.m_issueDate = this.readStr();
                    contractDetails.m_ratings = this.readStr();
                    contractDetails.m_bondType = this.readStr();
                    contractDetails.m_couponType = this.readStr();
                    contractDetails.m_convertible = this.readBoolFromInt();
                    contractDetails.m_callable = this.readBoolFromInt();
                    contractDetails.m_putable = this.readBoolFromInt();
                    contractDetails.m_descAppend = this.readStr();
                    contractDetails.m_summary.m_exchange = this.readStr();
                    contractDetails.m_summary.m_currency = this.readStr();
                    contractDetails.m_marketName = this.readStr();
                    contractDetails.m_tradingClass = this.readStr();
                    contractDetails.m_summary.m_conId = this.readInt();
                    contractDetails.m_minTick = this.readDouble();
                    contractDetails.m_orderTypes = this.readStr();
                    contractDetails.m_validExchanges = this.readStr();
                    if(version >= 2) {
                        contractDetails.m_nextOptionDate = this.readStr();
                        contractDetails.m_nextOptionType = this.readStr();
                        contractDetails.m_nextOptionPartial = this.readBoolFromInt();
                        contractDetails.m_notes = this.readStr();
                    }

                    if(version >= 4) {
                        contractDetails.m_longName = this.readStr();
                    }

                    this.eWrapper().bondContractDetails(reqId, contractDetails);
                    break;
                case 19:
                    version = this.readInt();
                    key = this.readStr();
                    this.eWrapper().scannerParameters(key);
                    break;
                case 20:
                    contractDetails = new ContractDetails();
                    reqId = this.readInt();
                    position = this.readInt();
                    algoParamsCount = this.readInt();

                    for(i = 0; i < algoParamsCount; ++i) {
                        size = this.readInt();
                        if(reqId >= 3) {
                            contractDetails.m_summary.m_conId = this.readInt();
                        }

                        contractDetails.m_summary.m_symbol = this.readStr();
                        contractDetails.m_summary.m_secType = this.readStr();
                        contractDetails.m_summary.m_expiry = this.readStr();
                        contractDetails.m_summary.m_strike = this.readDouble();
                        contractDetails.m_summary.m_right = this.readStr();
                        contractDetails.m_summary.m_exchange = this.readStr();
                        contractDetails.m_summary.m_currency = this.readStr();
                        contractDetails.m_summary.m_localSymbol = this.readStr();
                        contractDetails.m_marketName = this.readStr();
                        contractDetails.m_tradingClass = this.readStr();
                        String distance = this.readStr();
                        benchmark = this.readStr();
                        String projection = this.readStr();
                        legsStr = null;
                        if(reqId >= 2) {
                            legsStr = this.readStr();
                        }

                        this.eWrapper().scannerData(position, size, contractDetails, distance, benchmark, projection, legsStr);
                    }

                    this.eWrapper().scannerDataEnd(position);
                    break;
                case 21:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    marketPrice = this.readDouble();
                    if(marketPrice < 0.0D) {
                        marketPrice = 1.7976931348623157E308D;
                    }

                    marketValue = this.readDouble();
                    if(Math.abs(marketValue) > 1.0D) {
                        marketValue = 1.7976931348623157E308D;
                    }

                    averageCost = 1.7976931348623157E308D;
                    unrealizedPNL = 1.7976931348623157E308D;
                    realizedPNL = 1.7976931348623157E308D;
                    vega = 1.7976931348623157E308D;
                    double theta = 1.7976931348623157E308D;
                    undPrice = 1.7976931348623157E308D;
                    if(version >= 6 || position == 13) {
                        averageCost = this.readDouble();
                        if(averageCost < 0.0D) {
                            averageCost = 1.7976931348623157E308D;
                        }

                        unrealizedPNL = this.readDouble();
                        if(unrealizedPNL < 0.0D) {
                            unrealizedPNL = 1.7976931348623157E308D;
                        }
                    }

                    if(version >= 6) {
                        realizedPNL = this.readDouble();
                        if(Math.abs(realizedPNL) > 1.0D) {
                            realizedPNL = 1.7976931348623157E308D;
                        }

                        vega = this.readDouble();
                        if(Math.abs(vega) > 1.0D) {
                            vega = 1.7976931348623157E308D;
                        }

                        theta = this.readDouble();
                        if(Math.abs(theta) > 1.0D) {
                            theta = 1.7976931348623157E308D;
                        }

                        undPrice = this.readDouble();
                        if(undPrice < 0.0D) {
                            undPrice = 1.7976931348623157E308D;
                        }
                    }

                    this.eWrapper().tickOptionComputation(reqId, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL, vega, theta, undPrice);
                    break;
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 48:
                default:
                    this.m_parent.error(-1, EClientErrors.UNKNOWN_ID.code(), EClientErrors.UNKNOWN_ID.msg());
                    return false;
                case 45:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    marketPrice = this.readDouble();
                    this.eWrapper().tickGeneric(reqId, position, marketPrice);
                    break;
                case 46:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    endDateStr = this.readStr();
                    this.eWrapper().tickString(reqId, position, endDateStr);
                    break;
                case 47:
                    version = this.readInt();
                    reqId = this.readInt();
                    position = this.readInt();
                    marketPrice = this.readDouble();
                    String formattedBasisPoints = this.readStr();
                    impliedFuturesPrice = this.readDouble();
                    parentId = this.readInt();
                    legsStr = this.readStr();
                    high = this.readDouble();
                    low = this.readDouble();
                    this.eWrapper().tickEFP(reqId, position, marketPrice, formattedBasisPoints, impliedFuturesPrice, parentId, legsStr, high, low);
                    break;
                case 49:
                    this.readInt();
                    time = this.readLong();
                    this.eWrapper().currentTime(time);
                    break;
                case 50:
                    this.readInt();
                    version = this.readInt();
                    time = this.readLong();
                    marketPrice = this.readDouble();
                    marketValue = this.readDouble();
                    averageCost = this.readDouble();
                    unrealizedPNL = this.readDouble();
                    long volume = this.readLong();
                    vega = this.readDouble();
                    int count = this.readInt();
                    this.eWrapper().realtimeBar(version, time, marketPrice, marketValue, averageCost, unrealizedPNL, volume, vega, count);
                    break;
                case 51:
                    this.readInt();
                    version = this.readInt();
                    key = this.readStr();
                    this.eWrapper().fundamentalData(version, key);
                    break;
                case 52:
                    this.readInt();
                    version = this.readInt();
                    this.eWrapper().contractDetailsEnd(version);
                    break;
                case 53:
                    this.readInt();
                    this.eWrapper().openOrderEnd();
                    break;
                case 54:
                    this.readInt();
                    accountName = this.readStr();
                    this.eWrapper().accountDownloadEnd(accountName);
                    break;
                case 55:
                    this.readInt();
                    version = this.readInt();
                    this.eWrapper().execDetailsEnd(version);
                    break;
                case 56:
                    this.readInt();
                    version = this.readInt();
                    UnderComp underComp = new UnderComp();
                    underComp.m_conId = this.readInt();
                    underComp.m_delta = this.readDouble();
                    underComp.m_price = this.readDouble();
                    this.eWrapper().deltaNeutralValidation(version, underComp);
                    break;
                case 57:
                    this.readInt();
                    version = this.readInt();
                    this.eWrapper().tickSnapshotEnd(version);
                    break;
                case 58:
                    this.readInt();
                    version = this.readInt();
                    reqId = this.readInt();
                    this.eWrapper().marketDataType(version, reqId);
            }

            return true;
        }
    }

    protected String readStr() throws IOException {
        StringBuffer buf = new StringBuffer();

        while(true) {
            byte c = this.m_dis.readByte();
            if(c == 0) {
                String str = buf.toString();
                return str.length() == 0?null:str;
            }

            buf.append((char)c);
        }
    }

    boolean readBoolFromInt() throws IOException {
        String str = this.readStr();
        return str == null?false:Integer.parseInt(str) != 0;
    }

    protected int readInt() throws IOException {
        String str = this.readStr();
        return str == null?0:Integer.parseInt(str);
    }

    protected int readIntMax() throws IOException {
        String str = this.readStr();
        return str != null && str.length() != 0?Integer.parseInt(str):2147483647;
    }

    protected long readLong() throws IOException {
        String str = this.readStr();
        return str == null?0L:Long.parseLong(str);
    }

    protected double readDouble() throws IOException {
        String str = this.readStr();
        return str == null?0.0D:Double.parseDouble(str);
    }

    protected double readDoubleMax() throws IOException {
        String str = this.readStr();
        return str != null && str.length() != 0?Double.parseDouble(str):1.7976931348623157E308D;
    }
}
