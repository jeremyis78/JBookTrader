package com.jbooktrader.platform.backtest;

import com.jbooktrader.platform.marketbar.Snapshot;
import com.jbooktrader.platform.marketbook.MarketSnapshot;
import com.jbooktrader.platform.marketbook.SnapshotFilter;
import com.jbooktrader.platform.model.JBookTraderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Base class for all back-test data file readers.
 */
public abstract class BackTestFileReader {

    private static final String LINE_SEP = System.getProperty("line.separator");
    protected BufferedReader reader;
    protected SnapshotFilter filter;
    protected long fileSize;
    protected long previousTime, time;
    protected SimpleDateFormat sdf;
    protected final static Map<String, List<Snapshot>> cache = new HashMap<>();
    protected String cacheKey;

    /**
     * The names of the data columns in the order they appear in the file
     * @return the data column names
     */
    abstract List<String> getColumnHeaders();

    /**
     * Turns a line in the file into a snapshot
     * @param line the line of the data file
     * @return a Snapshot representing the line
     * @throws JBookTraderException if there's an unrecoverable error
     * @throws ParseException if there's an error parsing the date or time
     */
    abstract Snapshot toSnapshot(String line) throws JBookTraderException, ParseException;


    protected void setTimeZone(String line) throws JBookTraderException {
        String timeZone = line.substring(line.indexOf('=') + 1);
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        if (!tz.getID().equals(timeZone)) {
            String msg = "The specified time zone " + "\"" + timeZone + "\"" + " does not exist." + LINE_SEP;
            msg += "Examples of valid time zones: " + " America/New_York, Europe/London, Asia/Singapore.";
            throw new JBookTraderException(msg);
        }
        sdf = new SimpleDateFormat("MMddyyHHmmss");
        // Enforce strict interpretation of date and time formats
        sdf.setLenient(false);
        sdf.setTimeZone(tz);
    }

    public List<Snapshot> load(ProgressListener progressListener) throws JBookTraderException {

        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        String line = "";
        int lineSeparatorSize = System.getProperty("line.separator").length();
        long sizeRead = 0, lineNumber = 0;

        List<Snapshot> snapshots = new ArrayList<>();

        try {
            while ((line = reader.readLine()) != null) {
                if (lineNumber % 50000 == 0) {
                    progressListener.setProgress(sizeRead, fileSize, "Loading historical data file");
                    if (progressListener.isCancelled()) {
                        break;
                    }
                }
                lineNumber++;
                sizeRead += line.length() + lineSeparatorSize;
                boolean isComment = line.startsWith("#");
                boolean isProperty = line.contains("=");
                boolean isBlankLine = (line.trim().length() == 0);
                boolean isSnapshotLine = !(isComment || isProperty || isBlankLine);
                if (isSnapshotLine) {
                    Snapshot marketSnapshot = toSnapshot(line);
                    if (filter == null || filter.contains(time)) {
                        snapshots.add(marketSnapshot);
                    }
                    previousTime = time;
                } else if (isProperty) {
                    if (line.startsWith("timeZone")) {
                        setTimeZone(line);
                    }
                }
            }

            if (sdf == null) {
                String msg = "Property " + "\"timeZone\"" + " is not defined in the data file." + LINE_SEP;
                throw new JBookTraderException(msg);
            }

        } catch (IOException ioe) {
            throw new JBookTraderException("Could not read data file");
        } catch (Exception e) {
            String errorMsg = "Problem parsing line #" + lineNumber + ": " + line + LINE_SEP;
            String description = e.getMessage();
            if (description == null) {
                description = e.toString();
            }
            errorMsg += description;
            throw new RuntimeException(errorMsg);
        }

        if (!progressListener.isCancelled()) {
            cache.put(cacheKey, snapshots);
        }
        return snapshots;

    }

    protected List<String> fastSplit(String s) {
        ArrayList<String> tokens = new ArrayList<>();
        int index, lastIndex = 0;
        while ((index = s.indexOf(',', lastIndex)) != -1) {
            tokens.add(s.substring(lastIndex, index));
            lastIndex = index + 1;
        }
        tokens.add(s.substring(lastIndex));
        return tokens;
    }

    protected String join(Collection<String> collection, String delimiter){
        StringBuilder sb = new StringBuilder();
        for(String s: collection){
            sb.append(s).append(delimiter);
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}