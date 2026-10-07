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
├── docker-compose.yml
├── docker-compose.dev.yml
├── .env.example
├── .editorconfig
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

## Fundação executável

O estado atual contém apenas infraestrutura e uma página inicial com status do backend.
Os domínios e o fluxo descritos acima são planejamento, ainda não implementados.
TanStack Query, Prometheus, Grafana e OpenTelemetry também ficam para etapas futuras.

### Versões e organização

- Java 21, Maven 3.9.16 (Wrapper), Spring Boot 4.0.8 e Spring Modulith 2.0.8.
- React 19.3, TypeScript 7 e Vite 8; Node 24 recomendado.
- PostgreSQL 17, Redis 7.4 e RabbitMQ 4.1 com Management UI.
- Backend em `backend/src/main/java/com/orderflow`: aplicação e configuração técnica.
- Frontend em `frontend/src`: `app`, `config` e `lib` (client HTTP com fetch).
- `docs/adr`, `docs/architecture`, `docs/diagrams` e `docs/domain` reservados para documentação.

Os futuros módulos serão pacotes dentro de `com.orderflow`, com camadas `domain`,
`application`, `infrastructure` e `interfaces` conforme necessário. O Modulith detecta
apenas módulos explicitamente anotados, evitando tratar `config` como domínio.
Não há Lombok, entidades, eventos de negócio ou abstrações de persistência nesta etapa.

### Subir tudo com Docker

Pré-requisito: Docker Engine/Desktop em execução com containers Linux e Compose v2+.
Não é necessário instalar Java, Maven ou Node no host para esse modo.

```powershell
Copy-Item .env.example .env
docker compose config --quiet
docker compose up -d --build --wait --wait-timeout 300
docker compose ps
Invoke-RestMethod http://localhost:8080/actuator/health
```

Em Linux/macOS, use `cp .env.example .env` e `curl` para a consulta HTTP.
Os valores padrão são exclusivamente locais. `.env` é ignorado pelo Git.

| Serviço | Acesso no host | Porta interna |
| --- | --- | --- |
| Frontend (Nginx) | http://localhost:3000 | 8080 |
| Backend | http://localhost:8080/actuator/health | 8080 |
| RabbitMQ Management | http://localhost:15672 | 15672 |
| PostgreSQL | Apenas rede Docker | 5432 |
| Redis | Apenas rede Docker | 6379 |
| RabbitMQ AMQP | Apenas rede Docker | 5672 |

As portas publicadas ficam restritas a `127.0.0.1` e podem ser alteradas no `.env`.
Login local do RabbitMQ: `orderflow` / `orderflow_local`, configurável no `.env`.
PostgreSQL, Redis (AOF) e RabbitMQ possuem volumes persistentes. Alterar uma senha
no `.env` não altera automaticamente credenciais já gravadas nesses volumes.

```sh
docker compose logs -f backend
docker compose stop
docker compose down
```

`down` preserva dados; adicionar `--volumes` remove os dados locais permanentemente.

### Desenvolvimento no host

Requisitos: JDK 21, Node 24 e Docker em execução. O Maven Wrapper baixa o Maven.
Se a aplicação completa estiver rodando, pare backend/frontend para liberar portas:

```sh
docker compose stop backend frontend
docker compose -f docker-compose.yml -f docker-compose.dev.yml up -d --wait postgres redis rabbitmq
```

O arquivo adicional publica PostgreSQL em `5433`, Redis em `6380` e AMQP em `5673`,
sempre no loopback, evitando as portas padrão frequentemente usadas por outros projetos.

```powershell
cd backend
./mvnw.cmd spring-boot:run
```

Em outro terminal:

```powershell
cd frontend
Copy-Item .env.example .env
npm ci
npm run dev
```

Frontend de desenvolvimento: http://localhost:5173. Em Linux/macOS use `sh mvnw`.
O profile padrão é `local`; `dev` ativa a mesma configuração. No Docker, o profile é
`docker`, com credenciais obrigatórias injetadas pelo Compose. Para credenciais locais
personalizadas, exporte `DB_PASSWORD` e `RABBITMQ_PASSWORD` no terminal/IDE: o Spring
não lê automaticamente o `.env` da raiz. Também são configuráveis `DB_URL`,
`DB_USERNAME`, `REDIS_HOST`, `REDIS_PORT`, `RABBITMQ_HOST`, `RABBITMQ_PORT`,
`RABBITMQ_USERNAME`, `SERVER_PORT` e `LOG_LEVEL`.

No frontend, `VITE_API_BASE_URL` é público e resolvido no build; nunca deve conter
segredos. O padrão `/api` usa proxy no Vite e no Nginx, removendo esse prefixo antes
de encaminhar ao backend. `API_PROXY_TARGET` configura somente o proxy do Vite.

### Testes e builds

```powershell
cd backend
./mvnw.cmd -B test
./mvnw.cmd -B -DskipTests package
cd ../frontend
npm ci
npm run build
cd ..
docker compose config --quiet
```

Os testes não dependem dos serviços do Compose: Testcontainers cria PostgreSQL,
RabbitMQ e Redis temporários com portas aleatórias. Docker é obrigatório; os testes
não são silenciosamente ignorados quando ele está indisponível. São verificados o
contexto Spring, health `UP`, integração Flyway, bloqueio de endpoints não públicos
e limites de módulos com Modulith.

Se o JDK no Windows apresentar `Unable to establish loopback connection` /
`Invalid argument: connect` em `UnixDomainSockets` ao iniciar o Tomcat, configure
um diretório temporário curto e alternativo:

```powershell
$socketDir = Join-Path $env:USERPROFILE '.orderflow/tmp'
New-Item -ItemType Directory -Force $socketDir | Out-Null
./mvnw.cmd -B "-DargLine=-Djdk.net.unixdomain.tmpdir=$socketDir" test
```

Para `spring-boot:run` nesse mesmo ambiente, use
`./mvnw.cmd "-Dspring-boot.run.jvmArguments=-Djdk.net.unixdomain.tmpdir=$socketDir" spring-boot:run`.
Essa propriedade é documentada em [Java Networking](https://docs.oracle.com/en/java/javase/17/core/java-networking.html).

Flyway está habilitado em `backend/src/main/resources/db/migration`, ainda sem
migrações de negócio. Ele cria sua tabela de histórico; Hibernate apenas valida o
schema (`ddl-auto: validate`) e não cria tabelas. A ausência de migrações é esperada.

A observabilidade inicial usa logs no console, Actuator e health de PostgreSQL,
Redis e RabbitMQ. Somente health é exposto por HTTP, sem detalhes internos;
`/actuator/health/liveness` e `/actuator/health/readiness` estão habilitados.
Spring Security bloqueia as demais rotas até ser definida a autenticação do produto.

## Motivação

O OrderFlow faz parte de uma iniciativa pessoal de aprofundamento em desenvolvimento backend com Java e arquitetura de software.

Mais do que construir uma aplicação funcional, o projeto busca explorar decisões técnicas que aparecem em sistemas corporativos reais e compreender os impactos de cada escolha arquitetural.
