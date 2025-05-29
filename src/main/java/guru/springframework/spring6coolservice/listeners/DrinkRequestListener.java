package guru.springframework.spring6coolservice.listeners;

import guru.springframework.spring6coolservice.config.KafkaConfig;
import guru.springframework.spring6coolservice.services.DrinkRequestProcessor;
import guru.springframework.spring6restmvcapi.events.DrinkPreparedEvent;
import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DrinkRequestListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final DrinkRequestProcessor drinkRequestProcessor;

    @KafkaListener(groupId = "ColdListener", topics = KafkaConfig.DRINK_REQUEST_COOL_TOPIC)
    public void listenDrinkRequest(DrinkRequestEvent drinkRequestEvent) {
        log.debug("Listening DrinkRequestEvent in DrinkRequestListener");

        drinkRequestProcessor.processDrinkRequest(drinkRequestEvent);

        kafkaTemplate.send(KafkaConfig.DRINK_PREPARED_TOPIC, DrinkPreparedEvent.builder()
                .beerOrderLine(drinkRequestEvent.getBeerOrderLineDTO())
                .build());
    }

}
