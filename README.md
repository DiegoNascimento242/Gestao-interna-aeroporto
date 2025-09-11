# ✈️ Gestao-interna-aeroporto (POO)

Projeto acadêmico de **Programação Orientada a Objetos (POO)** cujo objetivo é desenvolver um sistema de **gestão da parte interna de um aeroporto**.  
O sistema permite a administração de passageiros, voos, companhias aéreas, aeroportos e processos internos (check-in, despacho de bagagens, boarding pass, etc.), utilizando o conceito de **CRUD** (Create, Read, Update e Delete).  

---

## 📌 Funcionalidades

### Telas iniciais
- Painel de voos de um dado dia.
- Tela administrativa.
- Compra de passagens (sem login).
- Gestão de passagens.

### CRUDs implementados
- **Passageiro**: id, nome, nascimento, documento, login, senha, datas de criação e modificação.  
- **Aeroporto**: id, nome, abreviação, cidade, datas.  
- **Companhia aérea**: id, nome, abreviação, datas.  
- **Voo**: id, origem, destino, data, duração, companhia aérea, capacidade, estado, datas.  
- **Assentos do voo**: id, voo, código do assento, passageiro, datas.  
- **Ticket**: id, valor, voo, passageiro, código (id+voo ou sequência aleatória), datas.  
- **Check-in**: id, ticket, documento, datas (só disponível 24h antes do voo).  
- **Despacho de bagagem**: id, ticket, documento, datas.  
- **Boarding Pass**: geração de cartão de embarque com dados do passageiro, voo e reserva.  

### Outras operações
- Busca de voos por origem, destino e datas.  
- Opção de ida e volta.  
- Escolha de assento no avião.  
- Entrada no aeroporto (registro de acesso).  
- Entrada no avião/voo.  

---

## 📊 Relatórios gerenciais
- Passageiros que saíram de um aeroporto.  
- Passageiros que chegaram a um aeroporto.  
- Valor arrecadado por companhia aérea em determinado período.  
- Boarding Pass.  
- Voos de um dado dia em um aeroporto.  
- Lista de passageiros de um voo.  

---

## 👥 Perfis de usuário
1. **Administrador geral** – gerenciamento de todos os CRUDs e relatórios.  
2. **Funcionário do aeroporto** – operações administrativas específicas.  
3. **Cliente (passageiro)** – consulta de voos, compra de passagem, check-in e seleção de assentos.  

---

## 🛠️ Tecnologias utilizadas
- **Linguagem:** Java  
- **Paradigma:** Programação Orientada a Objetos (POO)  
- **Padrão DAO:** para manipulação dos dados  
- **Entrada/Saída:** `System.out.println`, `Scanner`, `JOptionPane`  

### 📍 Fase 1
- Armazenamento em memória (sem banco de dados).  
- Sem interface gráfica (apenas console).   

### 📍 Fase 2
- Integração com banco de dados.  
- Uso permitido de **Collections Framework**.  
- Geração de relatórios em **PDF**.  
- Importação de alunos a partir de arquivo texto.  

---

## 🚀 Funcionalidades extras (opcionais)
- Políticas de acesso.  
- Gráficos e relatórios avançados.  
- Persistência com frameworks.  
- Geração de boletos bancários.  
- Agendamento de tarefas.  
