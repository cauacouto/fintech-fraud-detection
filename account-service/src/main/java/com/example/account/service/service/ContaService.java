package com.example.account.service.service;

import com.example.account.service.domin.Conta;
import com.example.account.service.dto.ContaDto;
import com.example.account.service.producer.AccontCreatEvent;
import com.example.account.service.producer.EventPublish;
import com.example.account.service.repository.ContaRepository;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    private final ContaRepository repository;
    private final EventPublish eventPublish;

    public ContaService(ContaRepository repository, EventPublish eventPublish) {
        this.repository = repository;
        this.eventPublish = eventPublish;
    }

    public void Criaconta(ContaDto dto){
        Conta conta = new Conta();
        conta.setTitular(dto.titular());
        conta.setCpf(dto.cpf());
        conta.setDataNascimento(dto.dataNascimento());
        conta.setTipoConta(dto.tipoConta());
        repository.save(conta);
        eventPublish.publishEvent(conta);
    }

   // public void tranferir


}
