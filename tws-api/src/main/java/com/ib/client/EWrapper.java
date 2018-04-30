//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ib.client;

public interface EWrapper extends AnyWrapper {
    void tickPrice(int var1, int var2, double var3, int var5);

    void tickSize(int var1, int var2, int var3);

    void tickOptionComputation(int var1, int var2, double var3, double var5, double var7, double var9, double var11, double var13, double var15, double var17);

    void tickGeneric(int var1, int var2, double var3);

    void tickString(int var1, int var2, String var3);

    void tickEFP(int var1, int var2, double var3, String var5, double var6, int var8, String var9, double var10, double var12);

    void orderStatus(int var1, String var2, int var3, int var4, double var5, int var7, int var8, double var9, int var11, String var12);

    void openOrder(int var1, Contract var2, Order var3, OrderState var4);

    void openOrderEnd();

    void updateAccountValue(String var1, String var2, String var3, String var4);

    void updatePortfolio(Contract var1, int var2, double var3, double var5, double var7, double var9, double var11, String var13);

    void updateAccountTime(String var1);

    void accountDownloadEnd(String var1);

    void nextValidId(int var1);

    void contractDetails(int var1, ContractDetails var2);

    void bondContractDetails(int var1, ContractDetails var2);

    void contractDetailsEnd(int var1);

    void execDetails(int var1, Contract var2, Execution var3);

    void execDetailsEnd(int var1);

    void updateMktDepth(int var1, int var2, int var3, int var4, double var5, int var7);

    void updateMktDepthL2(int var1, int var2, String var3, int var4, int var5, double var6, int var8);

    void updateNewsBulletin(int var1, int var2, String var3, String var4);

    void managedAccounts(String var1);

    void receiveFA(int var1, String var2);

    void historicalData(int var1, String var2, double var3, double var5, double var7, double var9, int var11, int var12, double var13, boolean var15);

    void scannerParameters(String var1);

    void scannerData(int var1, int var2, ContractDetails var3, String var4, String var5, String var6, String var7);

    void scannerDataEnd(int var1);

    void realtimeBar(int var1, long var2, double var4, double var6, double var8, double var10, long var12, double var14, int var16);

    void currentTime(long var1);

    void fundamentalData(int var1, String var2);

    void deltaNeutralValidation(int var1, UnderComp var2);

    void tickSnapshotEnd(int var1);

    void marketDataType(int var1, int var2);
}
