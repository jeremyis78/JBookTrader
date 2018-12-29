package com.jsystemtrader.client;

import java.util.*;

import javax.swing.table.*;

public class TableDataModel extends AbstractTableModel {
    private String[] schema;
    protected final List<Object> data;

    public TableDataModel() {
        data = new ArrayList<Object> ();
    }

    public void addData(Object[] item) {
        data.add(item);
        fireTableDataChanged();
    }

    public void setData(int row, int column, Object value) {
        String item[] = (String[]) data.get(row);
        item[column] = String.valueOf(value);
        data.set(row, item);
        fireTableCellUpdated(row, column);
    }

    public void removeData(int row) {
        data.remove(row);
        fireTableDataChanged();
    }

    public void setSchema(String[] schema) {
        this.schema = schema;
        fireTableStructureChanged();
    }

    public Object getValueAt(int row, int column) {
        Object item[] = (Object[]) data.get(row);
        return item[column];
    }

    public String getColumnName(int index) {
        return schema[index];
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return (schema == null) ? 0 : schema.length;
    }
}
