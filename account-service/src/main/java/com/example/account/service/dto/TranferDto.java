package com.example.account.service.dto;

import com.example.account.service.Enums.FormaPagamento;

import java.math.BigDecimal;

public record TranferDto(BigDecimal valor, FormaPagamento formaPagamento) {
}
