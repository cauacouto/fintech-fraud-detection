# 🏦 Fintech Fraud Detection

Sistema financeiro baseado em microsserviços orientados a eventos, utilizando **Apache Kafka** para comunicação assíncrona entre os serviços. Projeto criado com fins de estudo e portfólio, simulando um cenário real de detecção de fraude em transações financeiras.

---

## 📖 Sobre o projeto

O sistema simula um banco digital simples, onde:

1. Um cliente cria uma conta
2. Realiza transferências entre contas
3. Cada transferência é analisada em tempo real por um serviço de detecção de fraude
4. O resultado da análise é propagado para os demais serviços via Kafka

O foco principal é a comunicação **assíncrona e desacoplada** entre serviços através de eventos, ao invés de chamadas síncronas (REST) diretas entre eles.

---

## 🏗️ Arquitetura

```
                         ┌─────────────────────┐
                         │   Account Service    │
                         │  (Java + Spring Boot) │
                         └──────────┬───────────┘
                                    │
                    ┌───────────────┼────────────────┐
                    │               │                │
              account.created  transaction.initiated │
                    │               │                │
                    ▼               ▼                ▼
            ┌───────────────────────────────────────────┐
            │                Apache Kafka                 │
            └───────────────────────────────────────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │    Fraud Service      │
                         │     (planejado)        │
                         └─────────────────────┘
```

### Bancos de dados

| Dado | Banco | Motivo |
|---|---|---|
| Contas / Saldo | PostgreSQL | Dados relacionais, consistência forte |
| Transações | MongoDB | Alto volume, histórico, sem necessidade de joins |

---

## 🛠️ Stack utilizada

- **Java 21 + Spring Boot 3**
- **Apache Kafka 3.9** (modo KRaft, sem Zookeeper)
- **Spring Kafka** — producer/consumer
- **PostgreSQL** — dados de contas
- **MongoDB** — histórico de transações
- **Docker Compose** — orquestração do ambiente local
- **Kafka UI** — visualização de tópicos e mensagens

---

## 📦 Serviços

### ✅ Account Service (em desenvolvimento)

Responsável por:
- Criação de contas
- Realização de transferências
- Publicação de eventos no Kafka

**Tópicos publicados:**

| Tópico | Quando é publicado |
|---|---|
| `account.created` | Após a criação de uma nova conta |
| `transaction.initiated` | Após uma transferência ser registrada |

### 🔜 Fraud Service (planejado)

Vai consumir os eventos de transação e aplicar regras de detecção de fraude, como:
- Múltiplas transações em curto intervalo de tempo
- Valores muito altos fora do padrão de uso
- Transferências para contas nunca utilizadas antes

---

## 🚀 Como rodar o projeto

### Pré-requisitos
- Docker e Docker Compose
- Java 21+
- Maven

### 1. Subir a infraestrutura (Kafka + Kafka UI)

```bash
docker compose up -d
```

Acesse o Kafka UI em [http://localhost:8080](http://localhost:8080) para visualizar os tópicos e mensagens.

### 2. Configurar o `application.properties`

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/account-service
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/account-service

# Kafka
spring.kafka.bootstrap-servers=localhost:19092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

---

## 🗺️ Roadmap

- [x] Ambiente Kafka com Docker Compose (modo KRaft)
- [x] Account Service — criação de contas
- [x] Account Service — transferências
- [ ] Fraud Service — regras de detecção
- [ ] Kafka Streams — análise em janelas de tempo (ex: transações no último minuto)
- [ ] Dead Letter Queue (DLQ) para mensagens com falha de processamento
- [ ] Transactional Outbox Pattern — garantir consistência entre banco e Kafka
- [ ] Schema Registry + Avro — contratos versionados entre serviços
- [ ] Notification Service — notificações de transações aprovadas/rejeitadas

---

## 📚 Aprendizados

Este projeto foi construído como parte do meu estudo sobre **Apache Kafka** e arquitetura orientada a eventos, aplicando conceitos como:

- Producer/Consumer com Spring Kafka
- Serialização de mensagens em JSON
- Uso de chave de partição para garantir ordenação de eventos por conta
- Separação de responsabilidades entre lógica de negócio e mensageria
- Escolha de banco de dados adequado ao contexto (poliglota persistence)

---

## 📄 Licença

Este projeto é livre para fins de estudo e aprendizado.
