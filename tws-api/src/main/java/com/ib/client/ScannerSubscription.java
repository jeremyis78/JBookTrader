//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public class ScannerSubscription {
    public static final int NO_ROW_NUMBER_SPECIFIED = -1;
    private int m_numberOfRows = -1;
    private String m_instrument;
    private String m_locationCode;
    private String m_scanCode;
    private double m_abovePrice = 1.7976931348623157E308D;
    private double m_belowPrice = 1.7976931348623157E308D;
    private int m_aboveVolume = 2147483647;
    private int m_averageOptionVolumeAbove = 2147483647;
    private double m_marketCapAbove = 1.7976931348623157E308D;
    private double m_marketCapBelow = 1.7976931348623157E308D;
    private String m_moodyRatingAbove;
    private String m_moodyRatingBelow;
    private String m_spRatingAbove;
    private String m_spRatingBelow;
    private String m_maturityDateAbove;
    private String m_maturityDateBelow;
    private double m_couponRateAbove = 1.7976931348623157E308D;
    private double m_couponRateBelow = 1.7976931348623157E308D;
    private String m_excludeConvertible;
    private String m_scannerSettingPairs;
    private String m_stockTypeFilter;

    public ScannerSubscription() {
    }

    public int numberOfRows() {
        return this.m_numberOfRows;
    }

    public String instrument() {
        return this.m_instrument;
    }

    public String locationCode() {
        return this.m_locationCode;
    }

    public String scanCode() {
        return this.m_scanCode;
    }

    public double abovePrice() {
        return this.m_abovePrice;
    }

    public double belowPrice() {
        return this.m_belowPrice;
    }

    public int aboveVolume() {
        return this.m_aboveVolume;
    }

    public int averageOptionVolumeAbove() {
        return this.m_averageOptionVolumeAbove;
    }

    public double marketCapAbove() {
        return this.m_marketCapAbove;
    }

    public double marketCapBelow() {
        return this.m_marketCapBelow;
    }

    public String moodyRatingAbove() {
        return this.m_moodyRatingAbove;
    }

    public String moodyRatingBelow() {
        return this.m_moodyRatingBelow;
    }

    public String spRatingAbove() {
        return this.m_spRatingAbove;
    }

    public String spRatingBelow() {
        return this.m_spRatingBelow;
    }

    public String maturityDateAbove() {
        return this.m_maturityDateAbove;
    }

    public String maturityDateBelow() {
        return this.m_maturityDateBelow;
    }

    public double couponRateAbove() {
        return this.m_couponRateAbove;
    }

    public double couponRateBelow() {
        return this.m_couponRateBelow;
    }

    public String excludeConvertible() {
        return this.m_excludeConvertible;
    }

    public String scannerSettingPairs() {
        return this.m_scannerSettingPairs;
    }

    public String stockTypeFilter() {
        return this.m_stockTypeFilter;
    }

    public void numberOfRows(int num) {
        this.m_numberOfRows = num;
    }

    public void instrument(String txt) {
        this.m_instrument = txt;
    }

    public void locationCode(String txt) {
        this.m_locationCode = txt;
    }

    public void scanCode(String txt) {
        this.m_scanCode = txt;
    }

    public void abovePrice(double price) {
        this.m_abovePrice = price;
    }

    public void belowPrice(double price) {
        this.m_belowPrice = price;
    }

    public void aboveVolume(int volume) {
        this.m_aboveVolume = volume;
    }

    public void averageOptionVolumeAbove(int volume) {
        this.m_averageOptionVolumeAbove = volume;
    }

    public void marketCapAbove(double cap) {
        this.m_marketCapAbove = cap;
    }

    public void marketCapBelow(double cap) {
        this.m_marketCapBelow = cap;
    }

    public void moodyRatingAbove(String r) {
        this.m_moodyRatingAbove = r;
    }

    public void moodyRatingBelow(String r) {
        this.m_moodyRatingBelow = r;
    }

    public void spRatingAbove(String r) {
        this.m_spRatingAbove = r;
    }

    public void spRatingBelow(String r) {
        this.m_spRatingBelow = r;
    }

    public void maturityDateAbove(String d) {
        this.m_maturityDateAbove = d;
    }

    public void maturityDateBelow(String d) {
        this.m_maturityDateBelow = d;
    }

    public void couponRateAbove(double r) {
        this.m_couponRateAbove = r;
    }

    public void couponRateBelow(double r) {
        this.m_couponRateBelow = r;
    }

    public void excludeConvertible(String c) {
        this.m_excludeConvertible = c;
    }

    public void scannerSettingPairs(String val) {
        this.m_scannerSettingPairs = val;
    }

    public void stockTypeFilter(String val) {
        this.m_stockTypeFilter = val;
    }
}
