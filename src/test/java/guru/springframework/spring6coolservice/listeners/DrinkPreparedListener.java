package guru.springframework.spring6coolservice.listeners;

import guru.springframework.spring6coolservice.config.KafkaConfig;
import guru.springframework.spring6restmvcapi.events.DrinkPreparedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class DrinkPreparedListener {

    AtomicInteger coolMessageCounter = new AtomicInteger(0);

    @KafkaListener(groupId = "cold-listener", topics = KafkaConfig.DRINK_PREPARED_TOPIC)
    public void listen(DrinkPreparedEvent event) {
        log.debug("Listening Drink Prepared Event in Drink Prepared Listener for Testing only (?) - {}", event);
        coolMessageCounter.incrementAndGet();
    }

}
