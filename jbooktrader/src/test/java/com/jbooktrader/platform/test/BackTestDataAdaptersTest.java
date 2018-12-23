package com.jbooktrader.platform.test;

import com.jbooktrader.platform.backtest.MarketSnapshotBackTestDataAdapter;
import com.jbooktrader.platform.backtest.PriceBarBackTestDataAdapter;
import com.jbooktrader.platform.marketbar.PriceBar;
import com.jbooktrader.platform.marketbook.MarketSnapshot;
import com.jbooktrader.platform.util.format.NumberFormatterFactory;
import org.junit.Test;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * unit tests for [PriceBar|MarketSnapshot]BackTestDataAdapter
 */
public class BackTestDataAdaptersTest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMddyy,HHmmss");
    private static final DecimalFormat DECIMAL_FORMAT = NumberFormatterFactory.getNumberFormatter(2);

    private MarketSnapshot marketSnapshot;
    private PriceBar priceBar;


    @Test
    public void testTimeZoneIsInHeader(){
        MarketSnapshotBackTestDataAdapter msdfa = new MarketSnapshotBackTestDataAdapter();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME
                .withZone(ZoneId.of("America/New_York"));
        assertTrue(msdfa.getHeader(formatter).contains("\ntimeZone=America/New_York\n"));

        PriceBarBackTestDataAdapter pbdfa = new PriceBarBackTestDataAdapter();
        DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME
                .withZone(ZoneId.of("Europe/London"));
        assertTrue(pbdfa.getHeader(formatter2).contains("\ntimeZone=Europe/London\n"));
    }

    @Test
    public void testToDataRow(){
        MarketSnapshotBackTestDataAdapter msdfa = new MarketSnapshotBackTestDataAdapter();
        DateTimeFormatter formatter = DATE_TIME_FORMATTER
                .withZone(ZoneId.of("America/New_York"));
        marketSnapshot = new MarketSnapshot(1545552655555L, -10, 1500, 600);
        assertEquals("122318,031055,-10.0,1500,600",
                msdfa.toDataRow(marketSnapshot, formatter, DECIMAL_FORMAT));

        PriceBarBackTestDataAdapter pbdfa = new PriceBarBackTestDataAdapter();
        DateTimeFormatter formatter2 = DATE_TIME_FORMATTER
                .withZone(ZoneId.of("Europe/London"));
        priceBar = new PriceBar(1545552655555L, 10.12, 12.56, 9.00, 10.50, 123456);
        assertEquals("122318,081055,10.12,12.56,9,10.5,123456",
                pbdfa.toDataRow(priceBar, formatter2, DECIMAL_FORMAT));
    }
}
