//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

import java.util.Vector;

public class Order {
    public static final int CUSTOMER = 0;
    public static final int FIRM = 1;
    public static final char OPT_UNKNOWN = '?';
    public static final char OPT_BROKER_DEALER = 'b';
    public static final char OPT_CUSTOMER = 'c';
    public static final char OPT_FIRM = 'f';
    public static final char OPT_ISEMM = 'm';
    public static final char OPT_FARMM = 'n';
    public static final char OPT_SPECIALIST = 'y';
    public static final int AUCTION_MATCH = 1;
    public static final int AUCTION_IMPROVEMENT = 2;
    public static final int AUCTION_TRANSPARENT = 3;
    public static final String EMPTY_STR = "";
    public int m_orderId;
    public int m_clientId;
    public int m_permId;
    public String m_action;
    public int m_totalQuantity;
    public String m_orderType;
    public double m_lmtPrice;
    public double m_auxPrice;
    public String m_tif;
    public String m_ocaGroup;
    public int m_ocaType;
    public String m_orderRef;
    public boolean m_transmit = true;
    public int m_parentId;
    public boolean m_blockOrder;
    public boolean m_sweepToFill;
    public int m_displaySize;
    public int m_triggerMethod;
    public boolean m_outsideRth = false;
    public boolean m_hidden;
    public String m_goodAfterTime;
    public String m_goodTillDate;
    public boolean m_overridePercentageConstraints;
    public String m_rule80A;
    public boolean m_allOrNone;
    public int m_minQty = 2147483647;
    public double m_percentOffset = 1.7976931348623157E308D;
    public double m_trailStopPrice = 1.7976931348623157E308D;
    public String m_faGroup;
    public String m_faProfile;
    public String m_faMethod;
    public String m_faPercentage;
    public String m_openClose = "O";
    public int m_origin = 0;
    public int m_shortSaleSlot;
    public String m_designatedLocation = "";
    public int m_exemptCode = -1;
    public double m_discretionaryAmt;
    public boolean m_eTradeOnly;
    public boolean m_firmQuoteOnly;
    public double m_nbboPriceCap = 1.7976931348623157E308D;
    public boolean m_optOutSmartRouting = false;
    public int m_auctionStrategy;
    public double m_startingPrice = 1.7976931348623157E308D;
    public double m_stockRefPrice = 1.7976931348623157E308D;
    public double m_delta = 1.7976931348623157E308D;
    public double m_stockRangeLower = 1.7976931348623157E308D;
    public double m_stockRangeUpper = 1.7976931348623157E308D;
    public double m_volatility = 1.7976931348623157E308D;
    public int m_volatilityType = 2147483647;
    public int m_continuousUpdate;
    public int m_referencePriceType = 2147483647;
    public String m_deltaNeutralOrderType = "";
    public double m_deltaNeutralAuxPrice = 1.7976931348623157E308D;
    public int m_deltaNeutralConId = 0;
    public String m_deltaNeutralSettlingFirm = "";
    public String m_deltaNeutralClearingAccount = "";
    public String m_deltaNeutralClearingIntent = "";
    public double m_basisPoints = 1.7976931348623157E308D;
    public int m_basisPointsType = 2147483647;
    public int m_scaleInitLevelSize = 2147483647;
    public int m_scaleSubsLevelSize = 2147483647;
    public double m_scalePriceIncrement = 1.7976931348623157E308D;
    public String m_hedgeType;
    public String m_hedgeParam;
    public String m_account;
    public String m_settlingFirm;
    public String m_clearingAccount;
    public String m_clearingIntent;
    public String m_algoStrategy;
    public Vector<TagValue> m_algoParams;
    public boolean m_whatIf = false;
    public boolean m_notHeld = false;
    public Vector<TagValue> m_smartComboRoutingParams;

    public Order() {
    }

    public boolean equals(Object p_other) {
        if(this == p_other) {
            return true;
        } else if(p_other == null) {
            return false;
        } else {
            Order l_theOther = (Order)p_other;
            return this.m_permId == l_theOther.m_permId?true:(this.m_orderId == l_theOther.m_orderId && this.m_clientId == l_theOther.m_clientId && this.m_totalQuantity == l_theOther.m_totalQuantity && this.m_lmtPrice == l_theOther.m_lmtPrice && this.m_auxPrice == l_theOther.m_auxPrice && this.m_ocaType == l_theOther.m_ocaType && this.m_transmit == l_theOther.m_transmit && this.m_parentId == l_theOther.m_parentId && this.m_blockOrder == l_theOther.m_blockOrder && this.m_sweepToFill == l_theOther.m_sweepToFill && this.m_displaySize == l_theOther.m_displaySize && this.m_triggerMethod == l_theOther.m_triggerMethod && this.m_outsideRth == l_theOther.m_outsideRth && this.m_hidden == l_theOther.m_hidden && this.m_overridePercentageConstraints == l_theOther.m_overridePercentageConstraints && this.m_allOrNone == l_theOther.m_allOrNone && this.m_minQty == l_theOther.m_minQty && this.m_percentOffset == l_theOther.m_percentOffset && this.m_trailStopPrice == l_theOther.m_trailStopPrice && this.m_origin == l_theOther.m_origin && this.m_shortSaleSlot == l_theOther.m_shortSaleSlot && this.m_discretionaryAmt == l_theOther.m_discretionaryAmt && this.m_eTradeOnly == l_theOther.m_eTradeOnly && this.m_firmQuoteOnly == l_theOther.m_firmQuoteOnly && this.m_nbboPriceCap == l_theOther.m_nbboPriceCap && this.m_optOutSmartRouting == l_theOther.m_optOutSmartRouting && this.m_auctionStrategy == l_theOther.m_auctionStrategy && this.m_startingPrice == l_theOther.m_startingPrice && this.m_stockRefPrice == l_theOther.m_stockRefPrice && this.m_delta == l_theOther.m_delta && this.m_stockRangeLower == l_theOther.m_stockRangeLower && this.m_stockRangeUpper == l_theOther.m_stockRangeUpper && this.m_volatility == l_theOther.m_volatility && this.m_volatilityType == l_theOther.m_volatilityType && this.m_continuousUpdate == l_theOther.m_continuousUpdate && this.m_referencePriceType == l_theOther.m_referencePriceType && this.m_deltaNeutralAuxPrice == l_theOther.m_deltaNeutralAuxPrice && this.m_deltaNeutralConId == l_theOther.m_deltaNeutralConId && this.m_basisPoints == l_theOther.m_basisPoints && this.m_basisPointsType == l_theOther.m_basisPointsType && this.m_scaleInitLevelSize == l_theOther.m_scaleInitLevelSize && this.m_scaleSubsLevelSize == l_theOther.m_scaleSubsLevelSize && this.m_scalePriceIncrement == l_theOther.m_scalePriceIncrement && this.m_whatIf == l_theOther.m_whatIf && this.m_notHeld == l_theOther.m_notHeld && this.m_exemptCode == l_theOther.m_exemptCode?(Util.StringCompare(this.m_action, l_theOther.m_action) == 0 && Util.StringCompare(this.m_orderType, l_theOther.m_orderType) == 0 && Util.StringCompare(this.m_tif, l_theOther.m_tif) == 0 && Util.StringCompare(this.m_ocaGroup, l_theOther.m_ocaGroup) == 0 && Util.StringCompare(this.m_orderRef, l_theOther.m_orderRef) == 0 && Util.StringCompare(this.m_goodAfterTime, l_theOther.m_goodAfterTime) == 0 && Util.StringCompare(this.m_goodTillDate, l_theOther.m_goodTillDate) == 0 && Util.StringCompare(this.m_rule80A, l_theOther.m_rule80A) == 0 && Util.StringCompare(this.m_faGroup, l_theOther.m_faGroup) == 0 && Util.StringCompare(this.m_faProfile, l_theOther.m_faProfile) == 0 && Util.StringCompare(this.m_faMethod, l_theOther.m_faMethod) == 0 && Util.StringCompare(this.m_faPercentage, l_theOther.m_faPercentage) == 0 && Util.StringCompare(this.m_openClose, l_theOther.m_openClose) == 0 && Util.StringCompare(this.m_designatedLocation, l_theOther.m_designatedLocation) == 0 && Util.StringCompare(this.m_deltaNeutralOrderType, l_theOther.m_deltaNeutralOrderType) == 0 && Util.StringCompare(this.m_deltaNeutralSettlingFirm, l_theOther.m_deltaNeutralSettlingFirm) == 0 && Util.StringCompare(this.m_deltaNeutralClearingAccount, l_theOther.m_deltaNeutralClearingAccount) == 0 && Util.StringCompare(this.m_deltaNeutralClearingIntent, l_theOther.m_deltaNeutralClearingIntent) == 0 && Util.StringCompare(this.m_hedgeType, l_theOther.m_hedgeType) == 0 && Util.StringCompare(this.m_hedgeParam, l_theOther.m_hedgeParam) == 0 && Util.StringCompare(this.m_account, l_theOther.m_account) == 0 && Util.StringCompare(this.m_settlingFirm, l_theOther.m_settlingFirm) == 0 && Util.StringCompare(this.m_clearingAccount, l_theOther.m_clearingAccount) == 0 && Util.StringCompare(this.m_clearingIntent, l_theOther.m_clearingIntent) == 0 && Util.StringCompare(this.m_algoStrategy, l_theOther.m_algoStrategy) == 0?(!Util.VectorEqualsUnordered(this.m_algoParams, l_theOther.m_algoParams)?false:Util.VectorEqualsUnordered(this.m_smartComboRoutingParams, l_theOther.m_smartComboRoutingParams)):false):false);
        }
    }
}
