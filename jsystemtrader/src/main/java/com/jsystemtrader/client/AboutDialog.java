package com.jsystemtrader.client;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.jsystemtrader.platform.*;

/**
 * Dialog to show the application info, system info, and IB info.
 */
public class AboutDialog extends JDialog {

    /* inner class to define the "about" model */
    private class AboutTableModel extends TableDataModel {
        public AboutTableModel() {
            String[] aboutSchema = {"Name", "Value"};
            setSchema(aboutSchema);
        }
    }


    public AboutDialog(JFrame parent) {
        super(parent);
        jbInit();
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void jbInit() {
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("About " + JSystemTrader.APP_NAME);

        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel aboutContainer = new JPanel(new BorderLayout(5, 5));

        JPanel aboutPanel = new JPanel(new BorderLayout(10, 5));
        LayoutManager gridLayout = new GridLayout(6, 0, 5, 5);
        JPanel labelsPanel = new JPanel(gridLayout);
        JPanel valuesPanel = new JPanel(gridLayout);

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JTabbedPane tabbedPane1 = new JTabbedPane();
        contentPanel.add(tabbedPane1, BorderLayout.CENTER);
        tabbedPane1.addTab("About", aboutContainer);
        aboutContainer.add(aboutPanel, BorderLayout.NORTH);
        aboutPanel.add(labelsPanel, BorderLayout.WEST);
        aboutPanel.add(valuesPanel, BorderLayout.CENTER);

        // labels
        labelsPanel.add(new JLabel("Product:"));
        labelsPanel.add(new JLabel("Version:"));
        labelsPanel.add(new JLabel("Author:"));
        labelsPanel.add(new JLabel("Email:"));
        labelsPanel.add(new JLabel("License:"));
        labelsPanel.add(new JLabel("Support:"));

        // values
        valuesPanel.add(new JLabel(JSystemTrader.APP_NAME));
        valuesPanel.add(new JLabel("4.08, December 27, 2006"));
        valuesPanel.add(new JLabel("Eugene Kononov"));
        valuesPanel.add(new JLabel("nonlinear5@yahoo.com"));
        valuesPanel.add(new JLabel("BSD (Open Source)"));
        valuesPanel.add(new JLabel("http://www.interactivebrokers.com/cgi-bin/discus/board-auth.pl?file=/2/37888.html"));

        JPanel ibContainer = new JPanel(new BorderLayout(5, 5));
        JPanel ibPanel = new JPanel(new BorderLayout(10, 5));
        JPanel ibLabelsPanel = new JPanel(gridLayout);
        tabbedPane1.addTab("API Info", ibContainer);
        ibContainer.add(ibPanel, BorderLayout.NORTH);
        ibPanel.add(ibLabelsPanel, BorderLayout.WEST);
        ibLabelsPanel.add(new JLabel("Server Version:"));

        JPanel ibValuesPanel = new JPanel(gridLayout);

        ibPanel.add(ibValuesPanel, BorderLayout.CENTER);
        String serverVersion = "Not connected";
        Trader trader = Account.getTrader();
        if (trader != null) {
            serverVersion = String.valueOf(trader.getAssistant().getServerVersion());
        }
        ibValuesPanel.add(new JLabel(serverVersion));

        JPanel systemInfoPanel = new JPanel(new BorderLayout(5, 5));
        tabbedPane1.addTab("System Info", systemInfoPanel);

        JScrollPane systemInfoScrollPane = new JScrollPane();
        systemInfoPanel.add(systemInfoScrollPane, BorderLayout.CENTER);

        TableDataModel aboutModel = new AboutTableModel();
        systemInfoScrollPane.getViewport().add(new JTable(aboutModel));

        getContentPane().setPreferredSize(new Dimension(500, 350));

        Properties properties = System.getProperties();
        Enumeration propNames = properties.propertyNames();

        while (propNames.hasMoreElements()) {
            String key = (String) propNames.nextElement();
            String value = properties.getProperty(key);
            String[] item = {key, value};
            aboutModel.addData(item);
        }
    }
}
