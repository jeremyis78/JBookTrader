package com.jbooktrader.platform.util.contract;

import com.ib.client.*;

/**
 * Provides convenience methods to create stock, futures, and Forex contracts
 *
 * @author Eugene Kononov
 */
public class ContractFactory {

    public static Contract makeContract(String symbol, Types.SecType securityType, String exchange, String currency) {
        Contract contract = new Contract();

        contract.symbol(symbol);
        contract.secType(securityType);
        contract.exchange(exchange);
        contract.currency(currency);

        return contract;
    }

    public static Contract makeStockContract(String symbol, String exchange, String currency) {
        return makeContract(symbol, Types.SecType.STK, exchange, currency);
    }

    public static Contract makeStockContract(String symbol, String exchange) {
        return makeStockContract(symbol, exchange, null);
    }

    public static Contract makeFutureContract(String symbol, String exchange, String currency) {
        return makeContract(symbol, Types.SecType.FUT, exchange, currency);
    }

    public static Contract makeFutureContract(String symbol, String exchange) {
        return makeFutureContract(symbol, exchange, null);
    }

    public static Contract makeCashContract(String symbol, String currency) {
        return makeContract(symbol, Types.SecType.CASH, "IDEALPRO", currency);
    }

    public static Contract makeIndexContract(String symbol, String exchange) {
        return makeContract(symbol, Types.SecType.IND, exchange, null);
    }

}
