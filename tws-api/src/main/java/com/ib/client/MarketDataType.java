//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class MarketDataType {
    public static final int REALTIME = 1;
    public static final int FROZEN = 2;

    public MarketDataType() {
    }

    public static String getField(int marketDataType) {
        switch(marketDataType) {
            case 1:
                return "Real-Time";
            case 2:
                return "Frozen";
            default:
                return "Unknown";
        }
    }

    public static String[] getFields() {
        int totalFields = MarketDataType.class.getFields().length;
        String[] fields = new String[totalFields];

        for(int i = 0; i < totalFields; ++i) {
            fields[i] = getField(i + 1);
        }

        return fields;
    }
}
