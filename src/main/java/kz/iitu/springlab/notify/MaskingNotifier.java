package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Individual assignment, variant 6: replaces every digit of the message with an asterisk.
 */
@Component("masking")
@Order(3)
public class MaskingNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(MaskingNotifier.class);

    @PostConstruct
    void init() {
        log.info("MASKING >> MaskingNotifier initialised (variant 6)");
    }

    @Override
    public String send(String message) {
        String masked = message.replaceAll("\\d", "*");
        log.info("MASKING >> {}", masked);
        return masked;
    }

    @Override
    public String channel() { return "masking"; }
}
