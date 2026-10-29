# 🖨️ PrintGest - Sistema de Gestão para Gráficas (Projeto Final)

> **Diga adeus às tabelas de preço manuais.** Orçamentos para rótulos e gestão de produção em segundos.

![Java](https://img.shields.io/badge/java_25.0.1-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Projeto desenvolvido como trabalho final do curso Técnico de Desenvolvimento de Sistemas do **SENAI**.

---

## 📌 O Problema
No dia a dia de gráficas modernas, calcular orçamentos de rótulos e impressos costuma ser um processo manual, demorado e sujeito a erros. É necessário conferir tabelas de preço de papel, calcular área (altura x largura), prever o aproveitamento da folha e aplicar faixas de desconto por quantidade.

## 💡 A Solução
O **PrintGest** automatiza esse processo. É um sistema em **Java** (interface com `JOptionPane`) que calcula orçamentos instantaneamente e gerencia clientes, funcionários e pedidos (CRUD), com todos os dados salvos em um banco **PostgreSQL**.

O código segue uma **arquitetura em camadas** (Model, DAO, Service, Controller e View), separando a interface, as regras de negócio e o acesso ao banco.

A página de divulgação do projeto (landing page PWA, com um simulador de orçamento) fica em outro repositório: [Projeto-Final-PPA-LP](https://github.com/lucasdbf/Projeto-Final-PPA-LP).

---

## ⚙️ Principais Funcionalidades

- **🖩 Motor de Orçamento Inteligente:** Informe o tipo de papel, as dimensões do rótulo e a quantidade. O sistema calcula o melhor aproveitamento da folha, o número de folhas e o valor final com base em faixas de quantidade.
- **👥 Gestão de Clientes:** Cadastro, listagem, atualização e exclusão. Ao excluir um cliente, os pedidos dele também são removidos.
- **📦 Gestão de Pedidos:** Cada pedido é vinculado a um cliente. O valor é recalculado a cada cadastro ou alteração.
- **👷 Controle de Funcionários:** Cadastro, listagem, atualização e exclusão da equipe.
- **✅ Validação de Dados:** Campos vazios ou inválidos não são aceitos. O sistema mostra, ao final, uma mensagem listando todos os erros encontrados.
- **🗄️ Persistência em PostgreSQL:** Os dados continuam salvos depois de fechar o programa.

---

## 🚀 Tecnologias Utilizadas

**Sistema:**
- **Java 25.0.1** (Orientação a Objetos, arquitetura em camadas)
- **JDBC** com o driver `postgresql-42.7.13` (incluído em `libs/`)
- **PostgreSQL** como banco de dados
- Interface nativa via `JOptionPane`

---

## 📂 Estrutura do Repositório

```text
📁 Projeto-Final-PPA-PrintGest/
│
├── 📁 Projeto Final Java/           # Sistema em Java
│   ├── 📁 libs/                     # Driver JDBC do PostgreSQL
│   ├── 📁 resources/                # Favicon usado nos diálogos (PrintGestFavicon.png)
│   └── 📁 src/
│       ├── 📄 Principal.java        # Ponto de entrada e menu principal
│       ├── 📁 models/               # Cliente, Funcionario e Pedido
│       ├── 📁 dao/                  # Acesso ao banco (SQL)
│       ├── 📁 services/             # Regras de negócio, validações e cálculo de preço
│       ├── 📁 controllers/          # Fluxo de cada CRUD
│       ├── 📁 views/                # Menus e diálogos (JOptionPane)
│       ├── 📁 connections/          # Conexão com o PostgreSQL
│       ├── 📁 exceptions/           # Exceções de cada módulo
│       ├── 📁 utils/                # Conversões e carregamento do favicon
│       └── 📁 tests/                # Testes de integração com o banco
├── 📄 LICENSE                       # Licença MIT
└── 📄 README.md                     # Documentação do projeto
```

---

## 💻 Como executar o projeto

### 1. Preparar o banco de dados
Instale o **PostgreSQL**, crie o banco `projeto_final` e execute o script abaixo:

```sql
CREATE TABLE tbcliente (
    cdcliente          SERIAL PRIMARY KEY,
    nmcliente          VARCHAR(100) NOT NULL,
    nutelefone_cliente VARCHAR(15)
);

CREATE TABLE tbfuncionario (
    cdfuncionario       SERIAL PRIMARY KEY,
    nmfuncionario       VARCHAR(100) NOT NULL,
    nmcargo_funcionario VARCHAR(100)
);

CREATE TABLE tbpedido (
    cdpedido       SERIAL,
    nome_papel     VARCHAR(255),
    altura_rotulo  NUMERIC(10,2),
    largura_rotulo NUMERIC(10,2),
    qntd_rotulos   INTEGER,
    qntd_folha     NUMERIC(10,2),
    valor          NUMERIC(10,2),
    cdcliente      INTEGER NOT NULL,
    PRIMARY KEY (cdpedido, cdcliente),
    CONSTRAINT fk_tbpedido_cliente FOREIGN KEY (cdcliente) REFERENCES tbcliente (cdcliente)
);
```

Em seguida, confira a URL, o usuário e a senha em `Projeto Final Java/src/connections/ConexaoSQL.java` e ajuste para o seu ambiente (por padrão: `localhost:5432`, banco `projeto_final`, usuário `postgres`).

### 2. Executar o Sistema Java
Certifique-se de ter o **JDK 25.0.1** (ou compatível) instalado. Na pasta `Projeto Final Java`, execute:

```bash
# Compilar
javac -encoding UTF-8 -cp libs/postgresql-42.7.13.jar -d out $(find src -name '*.java')

# Executar
java -cp out:libs/postgresql-42.7.13.jar Principal
```

No Windows, troque os `:` do classpath por `;`. Também é possível abrir a pasta no IntelliJ IDEA e executar a classe `Principal`.

> **Favicon:** para exibir o ícone nos diálogos, coloque a imagem em `Projeto Final Java/resources/PrintGestFavicon.png`. Sem ela, o sistema funciona normalmente, apenas sem o ícone.

### 3. Executar os testes (opcional)
Os testes fazem cadastro, listagem, alteração e exclusão de verdade no banco e apagam o que criam. Ao terminar, devolvem as sequências de ID ao primeiro número livre, então os IDs não pulam.

```bash
java -cp out:libs/postgresql-42.7.13.jar tests.Teste
```

---

## 📄 Licença
Distribuído sob a licença MIT. Veja o arquivo `LICENSE`.
