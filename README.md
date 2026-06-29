# API - Cadastro de Desenvolvedores

## Sobre o Projeto
API REST para cadastro e gerenciamento de desenvolvedores Back-End e Front-End,
desenvolvida como projeto de portfólio com foco na aplicação prática de Design Patterns.

## Design Patterns Aplicados

| Padrão | Onde foi aplicado |
|---|---|
| **Singleton** | Implícito via container IoC do Spring — todos os `@Service` e `@Repository` |
| **Facade** | `ViaCepFacade` — abstrai a integração com a API externa ViaCEP |
| **Adapter** | `ViaCepResponse` — mapeia os campos em português do ViaCEP para o modelo interno |
| **Builder** | `@SuperBuilder` nas entidades `DeveloperCLT` e `DeveloperPJ` |
| **Strategy** | `CltStrategy` e `PjStrategy` — isolam as regras de negócio por tipo de contrato |
| **Observer** | `DeveloperRegisteredEvent` — log de auditoria disparado a cada cadastro |

## Regras de Negócio por Tipo de Contrato

### CLT
- Férias Remuneradas ✅
- 13º Salário ✅
- Sem período de contrato definido

### PJ
- Férias Remuneradas ✅
- 13º Salário ❌
- Possui período de contrato (DataInicio + PeriodoEmMeses)

## Atributos dos Desenvolvedores

### Dados Pessoais
- Nome e Sobrenome
- Data de Nascimento

### Dados Profissionais
- Empresa
- Salário
- Tipo de Desenvolvedor (`BACK`, `FRONT`)
- Tipo de Contrato (`CLT`, `PJ`)

### Dados Contratuais
- Data de Admissão (CLT) / Início do Contrato (PJ)
- Período do Contrato em meses (exclusivo PJ)
- Data de Férias

### Endereço (via ViaCEP)
- CEP, Logradouro, Bairro, Cidade, Estado

## Tecnologias

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- Spring Validation
- H2 Database (in-memory)
- Lombok
- OpenFeign
- SpringDoc OpenAPI (Swagger)
- JUnit 5 + Mockito

## Arquitetura
MVC com separação de responsabilidades por camadas:
`controller` → `service` → `repository`

Patterns organizados em pacotes dedicados dentro de `service`:
- `strategy` — Strategies e Resolver
- `integration/viacep` — Facade e Adapter
- `event` — Observer

## Como Rodar

```bash
./mvnw spring-boot:run
```

A aplicação sobe na porta `8080` com banco H2 em memória.

## Documentação dos Endpoints

Acesse o Swagger após subir a aplicação:
http://localhost:8080/swagger-ui/index.html

### Endpoints CLT — `/dev/clt`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/dev/clt` | Cadastrar desenvolvedor CLT |
| GET | `/dev/clt` | Listar todos os desenvolvedores CLT |
| GET | `/dev/clt/{id}` | Buscar desenvolvedor CLT por ID |
| PUT | `/dev/clt/{id}` | Atualizar desenvolvedor CLT |
| DELETE | `/dev/clt/{id}` | Deletar desenvolvedor CLT |

### Endpoints PJ — `/dev/pj`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/dev/pj` | Cadastrar desenvolvedor PJ |
| GET | `/dev/pj` | Listar todos os desenvolvedores PJ |
| GET | `/dev/pj/{id}` | Buscar desenvolvedor PJ por ID |
| PUT | `/dev/pj/{id}` | Atualizar desenvolvedor PJ |
| DELETE | `/dev/pj/{id}` | Deletar desenvolvedor PJ |