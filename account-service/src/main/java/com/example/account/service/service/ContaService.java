package com.example.account.service.service;

import com.example.account.service.Enums.StatusTranfer;
import com.example.account.service.Enums.FormaPagamento;
import com.example.account.service.Mapper.ContaMapper;
import com.example.account.service.domin.Conta;
import com.example.account.service.domin.Transfer;
import com.example.account.service.dto.ContaDto;
import com.example.account.service.dto.ContaDtoResponse;
import com.example.account.service.dto.TranferDto;
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
    private final ContaMapper mapper;

    public ContaService(ContaRepository repository, EventPublish eventPublish, TransferRepository transferRepository, ContaMapper mapper) {
        this.repository = repository;
        this.eventPublish = eventPublish;
        this.transferRepository = transferRepository;
        this.mapper = mapper;
    }

    public ContaDtoResponse Criaconta(ContaDto dto){
        Conta conta = mapper.toEntity(dto);
        conta.setSaldo(BigDecimal.ZERO);
        repository.save(conta);
        eventPublish.publishEvent(conta);
        return mapper.toDto(conta);


    }


    public void adicionarSaldo(UUID contaId,BigDecimal valor){
        Conta conta = repository.findById(contaId).orElseThrow();
        BigDecimal saldoAtua = conta.getSaldo();
        if (valor.compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("o valor deve ser maior que zero");
        }
        log.info("Saldo antes: {}", conta.getSaldo());

        BigDecimal novoSaldo = saldoAtua.add(valor);

        conta.setSaldo(novoSaldo);

        repository.save(conta);
        log.info("Saldo depois: {}", conta.getSaldo());
    }

    public void transferir(UUID idContaDestino, UUID idCotanOrigem, TranferDto tranferDto){

        Transfer transfer = new Transfer();
        log.info("Iniciando transferencia: origem={}, destino={}, valor={}"
                ,idCotanOrigem,idContaDestino,tranferDto.valor());

        transfer.setIdOrigem(idCotanOrigem);
        transfer.setIdDestino(idContaDestino);
        transfer.setTipo(tranferDto.formaPagamento());
        transfer.setStatusTranfer(StatusTranfer.PEDENDE);
        transfer.setRealizadaEm(Instant.now());
        transfer.setValor(tranferDto.valor());

        Conta conta = repository.findById(idCotanOrigem)
                .orElseThrow(()-> new RuntimeException("conta origem não encontrada"));

        BigDecimal saldoAtual = conta.getSaldo();

        log.info(
                "Validando transferência: contaOrigem={}, saldoAtual={}, valorTransferencia={}",
                idCotanOrigem,
                saldoAtual,
                tranferDto.valor()
        );
        if (tranferDto.valor().compareTo(saldoAtual) > 0){
            throw  new RuntimeException("valor incompativel ao saldo atual");
        }
        log.info("Saldo validado: idcontaOrigem={}, saldoAtual{}",idCotanOrigem,saldoAtual);

        BigDecimal novoSaldo = saldoAtual.subtract(tranferDto.valor());

        conta.setSaldo(novoSaldo);

        repository.save(conta);


        Conta contaDestino = repository.findById(idContaDestino)
                .orElseThrow(()-> new RuntimeException("conta destino não encotrada"));

        BigDecimal saldoAtualDestino = contaDestino.getSaldo();


        BigDecimal novoSaldoDestino= saldoAtualDestino.add(tranferDto.valor());

        contaDestino.setSaldo(novoSaldoDestino);

        repository.save(contaDestino);

        transferRepository.save(transfer);
        log.info(
                "Transferência realizada com sucesso: origem={}, destino={}, valor={}",
                idCotanOrigem,
                idContaDestino,
                tranferDto.valor()
        );

    }


}
