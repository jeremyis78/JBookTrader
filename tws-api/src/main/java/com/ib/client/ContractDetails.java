//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class ContractDetails {
    public Contract m_summary;
    public String m_marketName;
    public String m_tradingClass;
    public double m_minTick;
    public int m_priceMagnifier;
    public String m_orderTypes;
    public String m_validExchanges;
    public int m_underConId;
    public String m_longName;
    public String m_contractMonth;
    public String m_industry;
    public String m_category;
    public String m_subcategory;
    public String m_timeZoneId;
    public String m_tradingHours;
    public String m_liquidHours;
    public String m_cusip;
    public String m_ratings;
    public String m_descAppend;
    public String m_bondType;
    public String m_couponType;
    public boolean m_callable = false;
    public boolean m_putable = false;
    public double m_coupon = 0.0D;
    public boolean m_convertible = false;
    public String m_maturity;
    public String m_issueDate;
    public String m_nextOptionDate;
    public String m_nextOptionType;
    public boolean m_nextOptionPartial = false;
    public String m_notes;

    public ContractDetails() {
        this.m_summary = new Contract();
        this.m_minTick = 0.0D;
        this.m_underConId = 0;
    }

    public ContractDetails(Contract p_summary, String p_marketName, String p_tradingClass, double p_minTick, String p_orderTypes, String p_validExchanges, int p_underConId, String p_longName, String p_contractMonth, String p_industry, String p_category, String p_subcategory, String p_timeZoneId, String p_tradingHours, String p_liquidHours) {
        this.m_summary = p_summary;
        this.m_marketName = p_marketName;
        this.m_tradingClass = p_tradingClass;
        this.m_minTick = p_minTick;
        this.m_orderTypes = p_orderTypes;
        this.m_validExchanges = p_validExchanges;
        this.m_underConId = p_underConId;
        this.m_longName = p_longName;
        this.m_contractMonth = p_contractMonth;
        this.m_industry = p_industry;
        this.m_category = p_category;
        this.m_subcategory = p_subcategory;
        this.m_timeZoneId = p_timeZoneId;
        this.m_tradingHours = p_tradingHours;
        this.m_liquidHours = p_liquidHours;
    }
}
