package com.example.account.service.producer;

import com.example.account.service.domin.Conta;
import com.example.account.service.domin.Transfer;
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

    public void ContaProducer(Conta conta){

        AccontCreatEventDto event = new AccontCreatEventDto(
                conta.getId(),
                conta.getTitular(),
                conta.getCpf(),
                conta.getDataNascimento(),
                conta.getTipoConta()
        );
        kafkaTemplate.send("accont.creates",event.uuid().toString(),event);
        log.info("evento publicado | accountID={}", event.uuid());
    }


    public void TranfereciaProducer(Transfer transfer){

        TranferCreateEventDto eventDto = new TranferCreateEventDto(
                transfer.getUuid(),
                transfer.getIdOrigem(),
                transfer.getIdDestino(),
                transfer.getValor(),
                transfer.getFormaPagamento(),
                transfer.getRealizadaEm()
        );
        kafkaTemplate.send("tranfer.created",eventDto.idTranfercia(),eventDto);
        log.info("transacao publicada | transacaoId={}",eventDto.idTranfercia());

    }
}
