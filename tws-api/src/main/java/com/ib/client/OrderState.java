//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class OrderState {
    public String m_status;
    public String m_initMargin;
    public String m_maintMargin;
    public String m_equityWithLoan;
    public double m_commission;
    public double m_minCommission;
    public double m_maxCommission;
    public String m_commissionCurrency;
    public String m_warningText;

    OrderState() {
        this((String)null, (String)null, (String)null, (String)null, 0.0D, 0.0D, 0.0D, (String)null, (String)null);
    }

    OrderState(String status, String initMargin, String maintMargin, String equityWithLoan, double commission, double minCommission, double maxCommission, String commissionCurrency, String warningText) {
        this.m_initMargin = initMargin;
        this.m_maintMargin = maintMargin;
        this.m_equityWithLoan = equityWithLoan;
        this.m_commission = commission;
        this.m_minCommission = minCommission;
        this.m_maxCommission = maxCommission;
        this.m_commissionCurrency = commissionCurrency;
        this.m_warningText = warningText;
    }

    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(other == null) {
            return false;
        } else {
            OrderState state = (OrderState)other;
            return this.m_commission == state.m_commission && this.m_minCommission == state.m_minCommission && this.m_maxCommission == state.m_maxCommission?Util.StringCompare(this.m_status, state.m_status) == 0 && Util.StringCompare(this.m_initMargin, state.m_initMargin) == 0 && Util.StringCompare(this.m_maintMargin, state.m_maintMargin) == 0 && Util.StringCompare(this.m_equityWithLoan, state.m_equityWithLoan) == 0 && Util.StringCompare(this.m_commissionCurrency, state.m_commissionCurrency) == 0:false;
        }
    }
}
