package com.jsystemtrader.chart;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.general.*;
import org.jfree.data.xy.*;

public class FastXYPlot extends XYPlot {
    private final Map<Integer, Long> lastRenderedX = new HashMap<Integer, Long> ();

    public FastXYPlot() {
        this(null, null, null, null);
    }

    public FastXYPlot(XYDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer) {
        super(dataset, domainAxis, rangeAxis, renderer);
    }


    public void renderFast(ValueAxis xAxis, ValueAxis yAxis, XYDataset dataset, int series, XYItemRenderer renderer,
                           XYItemRendererState state, int pass, Graphics2D g2, Rectangle2D dataArea,
                           PlotRenderingInfo info, CrosshairState crosshairState) {

        int firstItem = findFirstItemIndex(xAxis.getLowerBound(), dataset, series);
        int lastItem = findLastItemIndex(xAxis.getUpperBound(), dataset, series);

        for (int item = firstItem; item < lastItem; item++) {

            double x = dataset.getXValue(series, item);
            double xx = xAxis.valueToJava2D(x, dataArea, getDomainAxisEdge());

            long lastRendered = lastRenderedX.containsKey(series) ? lastRenderedX.get(series) : 0;
            long rounded = (long) ( (xx * 100) / 10);
            if (rounded != lastRendered) {
                lastRenderedX.put(series, rounded);
                renderer.drawItem(g2, state, dataArea, info, this, xAxis, yAxis, dataset, series, item, crosshairState,
                                  pass);
            }
        }

    }


    public boolean render(Graphics2D g2, Rectangle2D dataArea, int index, PlotRenderingInfo info,
                          CrosshairState crosshairState) {

        boolean foundData = false;
        XYDataset dataset = getDataset(index);
        if (!DatasetUtilities.isEmptyOrNull(dataset)) {
            lastRenderedX.clear();
            foundData = true;
            ValueAxis xAxis = getDomainAxisForDataset(index);
            ValueAxis yAxis = getRangeAxisForDataset(index);
            XYItemRenderer renderer = getRenderer(index);
            if (renderer == null) {
                renderer = getRenderer();
            }

            XYItemRendererState state = renderer.initialise(g2, dataArea, this, dataset, info);
            int passCount = renderer.getPassCount();

            SeriesRenderingOrder seriesOrder = getSeriesRenderingOrder();

            if (seriesOrder == SeriesRenderingOrder.REVERSE) {
                //render series in reverse order
                for (int pass = 0; pass < passCount; pass++) {
                    int seriesCount = dataset.getSeriesCount();
                    for (int series = seriesCount - 1; series >= 0; series--) {
                        renderFast(xAxis, yAxis, dataset, series, renderer, state, pass, g2, dataArea, info,
                                   crosshairState);
                    }
                }
            } else {
                //render series in forward order
                for (int pass = 0; pass < passCount; pass++) {
                    int seriesCount = dataset.getSeriesCount();
                    for (int series = 0; series < seriesCount; series++) {
                        renderFast(xAxis, yAxis, dataset, series, renderer, state, pass, g2, dataArea, info,
                                   crosshairState);
                    }
                }
            }
        }
        return foundData;
    }


    /**
     *
     * @return int idx of first item less than xval
     */
    private int findFirstItemIndex(double xval, XYDataset ds, int series) {
        if (Double.isNaN(xval)) {
            return 0;
        }
        int bin = binarySearch(ds, series, xval);
        if (bin < 0) {
            bin = -bin - 1;
        }
        return Math.max(0, Math.min(bin, ds.getItemCount(series) - 1));
    }


    /**
     *
     * @return int idx of first item greater than xval
     */
    private int findLastItemIndex(double xval, XYDataset ds, int series) {
        if (Double.isNaN(xval)) {
            return ds.getItemCount(series);
        }
        int bin = binarySearch(ds, series, xval);
        if (bin < 0) {
            bin = -bin - 1;
        }
        if (bin > 0) {
            bin++;
        }
        return Math.max(0, Math.min(bin, ds.getItemCount(series)));
    }

    private int binarySearch(XYDataset ds, int series, double xval) {
        int low = 0;
        int high = ds.getItemCount(series) - 1;

        while (low <= high) {
            int mid = (low + high) >> 1;
            double diff = ds.getXValue(series, mid) - xval;

            if (diff < 0) {
                low = mid + 1;
            } else if (diff > 0) {
                high = mid - 1;
            } else {
                return mid; // key found
            }
        }
        return - (low + 1); // key not found
    }
}
