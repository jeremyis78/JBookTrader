/*
 * ChartScrollBar.java
 *
 */
package com.jsystemtrader.chart;

/**
 *
 * @author joseph_vu
 * @author EK (refactored original implementation to simplify)
 */
import java.awt.event.*;

import javax.swing.*;

import org.jfree.chart.axis.*;
import org.jfree.chart.event.*;
import org.jfree.chart.plot.*;
import org.jfree.data.*;

public class ChartScrollBar extends JScrollBar implements AdjustmentListener, AxisChangeListener {
    private static final int STEPS = 1000000;

    private final ValueAxis valueAxis;
    private double ratio;
    private final Range range;
    private double displayMin, displayMax, viewLength;

    public ChartScrollBar(int orientation, XYPlot plot, ValueAxis valueAxis) {
        super(orientation);
        this.valueAxis = valueAxis;
        range = plot.getDataRange(valueAxis);
        valueAxis.addChangeListener(this);
        addAdjustmentListener(this);
        axisUpdate();
    }

    public void axisUpdate() {

        displayMin = range.getLowerBound();
        displayMax = range.getUpperBound();
        double viewMin = valueAxis.getLowerBound();
        double viewMax = valueAxis.getUpperBound();

        if (valueAxis instanceof DateAxis) {
            Timeline timeLine = ( (DateAxis) valueAxis).getTimeline();
            displayMin = timeLine.toTimelineValue( (long) displayMin);
            displayMax = timeLine.toTimelineValue( (long) displayMax);
            viewMin = timeLine.toTimelineValue( (long) viewMin);
            viewMax = timeLine.toTimelineValue( (long) viewMax);
        }

        viewLength = viewMax - viewMin;
        ratio = STEPS / (displayMax - displayMin);

        int newExtent = (int) (viewLength * ratio);
        int newValue;
        if (orientation == VERTICAL) {
            newValue = (int) ( (displayMax - viewMax) * ratio);
        } else {
            newValue = (int) ( (viewMin - displayMin) * ratio);
        }

        setValues(newValue, newExtent, 0, STEPS);
    }

    public void axisChanged(AxisChangeEvent event) {
        axisUpdate();
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        double start, end;
        if (orientation == VERTICAL) {
            end = displayMax - (getValue() / ratio);
            start = end - viewLength;
        } else {
            start = getValue() / ratio + displayMin;
            end = start + viewLength;
        }

        if (end > start) {
            if (valueAxis instanceof DateAxis) {
                Timeline timeLine = ( (DateAxis) valueAxis).getTimeline();
                start = timeLine.toMillisecond( (long) start);
                end = timeLine.toMillisecond( (long) end);
            }
            valueAxis.setRange(start, end);
        }
    }
}
