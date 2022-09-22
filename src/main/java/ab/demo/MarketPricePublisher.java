package ab.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Collection;

public class MarketPricePublisher {

    private static final String WEBSERVER = "https://www.someurl.somedomain";
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketPricePublisher.class);
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.findAndRegisterModules();
    }

    public void publish(Collection<MarketPrice> prices) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEBSERVER))
                .POST(HttpRequest.BodyPublishers.ofString(MAPPER.writeValueAsString(prices)))
                .build();

        // commented until web server implementation
//        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
//        LOGGER.info("Response has been received: {}", response);
    }
}
