# API - Cadastro de Desenvolvedores

## Ideia
Minha ideia é criar uma API Rest para cadastro de Desenvolvedores: Back-End e Front-End
### Padrões de Projetos usados:
- Facade - Usar para fluxos completos
- Adapter - Adptar uma interface para que elas consigam conversar
- Builder - Para classes que tenham muitos atributos opcionais / Construtores Globais
- Factory Method - Quando a criação do Objeto for complexa / Quando o objetivo for desacoplar quem usa de quem cria
- Strategy - Usar quando tiver regras diferentes
- Observer - Para mandar logs de auditoria simples
- Singleton - Instanciar uma única vez

## Atributos dos Meus Devs

### Dados pessoais
- Nome  
- Sobrenome
- Data de Nascimento

### Dados profissionais

- Empresa
- Salário
- Tipo de Desenvolvedor (BACK_END, FRONT_END)
- Tipo de Contrato (CLT, PJ)

### Dados contratuais

- Data de Admissão / Início do Contrato
- Período do Contrato (em meses — exclusivo PJ)
- Data de Férias

### Endereço (via ViaCEP)

- CEP
- Logradouro
- Bairro
- Cidade
- Estado

## Regras de Negócio por Tipo de Contrato:

### CLT
- Ferias Remuneradas ✅
- 13º Salário ✅
- Sem período de contrato definido

### PJ
- Férias Remuneradas ✅
- 13º Salário ❌
- Possui período de contrato (DataInicio + PeriodoEmMeses) 

## Arquitetura Usada:
MVC

## Dependencias:
- Spring Web
- Spring Data JPA
- Spring Validation
- H2 Database
- Lombok
- OpenFeign
- SpringDoc OpenAPI
- SpringBoot Test
- JUnit 5
