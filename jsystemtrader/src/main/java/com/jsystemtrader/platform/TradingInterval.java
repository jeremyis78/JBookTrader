package com.jsystemtrader.platform;

import java.text.*;
import java.util.*;

/**
 * TradingInterval defines the time period during which a strategy can open
 * positions, close position, and initiate new positions. A trading interval
 * defines the open of the interval, the close of the interval, and the grace
 * period of the interval. Trading can start after the "open" time. Open
 * positions will be closed after the "close" time. No new positions will be
 * initiated during the grace period, which is the specified as the number of
 * minutes before the "close" time. All times must be defined in military
 * time local to the timezone of the computer where JSystemTrader is running.
 * A strategy may have more han one trading interval, but the intervals may not
 * overlap.
 *
 * Example: suppose the strategy defines two trading intervals:
 *    addTradingInterval("9:35", "11:45", 10);
 *    addTradingInterval("13:15", "15:55", 15);
 * Then the following trading timeline is formed:
 * -- Trading Period 1: start trading at 9:35, do not take new positions
 *    after 11:35 (10 minutes before the close of the interval), and
 *    close open positions at 11:45
 * -- Trading Period 2: resume trading at 13:15, do not take new positions
 *   after 15:40 (15 minutes before the close of the interval), and
 *    close open positions at 15:55
 *
 */
public class TradingInterval {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private final Calendar open, close, grace;

    public TradingInterval(String openTime, String closeTime, int graceTime) throws JSystemTraderException {
        open = getTime(openTime);
        close = getTime(closeTime);
        grace = (Calendar) close.clone();
        // subtract grace time from the close time
        grace.add(Calendar.MINUTE, -graceTime);

        String msg = "Invalid trading interval " + this.toString();
        if (!close.after(open)) {
            msg += " Close time must be after open time.";
            throw new JSystemTraderException(msg);
        }
        if (!grace.after(open) || !grace.before(close)) {
            msg += " Grace time must be between open and close.";
            throw new JSystemTraderException(msg);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[open: " + dateFormat.format(open.getTime()));
        sb.append(" close: " + dateFormat.format(close.getTime()));
        sb.append(" grace: " + dateFormat.format(grace.getTime()) + "]");
        return sb.toString();
    }


    private Calendar getTime(String time) throws JSystemTraderException {
        int hours, minutes;
        StringTokenizer st = new StringTokenizer(time, ":");
        int tokens = st.countTokens();
        if (tokens != 2) {
            String msg = "Time " + time + " does not conform to the HH:MM format.";
            throw new JSystemTraderException(msg);
        }

        String hourToken = st.nextToken();
        try {
            hours = Integer.parseInt(hourToken);
        } catch (NumberFormatException nfe) {
            String msg = hourToken + " in " + time + " can not be parsed as hours.";
            throw new JSystemTraderException(msg);
        }

        String minuteToken = st.nextToken();
        try {
            minutes = Integer.parseInt(minuteToken);
        } catch (NumberFormatException nfe) {
            String msg = minuteToken + " in " + time + " can not be parsed as minutes.";
            throw new JSystemTraderException(msg);
        }

        if (hours < 0 || hours > 23) {
            String msg = "Specified hours: " + hours + ". Number of minutes must be in the [0..23] range.";
            throw new JSystemTraderException(msg);
        }

        if (minutes < 0 || minutes > 59) {
            String msg = "Specified minutes: " + minutes + ". Number of minutes must be in the [0..59] range.";
            throw new JSystemTraderException(msg);
        }

        Calendar period = Calendar.getInstance();
        period.set(Calendar.HOUR_OF_DAY, hours);
        period.set(Calendar.MINUTE, minutes);
        // set seconds explicitly, otherwise they will be carried from the current time
        period.set(Calendar.SECOND, 0);

        return period;
    }

    public Calendar getOpenTime() {
        return open;
    }

    public Calendar getGraceTime() {
        return grace;
    }

    public Calendar getCloseTime() {
        return close;
    }
}
