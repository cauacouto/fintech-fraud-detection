package com.example.account.service.domin;

import com.example.account.service.Enums.TipoConta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CONTA_DB")

public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String titular;
    private String cpf;
    private BigDecimal saldo;
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    public Conta() {

    }

    public Conta(UUID id, String titular, String cpf, LocalDate dataNascimento, TipoConta tipoConta) {
        this.id = id;
        this.titular = titular;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.tipoConta = tipoConta;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}
