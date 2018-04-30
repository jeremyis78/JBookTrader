//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class ExecutionFilter {
    public int m_clientId;
    public String m_acctCode;
    public String m_time;
    public String m_symbol;
    public String m_secType;
    public String m_exchange;
    public String m_side;

    public ExecutionFilter() {
        this.m_clientId = 0;
    }

    public ExecutionFilter(int p_clientId, String p_acctCode, String p_time, String p_symbol, String p_secType, String p_exchange, String p_side) {
        this.m_clientId = p_clientId;
        this.m_acctCode = p_acctCode;
        this.m_time = p_time;
        this.m_symbol = p_symbol;
        this.m_secType = p_secType;
        this.m_exchange = p_exchange;
        this.m_side = p_side;
    }

    public boolean equals(Object p_other) {
        boolean l_bRetVal = false;
        if(p_other == null) {
            l_bRetVal = false;
        } else if(this == p_other) {
            l_bRetVal = true;
        } else {
            ExecutionFilter l_theOther = (ExecutionFilter)p_other;
            l_bRetVal = this.m_clientId == l_theOther.m_clientId && this.m_acctCode.equalsIgnoreCase(l_theOther.m_acctCode) && this.m_time.equalsIgnoreCase(l_theOther.m_time) && this.m_symbol.equalsIgnoreCase(l_theOther.m_symbol) && this.m_secType.equalsIgnoreCase(l_theOther.m_secType) && this.m_exchange.equalsIgnoreCase(l_theOther.m_exchange) && this.m_side.equalsIgnoreCase(l_theOther.m_side);
        }

        return l_bRetVal;
    }
}
