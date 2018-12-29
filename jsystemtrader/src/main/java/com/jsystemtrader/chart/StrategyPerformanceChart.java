package com.jsystemtrader.chart;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import com.jsystemtrader.indicator.*;
import com.jsystemtrader.performance.*;
import com.jsystemtrader.platform.*;
import com.jsystemtrader.util.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;
import org.jfree.ui.*;

/**
 * Multi-indicator chart where indicators can be grouped together and displayed
 * on subplots, one group of indicators per plot.
 */
public class StrategyPerformanceChart {
    private static final int PRICE_PLOT_WEIGHT = 5;
    private static final int BOUGHT_SOLD_RADIUS = 6;
    private static final int CANDLE_WIDTH = 4;
    private static final Font BOUGHT_SOLD_FONT = new Font("SansSerif", Font.BOLD, 12);

    private JFreeChart chart;
    private CombinedDomainXYPlot combinedPlot;
    private ChartPanel chartPanel;

    private NumberAxis valueAxis;
    private DateAxis dateAxis;
    private final Strategy strategy;
    private final Map<Integer, TimeSeriesCollection> tsCollections;
    private FastXYPlot pricePlot;
    private CandlestickRenderer candleRenderer;
    private MultiColoredBarRenderer mcbRenderer;
    private JComboBox chartTypeCombo, timeLineCombo;


    public StrategyPerformanceChart(Strategy strategy) throws JSystemTraderException {
        this.strategy = strategy;
        tsCollections = new HashMap<Integer, TimeSeriesCollection> ();
        chart = createChart();
    }


    private void setRenderer() {
        int chartType = chartTypeCombo.getSelectedIndex();
        switch (chartType) {
            case 0:
                pricePlot.setRenderer(mcbRenderer);
                break;
            case 1:
                pricePlot.setRenderer(candleRenderer);
                break;
        }
    }

    private void setTimeline() {
        int timeLineType = timeLineCombo.getSelectedIndex();
        int barSizeInMinutes = strategy.getBarSizeInSecs() / 60;
        QuoteHistory qh = strategy.getQuoteHistory();

        MarketTimeLine mtl = new MarketTimeLine(barSizeInMinutes, qh.getFirstPriceBar().getDate(),
                                                qh.getLastPriceBar().getDate());
        SegmentedTimeline segmentedTimeline = null;

        switch (timeLineType) {
            case 0:
                segmentedTimeline = mtl.getAllHoursTimeline();
                break;
            case 1:
                segmentedTimeline = mtl.getNormalHoursTimeline();
                break;
        }

        dateAxis.setTimeline(segmentedTimeline);
        //dateAxis.setAutoRange(true);
        //chart.setNotify(true);
    }


    public JFrame getChartFrame(JFrame parent) {
        final JFrame chartFrame = new JFrame("Strategy Performance Chart - " + strategy);
        chartFrame.setIconImage(parent.getIconImage());
        chartPanel = new ChartPanel(chart, true);
        chartPanel.setPreferredSize(new Dimension(640, 480));

        ChartScrollBar xscrollbar = new ChartScrollBar(Adjustable.HORIZONTAL, combinedPlot, dateAxis);
        ChartScrollBar yscrollbar = new ChartScrollBar(Adjustable.VERTICAL, combinedPlot, valueAxis);

        Container contentPane = chartFrame.getContentPane();

        JPanel chartOptionsPanel = new JPanel(new SpringLayout());

        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder border = BorderFactory.createTitledBorder(etchedBorder);
        border.setTitle("Chart Options");
        chartOptionsPanel.setBorder(border);

        Dimension dimension = new Dimension(120, 23);

        JLabel chartTypeLabel = new JLabel("Chart Type:", JLabel.TRAILING);
        chartTypeCombo = new JComboBox(new String[] {"OHLC Bar", "Candlestick"});
        chartTypeCombo.setPreferredSize(dimension);
        chartTypeCombo.setMaximumSize(dimension);
        chartTypeLabel.setLabelFor(chartTypeCombo);

        JLabel timeLineLabel = new JLabel("Timeline:", JLabel.TRAILING);
        timeLineCombo = new JComboBox(new String[] {"All Hours"});
        timeLineCombo.setPreferredSize(dimension);
        timeLineCombo.setMaximumSize(dimension);
        timeLineLabel.setLabelFor(timeLineCombo);

        setRenderer();

        chartTypeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setRenderer();
            }
        });

        timeLineCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTimeline();
            }
        });

        chartOptionsPanel.add(chartTypeLabel);
        chartOptionsPanel.add(chartTypeCombo);
        chartOptionsPanel.add(timeLineLabel);
        chartOptionsPanel.add(timeLineCombo);

        SpringUtilities.makeCompactGrid(chartOptionsPanel, 1, 4, 10, 5, 15, 5); //rows, cols, initX, initY, xPad, yPad

        contentPane.add(chartOptionsPanel, BorderLayout.NORTH);

        JPanel px = new JPanel(new BorderLayout());
        px.add(xscrollbar, BorderLayout.SOUTH);

        JPanel py = new JPanel(new BorderLayout());
        py.add(yscrollbar, BorderLayout.EAST);

        contentPane.add(chartPanel, BorderLayout.CENTER);
        contentPane.add(px, BorderLayout.PAGE_END);
        contentPane.add(py, BorderLayout.LINE_END);

        chartFrame.setContentPane(contentPane);
        chartFrame.pack();

        RefineryUtilities.centerFrameOnScreen(chartFrame);

        chartFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

        return chartFrame;
    }


    private TimeSeries createIndicatorSeries(IndicatorHistory indHistory) {

        TimeSeries ts = new TimeSeries(indHistory.getName(), Minute.class);
        ts.setRangeDescription(indHistory.getName());

        synchronized (indHistory) {
            for (Indicator indicator : indHistory.getHistory()) {
                try {
                    ts.add(new Minute(indicator.getDate()), indicator.getValue(), false);
                } catch (Exception e) {
                    Account.getLogger().write(e);
                }
            }
        }
        ts.fireSeriesChanged();
        return ts;
    }


    private TimeSeries createProfitAndLossSeries(ProfitAndLossHistory profitAndLossHistory) {
        TimeSeries ts = new TimeSeries("P&L", Minute.class);
        synchronized (profitAndLossHistory) {
            for (ProfitAndLoss profitAndLoss : profitAndLossHistory.getHistory()) {
                Date date = new Date(profitAndLoss.getDate());
                Minute minute = new Minute(date);
                ts.setRangeDescription("P&L");
                try {
                    ts.add(minute, profitAndLoss.getValue());
                } catch (Exception e) {
                    Account.getLogger().write(e);
                }
            }
        }
        return ts;
    }


    private OHLCDataset createHighLowDataset() {
        QuoteHistory qh = strategy.getQuoteHistory();
        int qhSize = qh.size();
        Date[] dates = new Date[qhSize];

        double[] highs = new double[qhSize];
        double[] lows = new double[qhSize];
        double[] opens = new double[qhSize];
        double[] closes = new double[qhSize];
        double[] volumes = new double[qhSize];

        for (int bar = 0; bar < qhSize; bar++) {
            Calendar cal = Calendar.getInstance();
            PriceBar priceBar = qh.getPriceBar(bar);
            cal.setTimeInMillis(priceBar.getDate());
            dates[bar] = cal.getTime();

            highs[bar] = priceBar.getHigh();
            lows[bar] = priceBar.getLow();
            opens[bar] = priceBar.getOpen();
            closes[bar] = priceBar.getClose();
            volumes[bar] = priceBar.getVolume();
        }
        OHLCDataset dataset = new DefaultHighLowDataset(strategy.getTicker(), dates, highs, lows, opens, closes,
                volumes);
        return dataset;
    }


    private JFreeChart createChart() {
        int barSizeInSeconds = strategy.getBarSizeInSecs();

        // create OHLC bar renderer
        mcbRenderer = new MultiColoredBarRenderer(barSizeInSeconds);
        mcbRenderer.setSeriesPaint(0, Color.GREEN);

        // create candlestick renderer
        candleRenderer = new CandlestickRenderer(CANDLE_WIDTH, false, new HighLowItemLabelGenerator());
        candleRenderer.setDrawVolume(false);
        candleRenderer.setAutoWidthMethod(candleRenderer.WIDTHMETHOD_AVERAGE);
        candleRenderer.setAutoWidthFactor(8.0);
        candleRenderer.setAutoWidthGap(1.0);
        candleRenderer.setUpPaint(Color.GREEN);
        candleRenderer.setDownPaint(Color.RED);
        candleRenderer.setSeriesPaint(0, Color.WHITE);
        candleRenderer.setStroke(new BasicStroke(1.0f));
        candleRenderer.setSeriesPaint(1, Color.GRAY);

        dateAxis = new DateAxis();
        int barSizeInMinutes = strategy.getBarSizeInSecs() / 60;

        QuoteHistory qh = strategy.getQuoteHistory();
        MarketTimeLine mtl = new MarketTimeLine(barSizeInMinutes, qh.getFirstPriceBar().getDate(),
                                                qh.getLastPriceBar().getDate());
        SegmentedTimeline segmentedTimeline = mtl.getAllHoursTimeline();
        dateAxis.setTimeline(segmentedTimeline);

        // create price plot
        OHLCDataset highLowDataset = createHighLowDataset();
        valueAxis = new NumberAxis("Price");
        valueAxis.setAutoRangeIncludesZero(false);
        pricePlot = new FastXYPlot(highLowDataset, dateAxis, valueAxis, null);
        pricePlot.setBackgroundPaint(Color.BLACK);

        pricePlot.setDomainCrosshairVisible(true);
        pricePlot.setDomainCrosshairLockedOnData(false);
        pricePlot.setRangeCrosshairVisible(true);
        pricePlot.setRangeCrosshairLockedOnData(false);
        pricePlot.setRangeCrosshairPaint(Color.WHITE);
        pricePlot.setDomainCrosshairPaint(Color.WHITE);

        // parent plot
        combinedPlot = new CombinedDomainXYPlot(dateAxis);
        combinedPlot.setGap(10.0);
        combinedPlot.setOrientation(PlotOrientation.VERTICAL);
        combinedPlot.add(pricePlot, PRICE_PLOT_WEIGHT);

        // Put all indicators into groups, so that each group is
        // displayed on its own subplot
        for (IndicatorHistory indHist : strategy.getIndicators()) {
            TimeSeries ts = createIndicatorSeries(indHist);
            int subChart = indHist.getSubChartNumber();

            TimeSeriesCollection tsCollection = tsCollections.get(subChart);
            if (tsCollection == null) {
                tsCollection = new TimeSeriesCollection();
                tsCollections.put(subChart, tsCollection);
            }

            tsCollection.addSeries(ts);
        }

        // Plot executions
        for (OrderStatus execution : strategy.getExecutions()) {

            Date date = new Date(execution.getDate());
            double aveFill = execution.getAvgFillPrice();

            int decision = execution.getDecision();
            String annotationText = "?";
            Color bkColor = null;

            switch (decision) {
                case Strategy.DECISION_LONG:
                    annotationText = "L";
                    bkColor = Color.GREEN;
                    break;
                case Strategy.DECISION_SHORT:
                    annotationText = "S";
                    bkColor = Color.RED;
                    break;
                case Strategy.DECISION_FLAT:
                    annotationText = "F";
                    bkColor = Color.YELLOW;
                    break;

            }

            CircledTextAnnotation circledText = new CircledTextAnnotation(annotationText, date.getTime(), aveFill,
                    BOUGHT_SOLD_RADIUS);
            circledText.setFont(BOUGHT_SOLD_FONT);
            circledText.setBkColor(bkColor);
            circledText.setPaint(Color.BLACK);
            circledText.setTextAnchor(TextAnchor.CENTER);

            pricePlot.addAnnotation(circledText);
        }

        // Now that the indicators are grouped, create subplots
        for (Map.Entry mapEntry : tsCollections.entrySet()) {
            int subChart = (Integer) mapEntry.getKey();
            TimeSeriesCollection tsCollection = (TimeSeriesCollection) mapEntry.getValue();

            StandardXYItemRenderer renderer = new StandardXYItemRenderer();
            renderer.setStroke(new BasicStroke(2));

            if (subChart == 0) {
                pricePlot.setDataset(1, tsCollection);
                pricePlot.setRenderer(1, renderer);
            } else {
                NumberAxis valueAxis = new NumberAxis();
                valueAxis.setAutoRangeIncludesZero(false);
                FastXYPlot plot = new FastXYPlot(tsCollection, dateAxis, valueAxis, renderer);
                plot.setBackgroundPaint(Color.BLACK);
                plot.setDomainCrosshairVisible(true);
                plot.setDomainCrosshairLockedOnData(false);
                plot.setRangeCrosshairVisible(true);
                plot.setRangeCrosshairLockedOnData(false);
                plot.setRangeCrosshairPaint(Color.WHITE);
                plot.setDomainCrosshairPaint(Color.WHITE);

                int weight = 1;
                combinedPlot.add(plot, weight);
            }
        }

        // Plot P&L
        TimeSeries ts = createProfitAndLossSeries(strategy.getPositionManager().getProfitAndLossHistory());
        TimeSeriesCollection tsCollection = new TimeSeriesCollection();
        tsCollection.addSeries(ts);
        pricePlot.setDataset(2, tsCollection);

        StandardXYItemRenderer renderer = new StandardXYItemRenderer();
        renderer.setSeriesPaint(0, Color.WHITE);
        renderer.setStroke(new BasicStroke(2));
        pricePlot.setRenderer(2, renderer);
        pricePlot.mapDatasetToRangeAxis(2, 1);
        ValueAxis axis2 = new NumberAxis("Total Profit & Loss (in contract points)");
        pricePlot.setRangeAxis(1, axis2);

        chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, true);
        chart.getLegend().setPosition(RectangleEdge.TOP);
        chart.getLegend().setBackgroundPaint(Color.LIGHT_GRAY);

        return chart;

    }

}
