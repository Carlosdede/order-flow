# OrderFlow

Plataforma corporativa de processamento de pedidos desenvolvida como projeto pessoal para aprofundamento em arquitetura de software e desenvolvimento Full Stack com Java e React.

O objetivo do projeto é simular problemas reais encontrados em sistemas corporativos de pedidos, cobrindo o fluxo completo desde a criação do pedido até estoque, pagamento, separação, expedição e entrega.

## Objetivos

O OrderFlow foi criado para servir como ambiente de estudo e aplicação prática de conceitos como:

- Arquitetura em camadas
- Domain-Driven Design
- Monólito modular
- Idempotência
- Concorrência
- Consistência de dados
- Processamento assíncrono
- Mensageria
- Cache
- Segurança
- Testes automatizados
- Observabilidade
- Escalabilidade

## Arquitetura

O projeto utiliza uma abordagem de **monólito modular orientado a DDD**, com separação clara entre os principais domínios da aplicação.

Cada módulo possui suas próprias responsabilidades e segue uma organização baseada em camadas:

```text
module/
├── domain/
├── application/
├── infrastructure/
└── interfaces/
```

A proposta é manter o domínio desacoplado de detalhes de infraestrutura, permitindo uma evolução arquitetural gradual e justificável.

## Domínios principais

Inicialmente, o sistema será dividido nos seguintes módulos:

- Order
- Inventory
- Payment
- Fulfillment
- Shipping
- Customer
- Catalog
- Identity

## Fluxo principal

```text
Criação do pedido
        ↓
Reserva de estoque
        ↓
Processamento do pagamento
        ↓
Separação
        ↓
Embalagem
        ↓
Expedição
        ↓
Entrega
```

Cada etapa possui suas próprias regras, estados e eventos de domínio.

## Stack

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Modulith
- PostgreSQL
- Redis
- RabbitMQ
- Flyway
- JUnit
- Mockito
- Testcontainers

### Frontend

- React
- TypeScript
- Vite
- TanStack Query

### Infraestrutura

- Docker
- Docker Compose
- Prometheus
- Grafana
- OpenTelemetry

## Conceitos que serão explorados

Entre os principais problemas técnicos abordados pelo projeto estão:

- Prevenção de pedidos duplicados
- Idempotência de requisições e eventos
- Concorrência na reserva de estoque
- Controle de estados do pedido
- Processamento assíncrono
- Falhas entre processos
- Retry e Dead Letter Queue
- Transactional Outbox
- Cache e invalidação
- Eventual consistency
- Observabilidade de fluxos distribuídos

## Estrutura inicial

```text
order-flow/
├── backend/
├── frontend/
├── docs/
│   ├── architecture/
│   ├── adr/
│   ├── diagrams/
│   └── domain/
├── docker/
└── README.md
```

## Documentação arquitetural

As principais decisões arquiteturais serão registradas utilizando ADRs — Architecture Decision Records.

Exemplos:

```text
ADR-001 - Monólito modular
ADR-002 - Estrutura baseada em DDD
ADR-003 - RabbitMQ como message broker
ADR-004 - Transactional Outbox
ADR-005 - Estratégia de concorrência no estoque
ADR-006 - Redis como camada de cache
```

O objetivo é documentar não apenas **o que foi implementado**, mas principalmente **por que determinada decisão foi tomada**.

## Status

🚧 Projeto em desenvolvimento.

A arquitetura, os módulos e as funcionalidades serão evoluídos de forma incremental ao longo do desenvolvimento.

## Motivação

O OrderFlow faz parte de uma iniciativa pessoal de aprofundamento em desenvolvimento backend com Java e arquitetura de software.

Mais do que construir uma aplicação funcional, o projeto busca explorar decisões técnicas que aparecem em sistemas corporativos reais e compreender os impactos de cada escolha arquitetural.