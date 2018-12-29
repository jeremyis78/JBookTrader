package com.jsystemtrader.util;

import java.text.*;
import java.util.*;

/**
 * Certain futures contracts (such as ES) expire on the 3rd Friday of the contract
 * month, but the volume shifts to the next month contract on the business day
 * preceeding the 2nd Friday of the expiration month. For example, a 200606
 * contract had more volume than a 200609 contract on Wednesday June 7, 2006,
 * but on Thursday, June 8th (the day preceeding the 2nd Friday of the expiration),
 * the 200609 contract had more volume than the 200606 contract.
 *
 * This utility calculates the most liquid contract traded as of given date.
 */
public class MostLiquidContract {
    private static final Map<Integer, Integer> contractMonths = new HashMap<Integer, Integer> ();

    static {
        contractMonths.put(Calendar.DECEMBER, Calendar.MARCH);
        contractMonths.put(Calendar.JANUARY, Calendar.MARCH);
        contractMonths.put(Calendar.FEBRUARY, Calendar.MARCH);
        contractMonths.put(Calendar.MARCH, Calendar.JUNE);
        contractMonths.put(Calendar.APRIL, Calendar.JUNE);
        contractMonths.put(Calendar.MAY, Calendar.JUNE);
        contractMonths.put(Calendar.JUNE, Calendar.SEPTEMBER);
        contractMonths.put(Calendar.JULY, Calendar.SEPTEMBER);
        contractMonths.put(Calendar.AUGUST, Calendar.SEPTEMBER);
        contractMonths.put(Calendar.SEPTEMBER, Calendar.DECEMBER);
        contractMonths.put(Calendar.OCTOBER, Calendar.DECEMBER);
        contractMonths.put(Calendar.NOVEMBER, Calendar.DECEMBER);
    }

    public static void test() throws ParseException {

        Calendar rightNow = Calendar.getInstance();

        System.out.println("Tests: ");

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");

        rightNow.setTime(df.parse("06/07/2006"));
        System.out.println(rightNow.getTime() + ": " + getMostLiquid(rightNow) + " (expecting 200606)");

        rightNow.setTime(df.parse("06/08/2006"));
        System.out.println(rightNow.getTime() + ": " + getMostLiquid(rightNow) + " (expecting 200609)");

        rightNow.setTime(df.parse("06/14/2006"));
        System.out.println(rightNow.getTime() + ": " + getMostLiquid(rightNow) + " (expecting 200609)");

        rightNow.setTime(df.parse("12/07/2006"));
        System.out.println(rightNow.getTime() + ": " + getMostLiquid(rightNow) + " (expecting 200703)");

    }

    private static boolean keepCurrent(int monthNow, int weekOfMonthNow, int dayOfWeekNow) {
        boolean keepCurrent = false;

        if (monthNow == Calendar.MARCH || monthNow == Calendar.JUNE || monthNow == Calendar.SEPTEMBER ||
            monthNow == Calendar.DECEMBER) {
            if (weekOfMonthNow == 1 || (weekOfMonthNow == 2 && dayOfWeekNow < Calendar.THURSDAY)) {
                keepCurrent = true;
            }
        }

        return keepCurrent;
    }

    public static String getMostLiquid() {
        Calendar rightNow = Calendar.getInstance();
        return getMostLiquid(rightNow);
    }

    /**
     *   For the contracts whose volume shifts from the front contract to the back contract
     *   on the day preceding the 2nd Friday of expiration month of the front contract.
     */
    public static String getMostLiquid(Calendar asOfDate) {

        int monthNow = asOfDate.get(Calendar.MONTH);
        int yearNow = asOfDate.get(Calendar.YEAR);
        int mostLiquidYear = yearNow;

        int mostLiquidMonth = contractMonths.get(monthNow);

        // special case with december
        if (monthNow == Calendar.DECEMBER) {
            mostLiquidYear = yearNow + 1;
        }

        int dayOfWeekNow = asOfDate.get(Calendar.DAY_OF_WEEK);
        int weekOfMonthNow = asOfDate.get(Calendar.WEEK_OF_MONTH);
        if (keepCurrent(monthNow, weekOfMonthNow, dayOfWeekNow)) {
            mostLiquidMonth = monthNow;
            mostLiquidYear = yearNow;
        }

        Calendar mostLiquidDate = Calendar.getInstance();
        mostLiquidDate.set(Calendar.DAY_OF_MONTH, 1);
        mostLiquidDate.set(Calendar.MONTH, mostLiquidMonth);
        mostLiquidDate.set(Calendar.YEAR, mostLiquidYear);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        String formattedDate = df.format(mostLiquidDate.getTime());

        return formattedDate;

    }
}
