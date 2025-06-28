# Sistema de Pedidos para um Marketplace de Restaurantes

### Descrição

Uma API que gerencia pedidos realizados por clientes em diferentes restaurantes, com integração de pagamentos, notificações assíncronas e controle de estoque.

### Funcionalidades principais

* Cadastro e autenticação de usuários (cliente e restaurante)
* Catálogo de produtos por restaurante
* Criação de pedidos com múltiplos itens
* Processamento de pedidos via fila (RabbitMQ)
* Atualização assíncrona de status do pedido (pago, em preparo, entregue)
* Controle de estoque por restaurante
* Relatórios simples de vendas

### Tecnologias

* **Java + Spring Boot**: API REST
* **PostgreSQL**: armazenamento de dados
* **RabbitMQ**: orquestração de eventos (ex: “PedidoCriado”, “PagamentoConfirmado”)
* **Spring Security + JWT**: autenticação
* **Flyway**: versionamento de banco de dados (opcional)

### Arquitetura Inicial – Sistema de Pedidos para Marketplace de Restaurantes

---

#### **1. Camadas do Sistema (Hexagonal / Ports & Adapters)**

```
[ API REST (Controller) ]
        ↓
[ Casos de Uso (Services) ]
        ↓
[ Domínio (Entidades + Regras de Negócio) ]
        ↓
[ Adapters: ]
   - Banco de Dados (PostgreSQL via JPA)
   - Mensageria (RabbitMQ)
   - Externos (Pagamentos, etc)
```

---

#### **2. Módulos Principais**

* **auth**: autenticação/validação de tokens JWT
* **user**: cadastro de usuários (cliente e restaurante)
* **menu**: cadastro de pratos e categorias por restaurante
* **order**: criação e rastreio de pedidos
* **payment**: simulação de pagamento e emissão de evento
* **notification**: consumidor de mensagens para status do pedido
* **inventory**: controle de estoque

---

#### **3. RabbitMQ – Eventos e Filas**

* **Exchange:** `pedido.events`

* **Eventos Publicados:**

  * `PedidoCriado`
  * `PagamentoRecebido`
  * `PedidoFinalizado`

* **Consumidores:**

  * `payment-service` escuta `PedidoCriado`
  * `order-service` escuta `PagamentoRecebido`
  * `notification-service` escuta `PedidoFinalizado`

---

#### **4. Banco de Dados (PostgreSQL – modelo simplificado)**

**Usuário**

```sql
id (PK), nome, email, senha, tipo (CLIENTE/RESTAURANTE)
```

**Restaurante**

```sql
id (PK), usuario_id (FK), nome, cnpj
```

**Produto**

```sql
id (PK), restaurante_id (FK), nome, preço, estoque
```

**Pedido**

```sql
id (PK), cliente_id (FK), restaurante_id (FK), status, total
```

**ItemPedido**

```sql
id (PK), pedido_id (FK), produto_id (FK), quantidade, subtotal
```

---

#### **5. Tecnologias Complementares**

* **Spring Boot Web + Validation**
* **Spring Data JPA + PostgreSQL**
* **Spring AMQP (RabbitMQ)**
* **Spring Security + JWT**
* **Lombok / MapStruct (opcional)**
* **Testes:** JUnit + Testcontainers (RabbitMQ/Postgres)

---

Deseja que eu inicie com a estrutura do projeto ou o esboço de um caso de uso (ex: criação de pedido)?
