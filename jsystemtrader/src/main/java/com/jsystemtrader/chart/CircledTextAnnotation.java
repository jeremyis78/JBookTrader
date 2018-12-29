package com.jsystemtrader.chart;

import java.awt.*;
import java.awt.geom.*;

import org.jfree.chart.annotations.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.text.*;
import org.jfree.ui.*;

/**
 * Defines the shape of the markers which show order executions. In this
 * implementation, the shape is a solid circle with a letter "L", "S", or "F"
 * inside the circle, designating the "long", "short", and "flat" positions
 * resulting from order executions.
 */
public class CircledTextAnnotation extends XYTextAnnotation {
    private final int radius;
    private Color color;
    private final Stroke circleStroke = new BasicStroke(1);

    public CircledTextAnnotation(String text, double x, double y, int radius) {
        super(text, x, y);
        this.radius = radius;
    }

    public void setBkColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis,
                     int rendererIndex, PlotRenderingInfo info) {

        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);

        float anchorX = (float) domainAxis.valueToJava2D(getX(), dataArea, domainEdge);
        float anchorY = (float) rangeAxis.valueToJava2D(getY(), dataArea, rangeEdge);

        if (orientation == PlotOrientation.HORIZONTAL) {
            float tempAnchor = anchorX;
            anchorX = anchorY;
            anchorY = tempAnchor;
        }

        g2.setColor(color);
        double x = anchorX - radius;
        double y = anchorY - radius;
        double width = radius * 2.0;
        double height = radius * 2.0;

        g2.fill(new Ellipse2D.Double(x, y, width, height));

        g2.setColor(Color.WHITE);
        g2.setStroke(circleStroke);
        g2.drawOval( (int) x, (int) y, (int) width, (int) height);

        g2.setFont(getFont());
        g2.setPaint(getPaint());
        TextUtilities.drawRotatedString(getText(), g2, anchorX, anchorY, getTextAnchor(), getRotationAngle(),
                                        getRotationAnchor());
        Shape hotspot = TextUtilities.calculateRotatedStringBounds(getText(), g2, anchorX, anchorY, getTextAnchor(),
                getRotationAngle(), getRotationAnchor());

        String toolTip = getToolTipText();
        String url = getURL();
        if (toolTip != null || url != null) {
            addEntity(info, hotspot, rendererIndex, toolTip, url);
        }
    }
}
