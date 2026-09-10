package com.example.account.service.service;

import com.example.account.service.Enums.StatusTranfer;
import com.example.account.service.Enums.TipoPagamento;
import com.example.account.service.domin.Conta;
import com.example.account.service.domin.Transfer;
import com.example.account.service.dto.ContaDto;
import com.example.account.service.producer.EventPublish;
import com.example.account.service.repository.ContaRepository;
import com.example.account.service.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class ContaService {

    private final ContaRepository repository;
    private final EventPublish eventPublish;
    private final TransferRepository transferRepository;

    public ContaService(ContaRepository repository, EventPublish eventPublish, TransferRepository transferRepository) {
        this.repository = repository;
        this.eventPublish = eventPublish;
        this.transferRepository = transferRepository;
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

    public void transferir(UUID idContaDestino, UUID idCotanOrigem, BigDecimal valor, TipoPagamento tiPagamento){

        Transfer transfer = new Transfer();
        log.info("Iniciando transferencia: origem={}, destino={}, valor={}"
                ,idCotanOrigem,idContaDestino,valor);

        transfer.setIdOrigem(idCotanOrigem);
        transfer.setIdDestino(idContaDestino);
        transfer.setTipo(tiPagamento);
        transfer.setStatusTranfer(StatusTranfer.PEDENDE);
        transfer.setRealizadaEm(Instant.now());
        transfer.setValor(valor);

        Conta conta = repository.findById(idCotanOrigem)
                .orElseThrow(()-> new RuntimeException("conta origem não encontrada"));

        BigDecimal saldoAtual = conta.getSaldo();

        if (valor.compareTo(saldoAtual) > 0){
            throw  new RuntimeException("valor incompativel ao saldo atual");
        }
        log.info("Saldo validado: idcontaOrigem={}, saldoAtual{}",idCotanOrigem,saldoAtual);

        BigDecimal novoSaldo = saldoAtual.subtract(valor);

        conta.setSaldo(novoSaldo);

        repository.save(conta);


        Conta contaDestino = repository.findById(idContaDestino)
                .orElseThrow(()-> new RuntimeException("conta destino não encotrada"));

        BigDecimal saldoAtualDestino = contaDestino.getSaldo();


        BigDecimal novoSaldoDestino= saldoAtualDestino.add(valor);

        contaDestino.setSaldo(novoSaldoDestino);

        repository.save(contaDestino);

        transferRepository.save(transfer);
        log.info(
                "Transferência realizada com sucesso: origem={}, destino={}, valor={}",
                idCotanOrigem,
                idContaDestino,
                valor
        );

    }


}
