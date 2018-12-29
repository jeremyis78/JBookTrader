package com.jsystemtrader.backtest;

import java.text.*;
import java.util.*;

import com.ib.client.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

public class BackTestTraderAssistant extends TraderAssistant {
    public List<PriceBar> priceBars;

    public BackTestTraderAssistant(Trader trader) throws ParseException, JSystemTraderException {
        super(trader);
    }

    public void connect() throws ParseException, JSystemTraderException {
        eventLogger.write("Reading back test file", "Info", 1);
        BackTestFileReader reader = new BackTestFileReader(Account.getBackestFileName());

        priceBars = reader.getPriceBars();

        // get the last date in the historical data file
        PriceBar priceBar = priceBars.get(priceBars.size() - 1);

        Calendar lastDateInFile = Calendar.getInstance();
        lastDateInFile.setTimeInMillis(priceBar.getDate());
        Strategy.setLastHistoricalDate(lastDateInFile);

        String msg = priceBars.size() + " bars have been read successfully.";
        MessageDialog.showMessage(msg);

        eventLogger.write("Connected to back test", "Info", 1);
    }


    synchronized public void placeOrder(Contract contract, Order order, Strategy strategy) {
        orderID++;
        orders.put(orderID, new OrderStatus(order, strategy));
        strategy.getPositionManager().setOrderExecutionPending(true);
        String msg = strategy.getName() + ": Placed order " + orderID;
        eventLogger.write(msg, "Info", 1);

        double price = strategy.getLastPriceBar().getClose();
        Execution execution = new Execution();
        execution.m_price = price;
        execution.m_shares = order.m_totalQuantity;
        trader.execDetails(orderID, contract, execution);
    }


    public void realAccountCheck() throws JSystemTraderException {
        // Empty implementation
    }

}
