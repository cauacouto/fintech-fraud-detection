package com.example.account.service.Controller;

import com.example.account.service.Enums.FormaPagamento;
import com.example.account.service.dto.ContaDto;
import com.example.account.service.dto.ContaDtoResponse;
import com.example.account.service.dto.TranferDto;
import com.example.account.service.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class AccontController {

    private final ContaService service;


    @PostMapping
    public ResponseEntity<ContaDtoResponse> criasConta(@RequestBody ContaDto dto){
        ContaDtoResponse response = service.Criaconta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @PostMapping("/{id}/saldo")
    public ResponseEntity<Void> adicionarSaldo(@PathVariable UUID id,@RequestBody BigDecimal valor){
        this.service.adicionarSaldo(id, valor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idDestino}/{idOrigem}")
    public ResponseEntity<Void> transferir(@PathVariable UUID idDestino,
                                           @PathVariable UUID idOrigem,
                                           @RequestBody TranferDto tranferDto){
        this.service.transferir(idDestino,idOrigem,tranferDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
