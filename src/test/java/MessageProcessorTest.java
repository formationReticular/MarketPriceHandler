import ab.demo.MessageProcessor;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageProcessorTest {

    private final MessageProcessor processor = new MessageProcessor();
    private final Logger logger = (Logger) LoggerFactory.getLogger(MessageProcessor.class);
    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @BeforeEach
    void initLogger() {
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void whenEmptyMessageReturnWithErrorMessage() {
        // given
        String message = "";
        // when
        processor.process(message);
        // then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Error occurred during mapping of message: {}", logsList.get(0)
                .getMessage());
        assertEquals(Level.ERROR, logsList.get(0)
                .getLevel());
    }

    @Test
    void whenMessageNotEmptyInstrumentPublished() {
        // given
        String message = "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002";
        // when
        processor.process(message);
        // then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Market prices has been published: {}", logsList.get(0)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());
        String published = logsList.get(0).getArgumentArray()[0].toString();
        assertTrue(published.contains("ask=1.2572560"));
    }

    @Test
    void whenFewPricesInMessageLatestPublished() {
        // given
        String message = "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002 " +
                         "\n 109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100";
        // when
        processor.process(message);
        // then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Market prices has been published: {}", logsList.get(0)
                .getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());
        String published = logsList.get(0).getArgumentArray()[0].toString();
        assertTrue(published.contains("bid=1.2486501"));
    }
}
