package ab.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FeedMapper {

    private static final String TIMESTAMP_PATTERN = "dd-MM-yyyy HH:mm:ss:SSS";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
    private static final String DELIMITER = ",";

    public List<MarketPrice> mapMessage(String message) {
        return message.lines().map(
                line -> {
                    String[] values = line.split(DELIMITER);
                    MarketPrice marketPrice = new MarketPrice();
                    marketPrice.setId(Integer.valueOf(values[0].trim()));
                    marketPrice.setInstrument(Instrument.get(values[1].trim()));
                    marketPrice.setBid(new BigDecimal(values[2].trim()));
                    marketPrice.setAsk(new BigDecimal(values[3].trim()));
                    marketPrice.setTimestamp(LocalDateTime.from(FORMATTER.parse(values[4].trim())));
                    return marketPrice;
                }).collect(Collectors.toList());
    }
}
