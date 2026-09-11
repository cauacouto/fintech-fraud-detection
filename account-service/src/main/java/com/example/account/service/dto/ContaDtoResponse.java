package com.example.account.service.dto;

import com.example.account.service.Enums.TipoConta;

import java.time.LocalDate;
import java.util.UUID;

public record ContaDtoResponse(UUID id,
                               String titular,
                               LocalDate dataNascimento,
                               TipoConta tipoConta) {
}
