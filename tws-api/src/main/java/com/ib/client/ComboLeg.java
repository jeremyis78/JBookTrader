//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class ComboLeg {
    public static final int SAME = 0;
    public static final int OPEN = 1;
    public static final int CLOSE = 2;
    public static final int UNKNOWN = 3;
    public int m_conId;
    public int m_ratio;
    public String m_action;
    public String m_exchange;
    public int m_openClose;
    public int m_shortSaleSlot;
    public String m_designatedLocation;
    public int m_exemptCode;

    public ComboLeg() {
        this(0, 0, (String)null, (String)null, 0, 0, (String)null, -1);
    }

    public ComboLeg(int p_conId, int p_ratio, String p_action, String p_exchange, int p_openClose) {
        this(p_conId, p_ratio, p_action, p_exchange, p_openClose, 0, (String)null, -1);
    }

    public ComboLeg(int p_conId, int p_ratio, String p_action, String p_exchange, int p_openClose, int p_shortSaleSlot, String p_designatedLocation) {
        this(p_conId, p_ratio, p_action, p_exchange, p_openClose, p_shortSaleSlot, p_designatedLocation, -1);
    }

    public ComboLeg(int p_conId, int p_ratio, String p_action, String p_exchange, int p_openClose, int p_shortSaleSlot, String p_designatedLocation, int p_exemptCode) {
        this.m_conId = p_conId;
        this.m_ratio = p_ratio;
        this.m_action = p_action;
        this.m_exchange = p_exchange;
        this.m_openClose = p_openClose;
        this.m_shortSaleSlot = p_shortSaleSlot;
        this.m_designatedLocation = p_designatedLocation;
        this.m_exemptCode = p_exemptCode;
    }

    public boolean equals(Object p_other) {
        if(this == p_other) {
            return true;
        } else if(p_other == null) {
            return false;
        } else {
            ComboLeg l_theOther = (ComboLeg)p_other;
            return this.m_conId == l_theOther.m_conId && this.m_ratio == l_theOther.m_ratio && this.m_openClose == l_theOther.m_openClose && this.m_shortSaleSlot == l_theOther.m_shortSaleSlot && this.m_exemptCode == l_theOther.m_exemptCode?Util.StringCompareIgnCase(this.m_action, l_theOther.m_action) == 0 && Util.StringCompareIgnCase(this.m_exchange, l_theOther.m_exchange) == 0 && Util.StringCompareIgnCase(this.m_designatedLocation, l_theOther.m_designatedLocation) == 0:false;
        }
    }
}
