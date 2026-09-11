package com.example.account.service.producer;

import com.example.account.service.domin.Conta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventPublish {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public EventPublish(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(Conta conta){

        AccontCreatEvent event = new AccontCreatEvent(
                conta.getId(),
                conta.getTitular(),
                conta.getCpf(),
                conta.getDataNascimento(),
                conta.getTipoConta()
        );
        kafkaTemplate.send("accont.creates",event.uuid().toString(),event);
        log.info("evento publicado | accountID={}", event.uuid());
    }
}
