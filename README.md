'''
# ✈️ Gestão Interna de Aeroporto (Fase 2 - JDBC/MySQL)

Projeto acadêmico de **Programação Orientada a Objetos (POO)** que implementa um sistema de gestão da parte interna de um aeroporto. Esta é a **Fase 2**, que evolui o projeto para usar persistência de dados com **MySQL e JDBC puro**, abandonando o armazenamento em memória.

O sistema permite a administração completa de passageiros, voos, companhias aéreas, e todos os processos internos como compra de passagens, check-in, despacho de bagagens e geração de relatórios, utilizando uma interface de console baseada em `Scanner` e `System.out.println`.

---

## 📋 Funcionalidades Implementadas

- **Interface de Console**: Menus interativos para todas as operações, sem uso de `JOptionPane`.
- **CRUDs Completos**: Gerenciamento das 11 entidades principais do sistema.
- **Busca de Voos**: Pesquisa por origem, destino e faixa de datas.
- **Compra de Passagens**: Criação de um `Ticket` associado a um passageiro e um voo.
- **Check-in Online**: Implementa a regra de negócio que permite o check-in apenas **até 24 horas antes do voo**.
- **Geração de Boarding Pass**: Criação de um cartão de embarque após o check-in bem-sucedido.
- **Controle de Lotação**: Impede a venda de passagens para voos que já atingiram a capacidade máxima.
- **Relatórios Gerenciais**: Geração de relatórios de texto para:
  - Passageiros por aeroporto (saída/chegada).
  - Valor arrecadado por companhia aérea.
  - Lista de passageiros de um voo.

---

## 🛠️ Tecnologias e Padrões

- **Linguagem**: Java 11+
- **Build Tool**: Apache Maven
- **Banco de Dados**: MySQL 8+
- **Conexão**: JDBC (puro, com `PreparedStatement` para evitar SQL Injection)
- **Testes**: JUnit 4 para testes de integração e regras de negócio.
- **Padrões de Projeto**:
  - **DAO (Data Access Object)**: Separa a lógica de acesso a dados da lógica de negócio.
  - **Camada de Serviço**: Centraliza as regras de negócio.
  - **Singleton**: Utilizado na classe `DatabaseConnection` para gerenciar a conexão.
- **Princípios de POO**: O projeto aplica os quatro pilares da POO, detalhados no arquivo `OO_Documentation.md`.

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e rodar o projeto em seu ambiente local.

### 1. Pré-requisitos

- **Java JDK 11** ou superior.
- **Apache Maven** 3.6 ou superior.
- **MySQL Server 8** ou superior (rodando localmente).

### 2. Configuração do Banco de Dados

O projeto precisa de um banco de dados chamado `aeroporto_db`. O arquivo `aeroporto_db.sql` na raiz do projeto contém todo o schema e os dados de exemplo.

**a. Acessar o MySQL via terminal:**

```sh
mysql -u root -p
```

Digite sua senha de root do MySQL quando solicitado.

**b. Importar o script SQL:**

Dentro do cliente MySQL, execute o comando abaixo para criar o banco e importar os dados. Certifique-se de que você está no diretório raiz do projeto (`Gestao-interna-aeroporto`) ao executar o comando no terminal que chamará o MySQL.

```sql
SOURCE aeroporto_db.sql;
```

Ao final, você verá a mensagem `Banco de dados aeroporto_db criado com sucesso!`.

### 3. Configuração da Conexão

O projeto lê as credenciais do banco de dados do arquivo `config.properties` na raiz do projeto.

```properties
# Configuração do Banco de Dados MySQL
# Para alterar a senha, modifique o valor de db.password abaixo

db.host=127.0.0.1
db.port=3306
db.name=aeroporto_db
db.user=root
db.password=
```

**IMPORTANTE**: Se o seu usuário `root` do MySQL tiver uma senha, **altere o campo `db.password`** para a sua senha. Por padrão, ele está configurado com a senha em branco.

### 4. Compilar o Projeto com Maven

Abra um terminal na raiz do projeto (`Gestao-interna-aeroporto`) e execute o comando Maven para compilar o projeto e baixar as dependências.

```sh
mvn clean install
```

Este comando irá compilar o código-fonte, executar os testes e criar um arquivo JAR executável no diretório `target/`.

### 5. Executar os Testes (Opcional)

Para rodar apenas os testes de unidade e integração, use o comando:

```sh
mvn test
```

Os testes verificam a conexão com o banco, a funcionalidade dos DAOs e as regras de negócio (check-in 24h, voo lotado).

### 6. Executar o Sistema

Após a compilação, você pode executar o sistema de duas formas.

**a. Via Maven:**

```sh
mvn exec:java -Dexec.mainClass="main.Main"
```

**b. Via JAR Executável:**

O Maven gera um JAR "fat" com todas as dependências incluídas. Execute-o com o seguinte comando:

```sh
java -jar target/gestao-interna-aeroporto-2.0.0-jar-with-dependencies.jar
```

Após a execução, o menu principal do sistema será exibido no console, e você poderá interagir com todas as funcionalidades.

---

## 📂 Estrutura de Pacotes

O projeto está organizado nos seguintes pacotes:

- `main`: Contém a classe `Main` que inicia o sistema e o menu de console.
- `model`: Contém as classes de entidade (POJOs) que representam os dados (ex: `Passageiro`, `Voo`).
- `dao`: Contém as classes do padrão **Data Access Object**, responsáveis pela comunicação com o banco de dados (`PassageiroDAO`, `VooDAO`).
- `service`: Contém a **camada de serviço**, que orquestra as operações e aplica as regras de negócio (`VooService`, `CheckInService`).
- `util`: Contém classes utilitárias, como `DatabaseConnection` e `DateUtil`.
- `tests`: Contém os testes de unidade e integração com JUnit.

'''
