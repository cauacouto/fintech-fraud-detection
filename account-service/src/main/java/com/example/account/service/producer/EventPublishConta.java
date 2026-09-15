package com.example.account.service.producer;

import com.account.service.AccountCreatedEvent;
import com.example.account.service.domin.Conta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventPublishConta {

    private final KafkaTemplate<String, AccountCreatedEvent> kafkaTemplate;

    public EventPublishConta(@Qualifier("accountKafkaTemplate")
                             KafkaTemplate<String, AccountCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    public void ContaProducer(Conta conta){

        com.account.service.TipoConta tipoContaAvro =
                com.account.service.TipoConta.valueOf(
                        conta.getTipoConta().name()
                );

            AccountCreatedEvent event = new AccountCreatedEvent(
                conta.getId().toString(),
                conta.getTitular(),
                conta.getCpf(),
                conta.getDataNascimento(),
              tipoContaAvro
        );
        kafkaTemplate.send("account.created",event.getId().toString(),event);
        log.info("evento publicado | accountID={}", event.getId());
    }

}
