package com.jsystemtrader.platform;

import java.io.*;
import java.text.*;
import java.util.*;

public class HTMLLog {

    /** access to static instance of SimpleDateFormat must be synchronized */
    private static final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss MM/dd/yy z");
    private static final int DATE_CULUMN_WIDTH = 18;

    private final int detailLevel;
    private final PrintWriter writer;
    private static boolean isDisabled;

    public HTMLLog(int detailLevel, String fileName) throws IOException {
        this.detailLevel = detailLevel;
        writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
        synchronized (df) {
            writer.println("<b>New Log Started: " + df.format(Calendar.getInstance().getTime()) + "</b>");
        }
        writer.println("<TABLE BORDER=1 WIDTH=100%>");
        writer.flush();
    }


    public static void disable() {
        isDisabled = true;
    }

    synchronized public void write(List columns, Calendar strategyCalendar, String messageType, int detailLevel) {
        if (this.detailLevel >= detailLevel && !isDisabled) {
            writer.println("<TR>");
            writer.println("<TD WIDTH=" + DATE_CULUMN_WIDTH + "%>");
            synchronized (df) {
                writer.println(df.format(strategyCalendar.getTime()));
            }
            writer.println("</TD>");

            for (Object column : columns) {
                writer.println("<TD>");
                writer.println(column);
                writer.println("</TD>");
            }

            writer.println("<TD WIDTH=5%>");
            writer.println(messageType);
            writer.println("</TD>");

            writer.println("</TR>");
            writer.flush();
        }
    }

    synchronized public void writeHeaders(String[] headers) {
        writer.println("<TR>");
        writer.println("<TD WIDTH=" + DATE_CULUMN_WIDTH + "%>");
        writer.println("<b>Date</b>");
        writer.println("</TD>");

        for (int i = 0; i < headers.length; i++) {
            writer.println("<TD>");
            writer.println("<b>" + headers[i] + "</b>");
            writer.println("</TD>");
        }

        writer.println("<TD WIDTH=5%>");
        writer.println("<b>Message Type</b>");
        writer.println("</TD>");
        writer.println("</TR>");
        writer.flush();
    }


    synchronized public void write(String s, String messageType, int detailLevel) {
        if (this.detailLevel >= detailLevel && !isDisabled) {
            if (! (messageType.equalsIgnoreCase("exception"))) {
                synchronized (df) {
                    String activity = df.format(Calendar.getInstance().getTime()) + ": " + s + " (" + messageType + ")";
                    Account.setActivity(activity);
                }
            }

            writer.println("<TR>");
            writer.println("<TD WIDTH=" + DATE_CULUMN_WIDTH + "%>");
            synchronized (df) {
                writer.println(df.format(Calendar.getInstance().getTime()));
            }
            writer.println("</TD>");
            writer.println("<TD>");
            if (messageType.equalsIgnoreCase("error")) {
                s = "<b>" + s + "</b>";
            }
            writer.println(s);
            writer.println("</TD>");

            writer.println("<TD WIDTH=5%>");
            writer.println(messageType);

            writer.println("</TD>");
            writer.println("</TR>");
            writer.flush();
        }
    }

    synchronized public void write(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.close();
        String stackTrace = sw.toString();
        write("<b>Exception: </b>" + stackTrace, "Exception", 1);
        synchronized (df) {
            Account.setActivity(df.format(Calendar.getInstance().getTime()) + ": " + t.toString() + " (Exception)");
        }
    }
}
