# OrderFlow — Diretrizes para Agentes

Este arquivo define o padrão esperado para qualquer agente de IA que trabalhe no projeto OrderFlow.

## Princípios gerais

O código deve ser:

- limpo;
- funcional;
- simples;
- legível;
- fácil de manter;
- sem abstrações desnecessárias;
- sem complexidade artificial;
- sem "firulas" arquiteturais.

Priorize sempre clareza e manutenção.

## Formatação

Utilize uma formatação visualmente espaçada.

Evite código excessivamente compacto.

Deve existir separação clara entre:

- atributos;
- construtores;
- métodos públicos;
- métodos privados;
- classes internas, quando existirem;
- blocos lógicos relevantes.

Prefira código como:

```java
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Order createOrder(CreateOrderCommand command) {
        var order = Order.create(command.customerId());

        return orderRepository.save(order);
    }


    private void validateOrder(Order order) {
        // Valida regras necessárias antes do processamento.
    }
}
```

Evite formatos excessivamente comprimidos.

## Comentários

Comentários devem ser breves e objetivos.

Use comentários somente quando ajudarem a explicar:

- uma regra de negócio;
- uma decisão não óbvia;
- uma limitação técnica;
- um workaround;
- um comportamento importante.

Evite comentar o óbvio.

Não use comentários como:

```java
// Cria o pedido
var order = new Order();
```

Prefira nomes claros de classes, métodos e variáveis.

## Classes

Cada classe deve possuir uma responsabilidade clara.

Evite:

- classes gigantes;
- métodos muito longos;
- múltiplas responsabilidades;
- abstrações criadas sem necessidade;
- herança sem justificativa;
- padrões de projeto aplicados apenas por estética.

Se uma solução simples resolve corretamente o problema, prefira a solução simples.

## Métodos

Métodos devem ser:

- pequenos;
- objetivos;
- com nomes descritivos;
- focados em uma única responsabilidade.

Extraia métodos quando isso melhorar a leitura.

Não fragmente o código excessivamente apenas para reduzir quantidade de linhas.

## Nomenclatura

Utilize nomes claros e orientados ao domínio.

Prefira:

```text
reserveStock()
confirmPayment()
cancelOrder()
calculateTotal()
```

Evite:

```text
process()
handle()
executeStuff()
doSomething()
```

quando houver um nome de domínio mais preciso.

## Java

Para Java:

- siga as convenções oficiais de nomenclatura;
- prefira imutabilidade quando possível;
- utilize `final` quando fizer sentido;
- evite setters públicos indiscriminadamente;
- preserve as invariantes das entidades;
- utilize exceptions específicas para regras relevantes;
- evite `Optional` como atributo de entidade;
- não utilize streams quando um `for` simples for mais legível;
- não introduza generics complexos sem necessidade.

## Spring Boot

Evite colocar regra de negócio em:

- Controllers;
- Repositories;
- configurações;
- consumers;
- listeners.

Controllers devem receber a requisição, delegar para a aplicação e retornar a resposta.

Regras de domínio devem permanecer no domínio.

## Arquitetura

O backend utiliza:

- monólito modular;
- DDD;
- arquitetura em camadas.

Estrutura esperada por módulo:

```text
module/
├── domain/
├── application/
├── infrastructure/
└── interfaces/
```

Respeite as responsabilidades entre as camadas.

Não crie dependências desnecessárias entre módulos.

## DDD

Priorize comportamento dentro das entidades e agregados.

Prefira:

```java
order.cancel();
```

em vez de:

```java
order.setStatus(OrderStatus.CANCELLED);
```

Entidades devem proteger suas próprias invariantes sempre que possível.

Evite modelos anêmicos sem necessidade.

## React e TypeScript

No frontend:

- mantenha componentes pequenos e claros;
- separe lógica de apresentação quando necessário;
- evite componentes gigantes;
- evite estados duplicados;
- evite `any`;
- utilize tipagem explícita onde melhorar a compreensão;
- prefira funções e hooks com responsabilidade clara.

Não crie abstrações genéricas antes de existir uma necessidade real.

## Arquivos

Evite arquivos gigantes.

Mantenha organização previsível.

Não crie uma grande quantidade de arquivos apenas para seguir um padrão arquitetural.

A estrutura deve existir para facilitar o entendimento do sistema, não para aumentar sua complexidade.

## Testes

Testes devem validar comportamento.

Prefira nomes que expliquem claramente o cenário.

Exemplo:

```java
shouldNotReserveStockWhenQuantityIsInsufficient()
```

Evite testes que apenas verificam implementação interna sem valor de negócio.

## Antes de implementar

Antes de criar uma nova abstração, pergunte:

1. Qual problema ela resolve?
2. O projeto realmente precisa disso agora?
3. A solução mais simples seria suficiente?
4. Isso melhora legibilidade ou apenas aumenta a quantidade de código?

## Ao alterar código existente

Antes de modificar:

1. analise a implementação atual;
2. preserve padrões válidos já existentes;
3. não reescreva código desnecessariamente;
4. evite mudanças fora do escopo solicitado;
5. execute testes e builds relevantes após a alteração.

## Resultado esperado

O OrderFlow deve possuir código com aparência profissional, mas simples.

O objetivo não é demonstrar quantos padrões ou abstrações podem ser utilizados.

O objetivo é construir um sistema:

- bem arquitetado;
- fácil de entender;
- fácil de testar;
- fácil de evoluir;
- tecnicamente defensável.
