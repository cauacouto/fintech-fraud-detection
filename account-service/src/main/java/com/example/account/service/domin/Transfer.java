package com.example.account.service.domin;

import com.example.account.service.Enums.StatusTranfer;
import com.example.account.service.Enums.TipoPagamento;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Document(collation = "transacoes")
@Getter
@Setter
public class Transfer {

    @Id
    private UUID uuid;
    private UUID idOrigem;
    private UUID idDestino;
    private BigDecimal valor;
    private StatusTranfer statusTranfer;
    private TipoPagamento tipo;
    private Instant  realizadaEm;
}
