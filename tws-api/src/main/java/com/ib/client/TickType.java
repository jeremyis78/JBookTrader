//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class TickType {
    public static final int BID_SIZE = 0;
    public static final int BID = 1;
    public static final int ASK = 2;
    public static final int ASK_SIZE = 3;
    public static final int LAST = 4;
    public static final int LAST_SIZE = 5;
    public static final int HIGH = 6;
    public static final int LOW = 7;
    public static final int VOLUME = 8;
    public static final int CLOSE = 9;
    public static final int BID_OPTION = 10;
    public static final int ASK_OPTION = 11;
    public static final int LAST_OPTION = 12;
    public static final int MODEL_OPTION = 13;
    public static final int OPEN = 14;
    public static final int LOW_13_WEEK = 15;
    public static final int HIGH_13_WEEK = 16;
    public static final int LOW_26_WEEK = 17;
    public static final int HIGH_26_WEEK = 18;
    public static final int LOW_52_WEEK = 19;
    public static final int HIGH_52_WEEK = 20;
    public static final int AVG_VOLUME = 21;
    public static final int OPEN_INTEREST = 22;
    public static final int OPTION_HISTORICAL_VOL = 23;
    public static final int OPTION_IMPLIED_VOL = 24;
    public static final int OPTION_BID_EXCH = 25;
    public static final int OPTION_ASK_EXCH = 26;
    public static final int OPTION_CALL_OPEN_INTEREST = 27;
    public static final int OPTION_PUT_OPEN_INTEREST = 28;
    public static final int OPTION_CALL_VOLUME = 29;
    public static final int OPTION_PUT_VOLUME = 30;
    public static final int INDEX_FUTURE_PREMIUM = 31;
    public static final int BID_EXCH = 32;
    public static final int ASK_EXCH = 33;
    public static final int AUCTION_VOLUME = 34;
    public static final int AUCTION_PRICE = 35;
    public static final int AUCTION_IMBALANCE = 36;
    public static final int MARK_PRICE = 37;
    public static final int BID_EFP_COMPUTATION = 38;
    public static final int ASK_EFP_COMPUTATION = 39;
    public static final int LAST_EFP_COMPUTATION = 40;
    public static final int OPEN_EFP_COMPUTATION = 41;
    public static final int HIGH_EFP_COMPUTATION = 42;
    public static final int LOW_EFP_COMPUTATION = 43;
    public static final int CLOSE_EFP_COMPUTATION = 44;
    public static final int LAST_TIMESTAMP = 45;
    public static final int SHORTABLE = 46;
    public static final int FUNDAMENTAL_RATIOS = 47;
    public static final int RT_VOLUME = 48;
    public static final int HALTED = 49;
    public static final int BID_YIELD = 50;
    public static final int ASK_YIELD = 51;
    public static final int LAST_YIELD = 52;
    public static final int CUST_OPTION_COMPUTATION = 53;
    public static final int TRADE_COUNT = 54;
    public static final int TRADE_RATE = 55;
    public static final int VOLUME_RATE = 56;
    public static final int LAST_RTH_TRADE = 57;

    public TickType() {
    }

    public static String getField(int tickType) {
        switch(tickType) {
            case 0:
                return "bidSize";
            case 1:
                return "bidPrice";
            case 2:
                return "askPrice";
            case 3:
                return "askSize";
            case 4:
                return "lastPrice";
            case 5:
                return "lastSize";
            case 6:
                return "high";
            case 7:
                return "low";
            case 8:
                return "volume";
            case 9:
                return "close";
            case 10:
                return "bidOptComp";
            case 11:
                return "askOptComp";
            case 12:
                return "lastOptComp";
            case 13:
                return "modelOptComp";
            case 14:
                return "open";
            case 15:
                return "13WeekLow";
            case 16:
                return "13WeekHigh";
            case 17:
                return "26WeekLow";
            case 18:
                return "26WeekHigh";
            case 19:
                return "52WeekLow";
            case 20:
                return "52WeekHigh";
            case 21:
                return "AvgVolume";
            case 22:
                return "OpenInterest";
            case 23:
                return "OptionHistoricalVolatility";
            case 24:
                return "OptionImpliedVolatility";
            case 25:
                return "OptionBidExchStr";
            case 26:
                return "OptionAskExchStr";
            case 27:
                return "OptionCallOpenInterest";
            case 28:
                return "OptionPutOpenInterest";
            case 29:
                return "OptionCallVolume";
            case 30:
                return "OptionPutVolume";
            case 31:
                return "IndexFuturePremium";
            case 32:
                return "bidExch";
            case 33:
                return "askExch";
            case 34:
                return "auctionVolume";
            case 35:
                return "auctionPrice";
            case 36:
                return "auctionImbalance";
            case 37:
                return "markPrice";
            case 38:
                return "bidEFP";
            case 39:
                return "askEFP";
            case 40:
                return "lastEFP";
            case 41:
                return "openEFP";
            case 42:
                return "highEFP";
            case 43:
                return "lowEFP";
            case 44:
                return "closeEFP";
            case 45:
                return "lastTimestamp";
            case 46:
                return "shortable";
            case 47:
                return "fundamentals";
            case 48:
                return "RTVolume";
            case 49:
                return "halted";
            case 50:
                return "bidYield";
            case 51:
                return "askYield";
            case 52:
                return "lastYield";
            case 53:
                return "custOptComp";
            case 54:
                return "trades";
            case 55:
                return "trades/min";
            case 56:
                return "volume/min";
            case 57:
                return "lastRTHTrade";
            default:
                return "unknown";
        }
    }
}
