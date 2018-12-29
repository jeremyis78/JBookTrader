package com.jsystemtrader.client;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;
import javax.swing.table.*;

import com.jsystemtrader.platform.*;
import com.jsystemtrader.platform.ModelListener;

/**
 * Main application window
 */
public class MainFrame extends JFrame implements ModelListener {

    private JMenuItem connectMenuItem, backTestMenuItem, addStrategyMenuItem, exitMenuItem, aboutMenuItem;
    private JPanel contentPanel;
    private JLabel statusLabel;
    private TradingTableModel tradingTableModel;
    private JTable tradingTable;

    public MainFrame() throws JSystemTraderException {
        Account.addListener(this);
        jbInit();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void modelChanged(int key, Object value) {
        switch (key) {
            case ModelListener.ACTIVITY:
                statusLabel.setText( (String) value);
                break;
            case ModelListener.CONNECTED_TO_TWS:
                addStrategyMenuItem.setEnabled(true);
                setTitle(getTitle() + " - " + value);

                // prevent from logging on twice
                connectMenuItem.setEnabled(false);
                backTestMenuItem.setEnabled(false);
                break;
            case ModelListener.TRADING_DECISION:
                Strategy strategy = (Strategy) value;
                tradingTableModel.updateStrategy(strategy);
                break;
            case ModelListener.STRATEGY_ADDED:
                Strategy addedStrategy = (Strategy) value;
                tradingTableModel.addStrategy(addedStrategy);
                break;
        }
    }

    public void disableAddStrategyMenu() {
        addStrategyMenuItem.setEnabled(false);
    }

    public void connectAction(ActionListener action) {
        connectMenuItem.addActionListener(action);
    }

    public void backTestAction(ActionListener action) {
        backTestMenuItem.addActionListener(action);
    }


    public void exitAction(ActionListener action) {
        exitMenuItem.addActionListener(action);
    }

    public void exitAction(WindowAdapter action) {
        addWindowListener(action);
    }

    public void strategyAction(ActionListener action) {
        addStrategyMenuItem.addActionListener(action);
    }

    public void aboutAction(ActionListener action) {
        aboutMenuItem.addActionListener(action);
    }

    public void strategyChartAction(MouseAdapter ma) {
        tradingTable.addMouseListener(ma);
    }

    private URL getImageURL(String imageFileName) throws JSystemTraderException {
        URL imgURL = ClassLoader.getSystemResource(imageFileName);
        if (imgURL == null) {
            String msg = "Could not locate " + imageFileName +
                         ". Make sure the JSystemTrader directory is in the classpath.";
            throw new JSystemTraderException(msg);
        }
        return imgURL;
    }

    private void jbInit() throws JSystemTraderException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // file menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        connectMenuItem = new JMenuItem("Connect to TWS", new ImageIcon(getImageURL("resources/tws.jpg")));
        connectMenuItem.setMnemonic('C');

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic('X');

        backTestMenuItem = new JMenuItem("Back Test...");
        exitMenuItem.setMnemonic('B');

        fileMenu.add(connectMenuItem);
        fileMenu.add(backTestMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        // trading menu
        JMenu tradingMenu = new JMenu("Trading");
        tradingMenu.setMnemonic('T');
        addStrategyMenuItem = new JMenuItem("Strategies...");
        addStrategyMenuItem.setMnemonic('S');
        addStrategyMenuItem.setEnabled(false);
        tradingMenu.add(addStrategyMenuItem);

        // help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        aboutMenuItem = new JMenuItem("About " + JSystemTrader.APP_NAME + "...");
        aboutMenuItem.setMnemonic('A');
        helpMenu.add(aboutMenuItem);

        // status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel();
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusLabel.setText("Status");
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(tradingMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new BorderLayout());
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // content panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(statusPanel, BorderLayout.SOUTH);
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        JScrollPane tradingScroll = new JScrollPane();
        tradingScroll.setAutoscrolls(true);
        JPanel tradingPanel = new JPanel(new BorderLayout());
        JLabel tradingLabel = new JLabel("Trading Strategies");
        tradingPanel.add(tradingLabel, BorderLayout.NORTH);
        tradingPanel.add(tradingScroll, BorderLayout.CENTER);

        tradingTableModel = new TradingTableModel();
        tradingTable = new JTable(tradingTableModel);

        // Make some columns wider than the rest, so that the info fits in.
        TableColumnModel columnModel = tradingTable.getColumnModel();
        int width = 100;
        // strategy name column
        columnModel.getColumn(0).setPreferredWidth(width);
        // last bar time column
        columnModel.getColumn(5).setPreferredWidth(120);
        // last bar close column
        columnModel.getColumn(6).setPreferredWidth(width);

        tradingScroll.getViewport().add(tradingTable);
        mainPanel.add(tradingPanel, BorderLayout.CENTER);

        Image appIcon = Toolkit.getDefaultToolkit().getImage(getImageURL("resources/JSystemTrader.jpg"));
        setIconImage(appIcon);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().setPreferredSize(new Dimension(850, 450));
        setTitle(JSystemTrader.APP_NAME);
        pack();
    }
}
