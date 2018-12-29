package com.jsystemtrader.client;

import java.util.*;

import com.jsystemtrader.platform.*;

/**
 * Strategy selection table
 */
public class StrategySelectionTable extends TableDataModel {
    private static final int TRADE_COLUMN_INDEX = 5;
    private static final String[] SCHEMA = {"Strategy Name", "Symbol", "Type", "Exchange", "Bar Size", "Trade"};
    private static final int STRATEGY_COLUMN_INDEX = SCHEMA.length;

    public StrategySelectionTable() {
        setSchema(SCHEMA);
    }

    /*
     * If this method is not implemented, the "Trade" column would contain text
     * ("true"/"false"), rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        // only the "Trade" column can be edited
        return (col == TRADE_COLUMN_INDEX) ? true : false;
    }

    public void setValueAt(Object value, int row, int col) {
        Object[] changedItem = (Object[]) data.get(row);
        changedItem[col] = value;
        fireTableCellUpdated(row, col);
    }

    public ArrayList<Strategy> getSelectedStrategies() {
        ArrayList<Strategy> selectedStrategies = new ArrayList<Strategy> ();

        int rows = getRowCount();
        for (int i = 0; i < rows; i++) {
            Object[] row = (Object[]) data.get(i);
            boolean isSelected = ( (Boolean) row[TRADE_COLUMN_INDEX]).booleanValue();
            if (isSelected) {
                Strategy strategy = (Strategy) row[STRATEGY_COLUMN_INDEX];
                selectedStrategies.add(strategy);
            }
        }
        return selectedStrategies;
    }

    public void addStrategy(Class clazz) throws InstantiationException, IllegalAccessException, JSystemTraderException {
        Object item[] = new Object[getColumnCount() + 1];
        Strategy strategy = (Strategy) clazz.newInstance();

        if (strategy != null) {
            item[0] = strategy.getName();
            item[1] = strategy.getTicker();
            item[2] = strategy.getContract().m_secType;
            item[3] = strategy.getContract().m_exchange;
            item[4] = PriceBar.barSizeToString(strategy.getBarSize());
            item[5] = new Boolean(false);
            item[6] = strategy; // not visible

            addData(item);
        }
    }
}
