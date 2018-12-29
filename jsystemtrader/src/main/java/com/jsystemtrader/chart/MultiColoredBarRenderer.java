package com.jsystemtrader.chart;

import java.awt.*;
import java.awt.geom.*;

import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.*;
import org.jfree.ui.*;

/**
 * Custom renderer for the HighLowChart to provide the following features:
 * 1) Each bar can be either green, orange, or yellow
 * 2) The thickness of the bar varies according to the chart zoom level
 *
 * The functionality above is not available in JFreeChart, thus the need
 * for the custom renderer. It's a somewhat clumsy implementation (should instead
 * override getItemPaint() and getItemStroke() methods). Needs refactoring.
 */
class MultiColoredBarRenderer extends HighLowRenderer {
    private static final Color BULLISH_COLOR = Color.GREEN;
    private static final Color BEARISH_COLOR = new Color(255, 141, 51); //  TWS default
    private static final Color NEUTRAL_COLOR = Color.YELLOW;
    private final Stroke itemStroke = new BasicStroke(4);
    private final int barSizeInSeconds;
    int lastX;

    public MultiColoredBarRenderer(int barSizeInSeconds) {
        this.barSizeInSeconds = barSizeInSeconds;
    }

    /**
     * Draws the visual representation of a single data item.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the area within which the plot is being drawn.
     * @param info  collects information about the drawing.
     * @param plot  the plot (can be used to obtain standard color
     *              information etc).
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param crosshairState  crosshair information for the plot
     *                        (<code>null</code> permitted).
     * @param pass  the pass index.
     */



    public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info,
                         XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series,
                         int item, CrosshairState crosshairState, int pass) {

        double x = dataset.getXValue(series, item);

        if (domainAxis.getRange().contains(x)) {
            double xx = domainAxis.valueToJava2D(x, dataArea, plot.getDomainAxisEdge());
            OHLCDataset hld = (OHLCDataset) dataset;

            double prevClose = 0;
            if (item > 0) {
                prevClose = hld.getCloseValue(0, item - 1);
            }
            double close = hld.getCloseValue(0, item);
            Color color = (close >= prevClose) ? BULLISH_COLOR : BEARISH_COLOR;

            g2.setPaint(color);
            g2.setStroke(itemStroke);

            RectangleEdge location = plot.getRangeAxisEdge();
            double yHigh = hld.getHighValue(series, item);
            double yLow = hld.getLowValue(series, item);
            double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, location);
            double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, location);
            g2.draw(new Line2D.Double(xx, yyLow, xx, yyHigh));

            double delta = 2;
            double yOpen = hld.getOpenValue(series, item);
            double yyOpen = rangeAxis.valueToJava2D(yOpen, dataArea, location);
            g2.draw(new Line2D.Double(xx - delta, yyOpen, xx, yyOpen));

            double yClose = hld.getCloseValue(series, item);
            double yyClose = rangeAxis.valueToJava2D(yClose, dataArea, location);
            g2.draw(new Line2D.Double(xx, yyClose, xx + delta, yyClose));
        }
    }

}
