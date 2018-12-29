package com.jsystemtrader.chart;

import org.jfree.chart.axis.*;

public class MarketTimeLine {
    private static final long MILLIS_IN_MINUTE = 60 * 1000;
    private static final long MILLIS_IN_DAY = MILLIS_IN_MINUTE * 60 * 24;
    private static final long MARKET_OPEN_TIME = (9 * 60 + 30) * MILLIS_IN_MINUTE;
    private static final int TRADING_MINUTES = 6 * 60 + 30;
    private static final int NON_TRADING_MINUTES = 17 * 60 + 30;
    private static final int WEEKEND_MINUTES = 65 * 60 + 20; // from 16:05 Fri to 9:25 Mon

    private final int barSizeInMinutes;
    private final long startTime, endTime;

    public MarketTimeLine(int barSizeInMinutes, long startTime, long endTime) {
        this.barSizeInMinutes = barSizeInMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SegmentedTimeline getNormalHoursTimeline() {
        long segmentSize = barSizeInMinutes * MILLIS_IN_MINUTE;
        int segmentsIncluded = TRADING_MINUTES / barSizeInMinutes;
        int segmentsExcluded = NON_TRADING_MINUTES / barSizeInMinutes;
        SegmentedTimeline timeline = new SegmentedTimeline(segmentSize, segmentsIncluded, segmentsExcluded);
        timeline.setAdjustForDaylightSaving(true);

        timeline.setStartTime(SegmentedTimeline.FIRST_MONDAY_AFTER_1900 + MARKET_OPEN_TIME);
        timeline.setBaseTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
        /*
                Calendar lastBarDate = Calendar.getInstance();
                lastBarDate.setTimeInMillis(endTime);

                Calendar now = Calendar.getInstance();
                now.setTimeInMillis(startTime);
                now.set(Calendar.HOUR_OF_DAY, 16);
                now.set(Calendar.MINUTE, 5);
                now.set(Calendar.SECOND, 0);
                now.set(Calendar.MILLISECOND, 0);

                // find the first Friday
                while (now.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                    now.add(Calendar.DAY_OF_YEAR, 1);
                }

                // exclude weekends
                while (now.before(lastBarDate)) {
                    long excludeFrom = now.getTimeInMillis();
                    long excludeTo = excludeFrom + WEEKEND_MINUTES * MILLIS_IN_MINUTE;
                    timeline.addBaseTimelineExclusions(excludeFrom, excludeTo);

                    now.add(Calendar.DAY_OF_YEAR, 7); // move to next Friday
                }
         */
        return timeline;
    }

    public SegmentedTimeline getAllHoursTimeline() {
        SegmentedTimeline timeline = new SegmentedTimeline(SegmentedTimeline.DAY_SEGMENT_SIZE, 7, 0);
        return timeline;
    }
}
