//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

import java.util.Vector;

public class Contract implements Cloneable {
    public int m_conId;
    public String m_symbol;
    public String m_secType;
    public String m_expiry;
    public double m_strike;
    public String m_right;
    public String m_multiplier;
    public String m_exchange;
    public String m_currency;
    public String m_localSymbol;
    public String m_primaryExch;
    public boolean m_includeExpired;
    public String m_secIdType;
    public String m_secId;
    public String m_comboLegsDescrip;
    public Vector m_comboLegs = new Vector();
    public UnderComp m_underComp;

    public Contract() {
        this.m_conId = 0;
        this.m_strike = 0.0D;
        this.m_includeExpired = false;
    }

    public Object clone() throws CloneNotSupportedException {
        Contract retval = (Contract)super.clone();
        retval.m_comboLegs = (Vector)retval.m_comboLegs.clone();
        return retval;
    }

    public Contract(int p_conId, String p_symbol, String p_secType, String p_expiry, double p_strike, String p_right, String p_multiplier, String p_exchange, String p_currency, String p_localSymbol, Vector p_comboLegs, String p_primaryExch, boolean p_includeExpired, String p_secIdType, String p_secId) {
        this.m_conId = p_conId;
        this.m_symbol = p_symbol;
        this.m_secType = p_secType;
        this.m_expiry = p_expiry;
        this.m_strike = p_strike;
        this.m_right = p_right;
        this.m_multiplier = p_multiplier;
        this.m_exchange = p_exchange;
        this.m_currency = p_currency;
        this.m_includeExpired = p_includeExpired;
        this.m_localSymbol = p_localSymbol;
        this.m_comboLegs = p_comboLegs;
        this.m_primaryExch = p_primaryExch;
        this.m_secIdType = p_secIdType;
        this.m_secId = p_secId;
    }

    public boolean equals(Object p_other) {
        if(this == p_other) {
            return true;
        } else if(p_other != null && p_other instanceof Contract) {
            Contract l_theOther = (Contract)p_other;
            if(this.m_conId != l_theOther.m_conId) {
                return false;
            } else if(Util.StringCompare(this.m_secType, l_theOther.m_secType) != 0) {
                return false;
            } else if(Util.StringCompare(this.m_symbol, l_theOther.m_symbol) == 0 && Util.StringCompare(this.m_exchange, l_theOther.m_exchange) == 0 && Util.StringCompare(this.m_primaryExch, l_theOther.m_primaryExch) == 0 && Util.StringCompare(this.m_currency, l_theOther.m_currency) == 0) {
                if(!Util.NormalizeString(this.m_secType).equals("BOND")) {
                    if(this.m_strike != l_theOther.m_strike) {
                        return false;
                    }

                    if(Util.StringCompare(this.m_expiry, l_theOther.m_expiry) != 0 || Util.StringCompare(this.m_right, l_theOther.m_right) != 0 || Util.StringCompare(this.m_multiplier, l_theOther.m_multiplier) != 0 || Util.StringCompare(this.m_localSymbol, l_theOther.m_localSymbol) != 0) {
                        return false;
                    }
                }

                if(Util.StringCompare(this.m_secIdType, l_theOther.m_secIdType) != 0) {
                    return false;
                } else if(Util.StringCompare(this.m_secId, l_theOther.m_secId) != 0) {
                    return false;
                } else if(!Util.VectorEqualsUnordered(this.m_comboLegs, l_theOther.m_comboLegs)) {
                    return false;
                } else {
                    if(this.m_underComp != l_theOther.m_underComp) {
                        if(this.m_underComp == null || l_theOther.m_underComp == null) {
                            return false;
                        }

                        if(!this.m_underComp.equals(l_theOther.m_underComp)) {
                            return false;
                        }
                    }

                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
