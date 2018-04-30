//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

public class EWrapperMsgGenerator extends AnyWrapperMsgGenerator {
    public static final String SCANNER_PARAMETERS = "SCANNER PARAMETERS:";
    public static final String FINANCIAL_ADVISOR = "FA:";

    public EWrapperMsgGenerator() {
    }

    public static String tickPrice(int tickerId, int field, double price, int canAutoExecute) {
        return "id=" + tickerId + "  " + TickType.getField(field) + "=" + price + " " + (canAutoExecute != 0?" canAutoExecute":" noAutoExecute");
    }

    public static String tickSize(int tickerId, int field, int size) {
        return "id=" + tickerId + "  " + TickType.getField(field) + "=" + size;
    }

    public static String tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
        String toAdd = "id=" + tickerId + "  " + TickType.getField(field) + ": vol = " + (impliedVol >= 0.0D && impliedVol != 1.7976931348623157E308D?Double.toString(impliedVol):"N/A") + " delta = " + (Math.abs(delta) <= 1.0D?Double.toString(delta):"N/A") + " gamma = " + (Math.abs(gamma) <= 1.0D?Double.toString(gamma):"N/A") + " vega = " + (Math.abs(vega) <= 1.0D?Double.toString(vega):"N/A") + " theta = " + (Math.abs(theta) <= 1.0D?Double.toString(theta):"N/A") + " optPrice = " + (optPrice >= 0.0D && optPrice != 1.7976931348623157E308D?Double.toString(optPrice):"N/A") + " pvDividend = " + (pvDividend >= 0.0D && pvDividend != 1.7976931348623157E308D?Double.toString(pvDividend):"N/A") + " undPrice = " + (undPrice >= 0.0D && undPrice != 1.7976931348623157E308D?Double.toString(undPrice):"N/A");
        return toAdd;
    }

    public static String tickGeneric(int tickerId, int tickType, double value) {
        return "id=" + tickerId + "  " + TickType.getField(tickType) + "=" + value;
    }

    public static String tickString(int tickerId, int tickType, String value) {
        return "id=" + tickerId + "  " + TickType.getField(tickType) + "=" + value;
    }

    public static String tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
        return "id=" + tickerId + "  " + TickType.getField(tickType) + ": basisPoints = " + basisPoints + "/" + formattedBasisPoints + " impliedFuture = " + impliedFuture + " holdDays = " + holdDays + " futureExpiry = " + futureExpiry + " dividendImpact = " + dividendImpact + " dividends to expiry = " + dividendsToExpiry;
    }

    public static String orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
        return "order status: orderId=" + orderId + " clientId=" + clientId + " permId=" + permId + " status=" + status + " filled=" + filled + " remaining=" + remaining + " avgFillPrice=" + avgFillPrice + " lastFillPrice=" + lastFillPrice + " parent Id=" + parentId + " whyHeld=" + whyHeld;
    }

    public static String openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        String msg = "open order: orderId=" + orderId + " action=" + order.m_action + " quantity=" + order.m_totalQuantity + " symbol=" + contract.m_symbol + " exchange=" + contract.m_exchange + " secType=" + contract.m_secType + " type=" + order.m_orderType + " lmtPrice=" + order.m_lmtPrice + " auxPrice=" + order.m_auxPrice + " TIF=" + order.m_tif + " localSymbol=" + contract.m_localSymbol + " client Id=" + order.m_clientId + " parent Id=" + order.m_parentId + " permId=" + order.m_permId + " outsideRth=" + order.m_outsideRth + " hidden=" + order.m_hidden + " discretionaryAmt=" + order.m_discretionaryAmt + " triggerMethod=" + order.m_triggerMethod + " goodAfterTime=" + order.m_goodAfterTime + " goodTillDate=" + order.m_goodTillDate + " faGroup=" + order.m_faGroup + " faMethod=" + order.m_faMethod + " faPercentage=" + order.m_faPercentage + " faProfile=" + order.m_faProfile + " shortSaleSlot=" + order.m_shortSaleSlot + " designatedLocation=" + order.m_designatedLocation + " exemptCode=" + order.m_exemptCode + " ocaGroup=" + order.m_ocaGroup + " ocaType=" + order.m_ocaType + " rule80A=" + order.m_rule80A + " allOrNone=" + order.m_allOrNone + " minQty=" + order.m_minQty + " percentOffset=" + order.m_percentOffset + " eTradeOnly=" + order.m_eTradeOnly + " firmQuoteOnly=" + order.m_firmQuoteOnly + " nbboPriceCap=" + order.m_nbboPriceCap + " optOutSmartRouting=" + order.m_optOutSmartRouting + " auctionStrategy=" + order.m_auctionStrategy + " startingPrice=" + order.m_startingPrice + " stockRefPrice=" + order.m_stockRefPrice + " delta=" + order.m_delta + " stockRangeLower=" + order.m_stockRangeLower + " stockRangeUpper=" + order.m_stockRangeUpper + " volatility=" + order.m_volatility + " volatilityType=" + order.m_volatilityType + " deltaNeutralOrderType=" + order.m_deltaNeutralOrderType + " deltaNeutralAuxPrice=" + order.m_deltaNeutralAuxPrice + " deltaNeutralConId=" + order.m_deltaNeutralConId + " deltaNeutralSettlingFirm=" + order.m_deltaNeutralSettlingFirm + " deltaNeutralClearingAccount=" + order.m_deltaNeutralClearingAccount + " deltaNeutralClearingIntent=" + order.m_deltaNeutralClearingIntent + " continuousUpdate=" + order.m_continuousUpdate + " referencePriceType=" + order.m_referencePriceType + " trailStopPrice=" + order.m_trailStopPrice + " scaleInitLevelSize=" + Util.IntMaxString(order.m_scaleInitLevelSize) + " scaleSubsLevelSize=" + Util.IntMaxString(order.m_scaleSubsLevelSize) + " scalePriceIncrement=" + Util.DoubleMaxString(order.m_scalePriceIncrement) + " hedgeType=" + order.m_hedgeType + " hedgeParam=" + order.m_hedgeParam + " account=" + order.m_account + " settlingFirm=" + order.m_settlingFirm + " clearingAccount=" + order.m_clearingAccount + " clearingIntent=" + order.m_clearingIntent + " notHeld=" + order.m_notHeld + " whatIf=" + order.m_whatIf;
        if("BAG".equals(contract.m_secType)) {
            if(contract.m_comboLegsDescrip != null) {
                msg = msg + " comboLegsDescrip=" + contract.m_comboLegsDescrip;
            }

            if(order.m_basisPoints != 1.7976931348623157E308D) {
                msg = msg + " basisPoints=" + order.m_basisPoints;
                msg = msg + " basisPointsType=" + order.m_basisPointsType;
            }
        }

        if(contract.m_underComp != null) {
            UnderComp underComp = contract.m_underComp;
            msg = msg + " underComp.conId =" + underComp.m_conId + " underComp.delta =" + underComp.m_delta + " underComp.price =" + underComp.m_price;
        }

        int i;
        TagValue param;
        Vector smartComboRoutingParams;
        if(!Util.StringIsEmpty(order.m_algoStrategy)) {
            msg = msg + " algoStrategy=" + order.m_algoStrategy;
            msg = msg + " algoParams={";
            if(order.m_algoParams != null) {
                smartComboRoutingParams = order.m_algoParams;

                for(i = 0; i < smartComboRoutingParams.size(); ++i) {
                    param = (TagValue)smartComboRoutingParams.elementAt(i);
                    if(i > 0) {
                        msg = msg + ",";
                    }

                    msg = msg + param.m_tag + "=" + param.m_value;
                }
            }

            msg = msg + "}";
        }

        if("BAG".equals(contract.m_secType)) {
            msg = msg + " smartComboRoutingParams={";
            if(order.m_smartComboRoutingParams != null) {
                smartComboRoutingParams = order.m_smartComboRoutingParams;

                for(i = 0; i < smartComboRoutingParams.size(); ++i) {
                    param = (TagValue)smartComboRoutingParams.elementAt(i);
                    if(i > 0) {
                        msg = msg + ",";
                    }

                    msg = msg + param.m_tag + "=" + param.m_value;
                }
            }

            msg = msg + "}";
        }

        String orderStateMsg = " status=" + orderState.m_status + " initMargin=" + orderState.m_initMargin + " maintMargin=" + orderState.m_maintMargin + " equityWithLoan=" + orderState.m_equityWithLoan + " commission=" + Util.DoubleMaxString(orderState.m_commission) + " minCommission=" + Util.DoubleMaxString(orderState.m_minCommission) + " maxCommission=" + Util.DoubleMaxString(orderState.m_maxCommission) + " commissionCurrency=" + orderState.m_commissionCurrency + " warningText=" + orderState.m_warningText;
        return msg + orderStateMsg;
    }

    public static String openOrderEnd() {
        return " =============== end ===============";
    }

    public static String updateAccountValue(String key, String value, String currency, String accountName) {
        return "updateAccountValue: " + key + " " + value + " " + currency + " " + accountName;
    }

    public static String updatePortfolio(Contract contract, int position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
        String msg = "updatePortfolio: " + contractMsg(contract) + position + " " + marketPrice + " " + marketValue + " " + averageCost + " " + unrealizedPNL + " " + realizedPNL + " " + accountName;
        return msg;
    }

    public static String updateAccountTime(String timeStamp) {
        return "updateAccountTime: " + timeStamp;
    }

    public static String accountDownloadEnd(String accountName) {
        return "accountDownloadEnd: " + accountName;
    }

    public static String nextValidId(int orderId) {
        return "Next Valid Order ID: " + orderId;
    }

    public static String contractDetails(int reqId, ContractDetails contractDetails) {
        Contract contract = contractDetails.m_summary;
        String msg = "reqId = " + reqId + " ===================================\n" + " ---- Contract Details begin ----\n" + contractMsg(contract) + contractDetailsMsg(contractDetails) + " ---- Contract Details End ----\n";
        return msg;
    }

    private static String contractDetailsMsg(ContractDetails contractDetails) {
        String msg = "marketName = " + contractDetails.m_marketName + "\n" + "tradingClass = " + contractDetails.m_tradingClass + "\n" + "minTick = " + contractDetails.m_minTick + "\n" + "price magnifier = " + contractDetails.m_priceMagnifier + "\n" + "orderTypes = " + contractDetails.m_orderTypes + "\n" + "validExchanges = " + contractDetails.m_validExchanges + "\n" + "underConId = " + contractDetails.m_underConId + "\n" + "longName = " + contractDetails.m_longName + "\n" + "contractMonth = " + contractDetails.m_contractMonth + "\n" + "industry = " + contractDetails.m_industry + "\n" + "category = " + contractDetails.m_category + "\n" + "subcategory = " + contractDetails.m_subcategory + "\n" + "timeZoneId = " + contractDetails.m_timeZoneId + "\n" + "tradingHours = " + contractDetails.m_tradingHours + "\n" + "liquidHours = " + contractDetails.m_liquidHours + "\n";
        return msg;
    }

    public static String contractMsg(Contract contract) {
        String msg = "conid = " + contract.m_conId + "\n" + "symbol = " + contract.m_symbol + "\n" + "secType = " + contract.m_secType + "\n" + "expiry = " + contract.m_expiry + "\n" + "strike = " + contract.m_strike + "\n" + "right = " + contract.m_right + "\n" + "multiplier = " + contract.m_multiplier + "\n" + "exchange = " + contract.m_exchange + "\n" + "primaryExch = " + contract.m_primaryExch + "\n" + "currency = " + contract.m_currency + "\n" + "localSymbol = " + contract.m_localSymbol + "\n";
        return msg;
    }

    public static String bondContractDetails(int reqId, ContractDetails contractDetails) {
        Contract contract = contractDetails.m_summary;
        String msg = "reqId = " + reqId + " ===================================\n" + " ---- Bond Contract Details begin ----\n" + "symbol = " + contract.m_symbol + "\n" + "secType = " + contract.m_secType + "\n" + "cusip = " + contractDetails.m_cusip + "\n" + "coupon = " + contractDetails.m_coupon + "\n" + "maturity = " + contractDetails.m_maturity + "\n" + "issueDate = " + contractDetails.m_issueDate + "\n" + "ratings = " + contractDetails.m_ratings + "\n" + "bondType = " + contractDetails.m_bondType + "\n" + "couponType = " + contractDetails.m_couponType + "\n" + "convertible = " + contractDetails.m_convertible + "\n" + "callable = " + contractDetails.m_callable + "\n" + "putable = " + contractDetails.m_putable + "\n" + "descAppend = " + contractDetails.m_descAppend + "\n" + "exchange = " + contract.m_exchange + "\n" + "currency = " + contract.m_currency + "\n" + "marketName = " + contractDetails.m_marketName + "\n" + "tradingClass = " + contractDetails.m_tradingClass + "\n" + "conid = " + contract.m_conId + "\n" + "minTick = " + contractDetails.m_minTick + "\n" + "orderTypes = " + contractDetails.m_orderTypes + "\n" + "validExchanges = " + contractDetails.m_validExchanges + "\n" + "nextOptionDate = " + contractDetails.m_nextOptionDate + "\n" + "nextOptionType = " + contractDetails.m_nextOptionType + "\n" + "nextOptionPartial = " + contractDetails.m_nextOptionPartial + "\n" + "notes = " + contractDetails.m_notes + "\n" + "longName = " + contractDetails.m_longName + "\n" + " ---- Bond Contract Details End ----\n";
        return msg;
    }

    public static String contractDetailsEnd(int reqId) {
        return "reqId = " + reqId + " =============== end ===============";
    }

    public static String execDetails(int reqId, Contract contract, Execution execution) {
        String msg = " ---- Execution Details begin ----\nreqId = " + reqId + "\n" + "orderId = " + execution.m_orderId + "\n" + "clientId = " + execution.m_clientId + "\n" + "symbol = " + contract.m_symbol + "\n" + "secType = " + contract.m_secType + "\n" + "expiry = " + contract.m_expiry + "\n" + "strike = " + contract.m_strike + "\n" + "right = " + contract.m_right + "\n" + "contractExchange = " + contract.m_exchange + "\n" + "currency = " + contract.m_currency + "\n" + "localSymbol = " + contract.m_localSymbol + "\n" + "execId = " + execution.m_execId + "\n" + "time = " + execution.m_time + "\n" + "acctNumber = " + execution.m_acctNumber + "\n" + "executionExchange = " + execution.m_exchange + "\n" + "side = " + execution.m_side + "\n" + "shares = " + execution.m_shares + "\n" + "price = " + execution.m_price + "\n" + "permId = " + execution.m_permId + "\n" + "liquidation = " + execution.m_liquidation + "\n" + "cumQty = " + execution.m_cumQty + "\n" + "avgPrice = " + execution.m_avgPrice + "\n" + "orderRef = " + execution.m_orderRef + "\n" + " ---- Execution Details end ----\n";
        return msg;
    }

    public static String execDetailsEnd(int reqId) {
        return "reqId = " + reqId + " =============== end ===============";
    }

    public static String updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
        return "updateMktDepth: " + tickerId + " " + position + " " + operation + " " + side + " " + price + " " + size;
    }

    public static String updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
        return "updateMktDepth: " + tickerId + " " + position + " " + marketMaker + " " + operation + " " + side + " " + price + " " + size;
    }

    public static String updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
        return "MsgId=" + msgId + " :: MsgType=" + msgType + " :: Origin=" + origExchange + " :: Message=" + message;
    }

    public static String managedAccounts(String accountsList) {
        return "Connected : The list of managed accounts are : [" + accountsList + "]";
    }

    public static String receiveFA(int faDataType, String xml) {
        return "FA: " + EClientSocket.faMsgTypeName(faDataType) + " " + xml;
    }

    public static String historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
        return "id=" + reqId + " date = " + date + " open=" + open + " high=" + high + " low=" + low + " close=" + close + " volume=" + volume + " count=" + count + " WAP=" + WAP + " hasGaps=" + hasGaps;
    }

    public static String realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {
        return "id=" + reqId + " time = " + time + " open=" + open + " high=" + high + " low=" + low + " close=" + close + " volume=" + volume + " count=" + count + " WAP=" + wap;
    }

    public static String scannerParameters(String xml) {
        return "SCANNER PARAMETERS:\n" + xml;
    }

    public static String scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {
        Contract contract = contractDetails.m_summary;
        return "id = " + reqId + " rank=" + rank + " symbol=" + contract.m_symbol + " secType=" + contract.m_secType + " expiry=" + contract.m_expiry + " strike=" + contract.m_strike + " right=" + contract.m_right + " exchange=" + contract.m_exchange + " currency=" + contract.m_currency + " localSymbol=" + contract.m_localSymbol + " marketName=" + contractDetails.m_marketName + " tradingClass=" + contractDetails.m_tradingClass + " distance=" + distance + " benchmark=" + benchmark + " projection=" + projection + " legsStr=" + legsStr;
    }

    public static String scannerDataEnd(int reqId) {
        return "id = " + reqId + " =============== end ===============";
    }

    public static String currentTime(long time) {
        return "current time = " + time + " (" + DateFormat.getDateTimeInstance().format(new Date(time * 1000L)) + ")";
    }

    public static String fundamentalData(int reqId, String data) {
        return "id  = " + reqId + " len = " + data.length() + '\n' + data;
    }

    public static String deltaNeutralValidation(int reqId, UnderComp underComp) {
        return "id = " + reqId + " underComp.conId =" + underComp.m_conId + " underComp.delta =" + underComp.m_delta + " underComp.price =" + underComp.m_price;
    }

    public static String tickSnapshotEnd(int tickerId) {
        return "id=" + tickerId + " =============== end ===============";
    }

    public static String marketDataType(int reqId, int marketDataType) {
        return "id=" + reqId + " marketDataType = " + MarketDataType.getField(marketDataType);
    }
}
