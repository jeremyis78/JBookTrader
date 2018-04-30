//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class UnderComp {
    public int m_conId = 0;
    public double m_delta = 0.0D;
    public double m_price = 0.0D;

    public UnderComp() {
    }

    public boolean equals(Object p_other) {
        if(this == p_other) {
            return true;
        } else if(p_other != null && p_other instanceof UnderComp) {
            UnderComp l_theOther = (UnderComp)p_other;
            return this.m_conId != l_theOther.m_conId?false:(this.m_delta != l_theOther.m_delta?false:this.m_price == l_theOther.m_price);
        } else {
            return false;
        }
    }
}
