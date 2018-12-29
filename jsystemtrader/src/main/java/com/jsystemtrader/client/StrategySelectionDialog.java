package com.jsystemtrader.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.jsystemtrader.backtest.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * Dialog to select strategies to trade.
 */
public class StrategySelectionDialog extends JDialog {
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton okButton = new JButton("Start Trading");
    private final StrategySelectionTable strategiesModel = new StrategySelectionTable();

    public StrategySelectionDialog(JFrame parent) throws JSystemTraderException, FileNotFoundException, IOException {
        super(parent);
        jbInit();
        pack();
        assignListeners();
        setLocationRelativeTo(parent);
        populateStrategies();
        setVisible(true);
    }

    private void populateStrategies() throws JSystemTraderException {
        try {
            ClassFinder classFinder = new ClassFinder();
            List<Class>
                    strategies = classFinder.getClasses("com.jsystemtrader.strategy", "com.jsystemtrader.platform.Strategy");
            for (Class strategyClass : strategies) {
                strategiesModel.addStrategy(strategyClass);
            }
        } catch (Exception e) {
            throw new JSystemTraderException("Could not populate strategies: " + e.getMessage());
        }
    }


    private void assignListeners() {

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Strategy> selectedStrategies = strategiesModel.getSelectedStrategies();
                    if (selectedStrategies.size() == 0) {
                        MessageDialog.showError("At least one strategy must be selected for trading.");
                        return;
                    }

                    for (Strategy strategy : selectedStrategies) {
                        if (Account.getMode() == Account.BACK_TEST_MODE) {
                            new BackTestStrategyRunner(strategy);
                        } else {
                            new StrategyRunner(strategy);
                        }
                    }
                    dispose();
                    MainFrame mainFrame = (MainFrame) StrategySelectionDialog.this.getOwner();
                    mainFrame.disableAddStrategyMenu();

                } catch (Exception ex) {
                    Account.getLogger().write(ex);
                    String msg = "Could not add strategies. Exception: " + ex.getMessage();
                    MessageDialog.showError(msg);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void jbInit() throws JSystemTraderException, FileNotFoundException, IOException {

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setTitle("Strategy Selection");

        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel();

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        JScrollPane strategiesScroll = new JScrollPane();
        strategiesScroll.setAutoscrolls(true);

        JTable strategiesTable = new JTable(strategiesModel);
        strategiesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        strategiesScroll.getViewport().add(strategiesTable);

        contentPanel.add(strategiesScroll, BorderLayout.CENTER);

        controlPanel.add(okButton);
        controlPanel.add(cancelButton);

        getContentPane().setPreferredSize(new Dimension(600, 350));
    }
}
