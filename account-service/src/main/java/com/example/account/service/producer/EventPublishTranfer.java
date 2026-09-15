package com.example.account.service.producer;

import com.account.service.TransferEvent;
import com.example.account.service.domin.Transfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

@Service
@Slf4j
public class EventPublishTranfer {

    private final KafkaTemplate<String, TransferEvent> kafkaTemplate;

    public EventPublishTranfer(@Qualifier("transferKafkaTemplate")
            KafkaTemplate<String, TransferEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void TranfereciaProducer(Transfer transfer){


        ByteBuffer valor = ByteBuffer.wrap(
                transfer.getValor()
                        .setScale(2)
                        .unscaledValue()
                        .toByteArray()
        );

        com.account.service.FormaPagamento formaPagamentoAvro =
                com.account.service.FormaPagamento.valueOf(
                        transfer.getFormaPagamento().name()
                );

        TransferEvent event = new TransferEvent(
                transfer.getUuid(),
                transfer.getIdOrigem(),
                transfer.getIdDestino(),
                formaPagamentoAvro,
                valor,
                transfer.getRealizadaEm()

        );
        kafkaTemplate.send("tranfer.created",event.getIdTransferencia().toString(),event);
        log.info("transacao publicada | transacaoId={}",event.getIdTransferencia());

    }
}
