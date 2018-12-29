package com.jsystemtrader.client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.jsystemtrader.backtest.*;
import com.jsystemtrader.chart.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * Acts as a controller in the Model-View-Controller pattern
 */
public class Controller {
    private final MainFrame mainView;

    public Controller() throws JSystemTraderException {
        mainView = new MainFrame();
        assignListeners();
    }

    private void assignListeners() {
        mainView.connectAction(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Account.logOn(Account.TWS_MODE);
                } catch (Throwable t) {
                    Account.getLogger().write(t);
                    MessageDialog.showError(t.getMessage());
                } finally {
                    mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        mainView.backTestAction(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    BackTestDialog backTestDialog = new BackTestDialog(mainView);
                    if (!backTestDialog.getIsCancelled()) {
                        mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        String backTestFileName = backTestDialog.getBackTestFileName();
                        Account.setBackestFileName(backTestFileName);
                        Account.logOn(Account.BACK_TEST_MODE);
                    }
                } catch (Throwable t) {
                    Account.getLogger().write(t);
                    MessageDialog.showError(t.getMessage());
                } finally {
                    mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        mainView.exitAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Account.logOff();
            }
        });

        mainView.exitAction(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Account.logOff();
            }
        });

        mainView.aboutAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AboutDialog(mainView);
            }
        });

        mainView.strategyAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new StrategySelectionDialog(mainView);
                } catch (Exception ex) {
                    Account.getLogger().write(ex);
                    MessageDialog.showError(ex.getMessage());
                }
            }
        });

        mainView.strategyChartAction(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 2) {
                        mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        JTable table = (JTable) e.getSource();
                        TradingTableModel ttm = (TradingTableModel) table.getModel();
                        Strategy strategy = ttm.getStrategyForRow(table.getSelectedRow());
                        StrategyPerformanceChart spChart = new StrategyPerformanceChart(strategy);
                        JFrame chartFrame = spChart.getChartFrame(mainView);
                        chartFrame.setVisible(true);
                    }
                } catch (Exception ex) {
                    Account.getLogger().write(ex);
                    MessageDialog.showError(ex.toString());
                } finally {
                    mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }
        });
    }
}
