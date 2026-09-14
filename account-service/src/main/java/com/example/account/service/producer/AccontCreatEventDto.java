package com.example.account.service.producer;

import com.example.account.service.Enums.TipoConta;

import java.time.LocalDate;
import java.util.UUID;

public record AccontCreatEventDto(UUID uuid, String titular, String cpf, LocalDate dataNascimento, TipoConta tipoConta

                               ) {
}
