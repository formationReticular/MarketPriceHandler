package ab.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

public class MessageProcessor {

    private static final BigDecimal BID_FACTOR = new BigDecimal("0.999");
    private static final BigDecimal ASK_FACTOR = new BigDecimal("1.001");

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);
    private final FeedMapper mapper = new FeedMapper();
    private final MarketPricePublisher publisher = new MarketPricePublisher();

    public void process(String message) {
        List<MarketPrice> feed = mapper.mapMessage(message);
        if (feed.isEmpty()) {
            LOGGER.error("Error occurred during mapping of message: {}", message);
            return;
        }

        Collection<MarketPrice> latestPrices = extractLatest(feed);
        addMargin(latestPrices);

        try {
            publisher.publish(latestPrices);
            LOGGER.info("Market prices has been published: {}", latestPrices);
        } catch (Exception exception) {
            LOGGER.error("Market prices couldn't be published: {}", latestPrices, exception);
        }
    }

    private Collection<MarketPrice> extractLatest(List<MarketPrice> feed) {
        EnumMap<Instrument, MarketPrice> latest = new EnumMap<>(Instrument.class);
        for (MarketPrice marketPrice : feed) {
            Instrument instrument = marketPrice.getInstrument();
            if (!latest.containsKey(instrument)) {
                latest.put(instrument, marketPrice);
            } else {
                MarketPrice storedPrice = latest.get(instrument);
                if (marketPrice.getTimestamp().isAfter(storedPrice.getTimestamp())) {
                    latest.put(instrument, marketPrice);
                }
            }
        }
        return latest.values();
    }

    private void addMargin(Collection<MarketPrice> prices) {
        for (MarketPrice marketPrice : prices) {
            marketPrice.setAsk(marketPrice.getAsk().multiply(ASK_FACTOR));
            marketPrice.setBid(marketPrice.getBid().multiply(BID_FACTOR));
        }
    }
}
