package com.example.account.service.dto;

import com.example.account.service.Enums.TipoConta;

import java.time.LocalDate;

public record ContaDto(String titular,
                       String cpf,
                       LocalDate dataNascimento,
                       TipoConta tipoConta) {
}
