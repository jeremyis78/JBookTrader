package com.jbooktrader.platform.indicator;

import com.jbooktrader.platform.marketbar.Snapshot;
import com.jbooktrader.platform.marketbook.*;
import com.jbooktrader.platform.strategy.Strategy;

import java.util.*;

/**
 * @author Eugene Kononov
 */
public class IndicatorManager {
    private class StrategyContext {
        public StrategyContext(final long previousSnapshotTime, final long samples) {
            this.previousSnapshotTime = previousSnapshotTime;
            this.samples = samples;
        }

        long previousSnapshotTime;
        long samples;
    }

    private final List<Indicator> indicators;
    private MarketBook marketBook;

    //A map for each strategy to its previous snapshot time and current recorded samples count
    private Map<String, StrategyContext> strategyContextMap;

    public Indicator addIndicator(Indicator newIndicator) {
        String key = newIndicator.getKey();
        for (Indicator indicator : indicators) {
            if (key.equals(indicator.getKey())) {
                return indicator;
            }
        }

        indicators.add(newIndicator);
        newIndicator.setMarketBook(marketBook);

        return newIndicator;
    }


    public IndicatorManager() {
        indicators = new ArrayList<>();
        strategyContextMap = new HashMap<>();
    }

    public void setMarketBook(MarketBook marketBook) {
        this.marketBook = marketBook;
        for (Indicator indicator : indicators) {
            indicator.setMarketBook(marketBook);
        }
    }

    public boolean hasValidIndicators(Strategy strategy) {
        StrategyContext context = strategyContextMap.get(strategy.getName());
        return (context.samples >= strategy.getMinimumSamplesSize());
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void updateIndicators(Strategy strategy) {
        Snapshot snapshot = strategy.getMarket().getSnapshot(); //marketBook.getSnapshot();
        if (snapshot == null) {
            return;
        }
        long lastSnapshotTime = snapshot.getTime();

        StrategyContext context = strategyContextMap.get(strategy.getName());
        if (Objects.isNull(context)){
            context = new StrategyContext(0, 0);
            strategyContextMap.put(strategy.getName(), context);
        }

        context.samples++;
        if (lastSnapshotTime - context.previousSnapshotTime > strategy.getGapSize()) {
            context.samples = 0;
            for (Indicator indicator : indicators) {
                indicator.reset();
            }
        }
        context.previousSnapshotTime = lastSnapshotTime;

        for (Indicator indicator : indicators) {
            indicator.calculate();
        }
    }
}
