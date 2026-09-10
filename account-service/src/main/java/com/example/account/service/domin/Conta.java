package com.example.account.service.domin;

import com.example.account.service.Enums.TipoConta;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter

public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID uuid;
    private String titular;
    private String cpf;
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    public Conta(UUID uuid, String titular, String cpf, LocalDate dataNascimento, TipoConta tipoConta) {
        this.uuid = uuid;
        this.titular = titular;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.tipoConta = tipoConta;
    }

    public Conta() {

    }
}
