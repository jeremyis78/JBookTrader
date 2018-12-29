package com.jsystemtrader.util;

import java.text.*;
import java.util.*;

import com.jsystemtrader.client.*;
import com.jsystemtrader.platform.*;

/**
 * Utility class to ensure time synchronization between the machine where
 * JSystemTrader is running and the Interactive Brokers' server(s).
 *
 * It's recommended that a time sync service be running at all times.
 */
public class TimeSyncChecker {
    /** Maximum number of seconds difference between local machine's time and IB server's time */
    private static final int TOLERANCE = 2;

    private static final String lineSep = System.getProperty("line.separator");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    /**
     * Makes sure that the clock on the machine where JSystemTrader is running
     * is in sync with the Interactive Brokers server.
     *
     *
     * @param twsFormattedTime String Time as reported by TWS
     * @throws ParseException
     * @throws JSystemTraderException If the difference between the two clocks is greater than the tolerance
     */
    public static void timeCheck(String twsFormattedTime) throws ParseException, JSystemTraderException {

        Date twsTime = dateFormat.parse(twsFormattedTime);
        Date timeNow = new Date();

        // Difference in seconds between TWS time and local machine's time
        long difference = (timeNow.getTime() - twsTime.getTime()) / 1000;

        if (Math.abs(difference) > TOLERANCE) {
            String msg = "This machine's clock is out of sync with the TWS clock.";

            msg += lineSep + "TWS Time: " + twsTime;
            msg += lineSep + "Time now: " + timeNow;
            msg += lineSep + "Rounded Difference: " + difference + " seconds";
            msg += lineSep + lineSep;
            msg += "Close both TWS and " + JSystemTrader.APP_NAME +
                    ", set the machine's clock to the correct time, and try again.";

            throw new JSystemTraderException(msg);
        }
    }
}
