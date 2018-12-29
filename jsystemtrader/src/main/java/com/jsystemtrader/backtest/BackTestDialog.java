package com.jsystemtrader.backtest;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.jsystemtrader.client.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;

/**
 * Dialog to specify GUI options back testing using historical data file.
 */
public class BackTestDialog extends JDialog {
    /** Minimum frame size */
    private static final Dimension MIN_SIZE = new Dimension(560, 150);

    private static final int COMPONENT_HEIGHT = 23;
    private static final int COMPONENT_WIDTH = 100;

    private JButton cancelButton, okButton, selectFileButton;
    private JTextField fileNameText, maxTradesText;
    private JComboBox logCombo;
    private boolean isCancelled;

    public BackTestDialog(JFrame parent) throws JSystemTraderException, FileNotFoundException, IOException {
        super(parent);
        jbInit();
        pack();
        assignListeners();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public boolean getIsCancelled() {
        return isCancelled;
    }

    private void setOptions() throws JSystemTraderException {

        String historicalFileName = fileNameText.getText();

        File file = new File(historicalFileName);
        if (!file.exists()) {
            String msg = "Historical file " + "\"" + historicalFileName + "\"" + " does not exist.";
            throw new JSystemTraderException(msg);
        }

        int maxTrades = 0;
        try {
            maxTrades = Integer.parseInt(maxTradesText.getText());
        } catch (NumberFormatException nfe) {
            String msg = "\"" + maxTradesText.getText() + "\"" + " is not valid.";
            throw new JSystemTraderException(msg);

        }

        if (maxTrades < 1) {
            String msg = "\"" + "Max trades" + "\"" + " must be greater or equal to 1.";
            throw new JSystemTraderException(msg);
        }

        Strategy.setIsBackTest(true);
        Strategy.setBackTestMaxTrades(maxTrades);

        boolean islogDisabled = (logCombo.getSelectedIndex() == 1);
        if (islogDisabled) {
            HTMLLog.disable();
        }

    }

    private void assignListeners() {

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    setOptions();
                    dispose();
                } catch (JSystemTraderException jste) {
                    MessageDialog.showError(jste.getMessage());
                }

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isCancelled = true;
                dispose();
            }
        });

        selectFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(JSystemTrader.getAppPath());
                fileChooser.setDialogTitle("Select Historical Data File");

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    fileNameText.setText(file.getAbsolutePath());
                }
            }
        });
    }


    private void jbInit() throws JSystemTraderException, FileNotFoundException, IOException {

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Back Test");

        getContentPane().setLayout(new BorderLayout());

        cancelButton = new JButton("Cancel");
        okButton = new JButton("OK");

        JPanel optionsPanel = new JPanel(null);

        JLabel fileNameLabel = new JLabel("File name: ");
        fileNameText = new JTextField();

        selectFileButton = new JButton("...");

        JLabel maxTradesLabel = new JLabel("Max trades: ");
        maxTradesText = new JTextField("100");

        JLabel logLabel = new JLabel("Log: ");
        logCombo = new JComboBox(new String[] {"Enable", "Disable"});

        int labelX = 10;
        int componentX = 100;

        // A LayoutManager is not used. This needs to be refactored.
        fileNameLabel.setBounds(labelX, 10, COMPONENT_WIDTH, COMPONENT_HEIGHT);
        fileNameText.setBounds(componentX, 10, COMPONENT_WIDTH * 3, COMPONENT_HEIGHT);
        selectFileButton.setBounds(405, 10, COMPONENT_HEIGHT, COMPONENT_HEIGHT);

        maxTradesLabel.setBounds(labelX, 40, COMPONENT_WIDTH, COMPONENT_HEIGHT);
        maxTradesText.setBounds(componentX, 40, COMPONENT_WIDTH, COMPONENT_HEIGHT);
        logLabel.setBounds(labelX, 70, COMPONENT_WIDTH, COMPONENT_HEIGHT);
        logCombo.setBounds(componentX, 70, COMPONENT_WIDTH, COMPONENT_HEIGHT);

        optionsPanel.add(fileNameLabel);
        optionsPanel.add(fileNameText);
        optionsPanel.add(selectFileButton);

        optionsPanel.add(maxTradesLabel);
        optionsPanel.add(maxTradesText);
        optionsPanel.add(logLabel);
        optionsPanel.add(logCombo);

        JPanel controlPanel = new JPanel();
        controlPanel.add(okButton);
        controlPanel.add(cancelButton);

        getContentPane().add(optionsPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(okButton);
        getContentPane().setPreferredSize(MIN_SIZE);
        getContentPane().setMinimumSize(getContentPane().getPreferredSize());
    }

    public String getBackTestFileName() {
        return fileNameText.getText();
    }

}
