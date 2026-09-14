package com.example.account.service.dto;

import com.example.account.service.Enums.TipoConta;

import java.math.BigDecimal;

public record VerificarContaDto(String titular, BigDecimal saldo, TipoConta tipoConta) {
}
