package ab.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagingSystemFeedSubscriber implements FeedSubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingSystemFeedSubscriber.class);
    private final MessageProcessor processor = new MessageProcessor();

    @Override
    public void onMessage(String message) {
        if (!messageIsValid(message)) {
            throw new MessageIsNotValidException();
        }
        processor.process(message);
    }

    private boolean messageIsValid(String message) {
        if (message == null || message.isBlank()) {
            LOGGER.error("Message is blank");
            return false;
        }
        // There is a room for more complex check
        return true;
    }
}
