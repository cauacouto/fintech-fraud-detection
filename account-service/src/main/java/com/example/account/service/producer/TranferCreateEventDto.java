package com.example.account.service.producer;

import com.example.account.service.Enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TranferCreateEventDto(String idTranfercia,
                                    String idOrigem,
                                    String dDestino,
                                    BigDecimal valor,
                                    FormaPagamento formaPagamento,
                                    Instant RealizadaEm) {
}
