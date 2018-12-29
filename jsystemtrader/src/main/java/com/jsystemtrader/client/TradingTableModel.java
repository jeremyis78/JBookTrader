package com.jsystemtrader.client;

import java.text.*;
import java.util.*;

import com.jsystemtrader.platform.*;

/**
 */
public class TradingTableModel extends TableDataModel {
    private final Map<Strategy, Integer> rows = new HashMap<Strategy, Integer> ();
    DecimalFormat nf4;

    public TradingTableModel() {
        String[] tradingSchema = {"Strategy", "Ticker", "Type", "Exchange", "Bar Size", "Last Bar Time",
                                 "Last Bar Close", "Trades", "Position", "Realized P&L"};
        setSchema(tradingSchema);
        nf4 = (DecimalFormat) NumberFormat.getNumberInstance();
        nf4.setMaximumFractionDigits(4);
    }


    public Strategy getStrategyForRow(int row) {
        Strategy strategy = null;
        for (Map.Entry mapEntry : rows.entrySet()) {
            int thisRow = (Integer) mapEntry.getValue();
            if (thisRow == row) {
                strategy = (Strategy) mapEntry.getKey();
                break;
            }
        }
        return strategy;
    }


    synchronized public void updateStrategy(Strategy strategy) {
        int row = rows.get(strategy);
        PriceBar lastPriceBar = strategy.getLastPriceBar();
        double close = lastPriceBar.getClose();

        if (row >= 0 && lastPriceBar != null) {
            PositionManager positionManager = strategy.getPositionManager();
            setData(row, 5, lastPriceBar.getShortDate());
            setData(row, 6, close);
            setData(row, 7, positionManager.getTrades());
            setData(row, 8, positionManager.getPositionAsString());
            setData(row, 9, nf4.format(positionManager.getTotalProfitAndLoss()));
        }
    }

    public void addStrategy(Strategy strategy) {
        String item[] = new String[getColumnCount()];
        item[0] = strategy.getName();
        item[1] = strategy.getTicker();
        item[2] = strategy.getContract().m_secType;
        item[3] = strategy.getContract().m_exchange;
        item[4] = strategy.getBarSizeInSecs() / 60 + " Min";

        addData(item);
        rows.put(strategy, getRowCount() - 1);
    }
}
