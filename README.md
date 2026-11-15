# 🎓 Sistema de Matrículas 👨‍💻

> [!NOTE]
> Sistema web para informatizar as matrículas de uma universidade: a secretaria monta o currículo do semestre, os alunos se matriculam (e cancelam) dentro do período de matrículas e os professores consultam os alunos de cada disciplina.

<table>
  <tr>
    <td width="800px">
      <div align="justify">
        O <b>Sistema de Matrículas</b> é o protótipo desenvolvido no <b>Laboratório 03</b> da disciplina <i>Desenvolvimento e Integração de Aplicações Web (DIAW)</i> do curso de Engenharia de Software. A secretaria mantém <i>cursos</i>, <i>disciplinas</i>, <i>professores</i> e <i>alunos</i> e gera o <i>currículo</i> de cada semestre. Durante o <b>período de matrículas</b>, cada aluno escolhe até <b>4 disciplinas obrigatórias</b> (1ª opção) e <b>2 optativas</b>. Ao final do período, as disciplinas com pelo menos <b>3 alunos</b> ficam ativas e as demais são canceladas; uma disciplina aceita no máximo <b>60 alunos</b>. Cada inscrição é comunicada ao <b>sistema de cobranças</b>, e todo acesso exige <b>login com usuário e senha</b>.
      </div>
    </td>
    <td>
      <div>
        <img src="https://joaopauloaramuni.github.io/image/logo_ES_vertical.png" alt="Logo do Projeto" width="120px"/>
      </div>
    </td>
  </tr>
</table>

---

## 🚧 Status do Projeto

![Versão](https://img.shields.io/badge/Versão-v1.0.0-blue?style=for-the-badge) ![Java](https://img.shields.io/badge/Java-25-007ec6?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.9-007ec6?style=for-the-badge&logo=springboot&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.9-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white) ![React](https://img.shields.io/badge/React-19-007ec6?style=for-the-badge&logo=react&logoColor=white) ![Vite](https://img.shields.io/badge/Vite-8-007ec6?style=for-the-badge&logo=vite&logoColor=white) ![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-007ec6?style=for-the-badge&logo=mongodb&logoColor=white)

> ✅ Protótipo concluído: modelos UML (v1 e v2 corrigida), back-end Spring Boot com autenticação, front-end React + Vite e persistência no MongoDB Atlas.

---

## 📚 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Histórias de Usuário](#-histórias-de-usuário)
- [Regras de Negócio](#-regras-de-negócio)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura](#-arquitetura)
  - [Diagramas UML](#diagramas-uml)
  - [Correções dos modelos (v1 → v2)](#correções-dos-modelos-v1--v2)
  - [Alinhamento entre modelo e código](#alinhamento-entre-modelo-e-código)
- [Instalação e Execução](#-instalação-e-execução)
  - [Pré-requisitos](#pré-requisitos)
  - [Variáveis de Ambiente](#-variáveis-de-ambiente)
  - [Banco de Dados (MongoDB Atlas)](#-banco-de-dados-mongodb-atlas)
  - [Instalação de Dependências](#-instalação-de-dependências)
  - [Como Executar a Aplicação](#-como-executar-a-aplicação)
  - [Usuários de Demonstração](#-usuários-de-demonstração)
- [Endpoints da API](#-endpoints-da-api)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Demonstração](#-demonstração)
- [Testes](#-testes)
- [Documentações utilizadas](#-documentações-utilizadas)
- [Autores](#-autores)
- [Licença](#-licença)

---

## 📝 Sobre o Projeto

Uma universidade pretende informatizar seu sistema de matrículas. Hoje a secretaria gera manualmente o currículo de cada semestre e mantém as informações sobre disciplinas, professores e alunos. Este projeto entrega um protótipo web que:

- **Por que existe:** automatizar o processo de matrícula e as regras de abertura/cancelamento de disciplinas.
- **Qual problema resolve:** elimina o controle manual de vagas (mínimo de 3 e máximo de 60 alunos por disciplina), dos limites de inscrição por aluno (4 obrigatórias + 2 optativas) e da comunicação com o setor de cobranças.
- **Contexto:** acadêmico — Laboratório 03 da disciplina DIAW (Engenharia de Software, 2º período).
- **Onde pode ser utilizado:** secretarias acadêmicas de instituições de ensino que trabalham com períodos de matrícula semestrais.

---

## ✨ Funcionalidades Principais

- 🔐 **Autenticação obrigatória:** login com usuário e senha para alunos, professores e secretaria.
- 🏫 **Gestão acadêmica (secretaria):** cadastro de cursos (nome e créditos), disciplinas, professores e alunos.
- 🗓️ **Currículo do semestre:** a secretaria gera o currículo, oferta as disciplinas e abre/encerra o período de matrículas.
- 📝 **Matrícula do aluno:** até 4 disciplinas obrigatórias e 2 optativas, com cancelamento durante o período.
- 🚦 **Regras de vagas:** inscrições encerradas ao atingir 60 alunos; disciplina ativa somente com 3 ou mais alunos ao final do período.
- 💳 **Integração com cobranças:** o sistema de cobranças é notificado a cada inscrição do aluno no semestre.
- 👩‍🏫 **Consulta do professor:** lista de alunos matriculados em cada disciplina que leciona.

---

## 📋 Histórias de Usuário

As histórias abaixo compõem o **Modelo de Análise** do sistema, junto com o Diagrama de Casos de Uso. Cada história segue o formato *"Como &lt;papel&gt;, quero &lt;objetivo&gt;, para &lt;benefício&gt;"* e traz critérios de aceitação.

### 🔐 Todos os usuários

**US01 – Realizar login**
> Como **usuário do sistema** (aluno, professor ou secretaria), quero **entrar com meu usuário e senha**, para **acessar apenas as funcionalidades do meu perfil**.
- [ ] O acesso a qualquer tela ou endpoint exige autenticação.
- [ ] A senha é validada e nunca é armazenada em texto puro.
- [ ] Usuário ou senha inválidos exibem mensagem de erro, sem revelar qual dos dois está errado.
- [ ] Após o login, o usuário é direcionado para a área do seu perfil.

**US02 – Sair do sistema**
> Como **usuário autenticado**, quero **encerrar minha sessão**, para **impedir que outra pessoa use minha conta**.
- [ ] Ao sair, as credenciais da sessão são descartadas e a tela de login é exibida.

### 🏫 Secretaria

**US03 – Manter cursos**
> Como **secretaria**, quero **cadastrar, editar, listar e remover cursos** com nome, número de créditos e disciplinas, para **manter a estrutura acadêmica atualizada**.
- [ ] Todo curso tem nome e número de créditos (maior que zero).
- [ ] Um curso é constituído por diversas disciplinas.

**US04 – Manter disciplinas**
> Como **secretaria**, quero **cadastrar, editar, listar e remover disciplinas**, para **definir o que pode ser ofertado nos semestres**.
- [ ] Cada disciplina tem código, nome, créditos e valor (usado na cobrança).

**US05 – Manter professores**
> Como **secretaria**, quero **cadastrar, editar, listar e remover professores** com login e senha, para **que eles acessem o sistema**.

**US06 – Manter alunos**
> Como **secretaria**, quero **cadastrar, editar, listar e remover alunos** com matrícula, curso, login e senha, para **que eles possam se matricular**.
- [ ] O login é único entre todos os usuários do sistema.

**US07 – Gerar currículo do semestre**
> Como **secretaria**, quero **gerar o currículo de um semestre**, ofertando disciplinas e indicando o professor de cada uma, para **disponibilizá-las para matrícula**.
- [ ] Um currículo pertence a um único semestre (ex.: `2027/1`).
- [ ] Cada oferta liga uma disciplina a um professor.

**US08 – Abrir e encerrar o período de matrículas**
> Como **secretaria**, quero **definir as datas e abrir/encerrar o período de matrículas**, para **controlar quando os alunos podem se matricular**.
- [ ] Fora do período, matrículas e cancelamentos são bloqueados.
- [ ] Ao encerrar o período, toda disciplina com **3 ou mais** alunos fica **ativa** (vai ocorrer no semestre seguinte).
- [ ] As disciplinas com **menos de 3** alunos são **canceladas**, e as matrículas nelas também.

**US09 – Consultar notificações de cobrança**
> Como **secretaria**, quero **visualizar as notificações enviadas ao sistema de cobranças**, para **conferir o que será cobrado de cada aluno**.

### 🧑‍🎓 Aluno

**US10 – Consultar disciplinas ofertadas**
> Como **aluno**, quero **ver as disciplinas ofertadas no semestre**, com professor, créditos e vagas, para **escolher onde me matricular**.

**US11 – Matricular-se em disciplinas**
> Como **aluno**, quero **me matricular em até 4 disciplinas obrigatórias (1ª opção) e 2 optativas (alternativas)**, para **montar meu semestre**.
- [ ] Só é possível se matricular durante o período de matrículas.
- [ ] Não é possível ultrapassar 4 obrigatórias nem 2 optativas.
- [ ] Não é possível se matricular duas vezes na mesma disciplina.
- [ ] Uma disciplina aceita no máximo **60** alunos; ao atingir esse número, as inscrições nela são **encerradas**.

**US12 – Cancelar matrícula**
> Como **aluno**, quero **cancelar uma matrícula feita anteriormente**, para **ajustar minhas escolhas**.
- [ ] Só é possível cancelar durante o período de matrículas.
- [ ] O cancelamento libera a vaga e reabre as inscrições se a disciplina estava lotada.

**US13 – Notificar o sistema de cobranças**
> Como **aluno**, quero **que o sistema de cobranças seja notificado das minhas inscrições**, para **ser cobrado corretamente pelas disciplinas do semestre**.
- [ ] Após cada inscrição (ou cancelamento) o sistema de cobranças recebe as disciplinas e o valor total do aluno no semestre.

### 👩‍🏫 Professor

**US14 – Consultar alunos matriculados**
> Como **professor**, quero **ver quais alunos estão matriculados em cada disciplina que leciono**, para **me planejar para o semestre**.
- [ ] O professor vê apenas as suas disciplinas.

---

## 📏 Regras de Negócio

| # | Regra | Onde é garantida no código |
| :---: | --- | --- |
| RN01 | Todo acesso exige login com usuário e senha; senhas armazenadas com BCrypt. | `SecurityConfig`, `AuthService`, `Usuario.validarSenha()` |
| RN02 | Cada perfil acessa apenas suas funcionalidades (aluno, professor, secretaria). | `@PreAuthorize` nos controllers + `ProtectedRoute` no front-end |
| RN03 | O aluno pode ter no máximo **4 matrículas obrigatórias** e **2 optativas** ativas por semestre. | `MatriculaService.matricular()` |
| RN04 | Matrículas e cancelamentos só são aceitos **durante o período de matrículas**. | `Curriculo.isPeriodoMatriculasAberto()` |
| RN05 | Uma disciplina aceita no máximo **60 alunos**; ao atingir o limite as inscrições são **encerradas** (status `LOTADA`). Um cancelamento reabre a vaga. | `Oferta.registrarInscricao()` / `Oferta.removerInscricao()` |
| RN06 | Ao **encerrar o período**, disciplinas com **3 ou mais** alunos ficam **ativas**; as demais são **canceladas**, junto com as matrículas nelas. | `Oferta.avaliarAtivacao()`, `CurriculoService.encerrarPeriodoMatriculas()` |
| RN07 | Após cada inscrição (e cancelamento) o **sistema de cobranças** é notificado com as disciplinas e o valor do semestre. | `SistemaCobrancas.notificar()` |
| RN08 | O professor só vê os alunos das disciplinas que leciona. | `ProfessorService.listarAlunosMatriculados()` |
| RN09 | O login é único entre todos os usuários; não há matrícula duplicada na mesma disciplina. | `UsuarioService.loginEmUso()`, `MatriculaService` |
| RN10 | Inscrições simultâneas não ultrapassam o limite de vagas (controle de concorrência otimista). | `@Version` em `Oferta` |

---

## 🛠 Tecnologias Utilizadas

### 💻 Front-end

* **Biblioteca:** React 19
* **Linguagem:** JavaScript (ES2022+)
* **Build Tool:** Vite 8
* **Roteamento:** React Router 7
* **Estado de autenticação:** Context API + `localStorage` (token JWT)
* **Estilização:** CSS puro com variáveis (design tokens)

### 🖥️ Back-end

* **Linguagem:** Java 25 (JDK 25)
* **Framework:** Spring Boot 4.0.x
* **Build e dependências:** Apache Maven
* **Banco de Dados:** MongoDB Atlas
* **Acesso a dados:** Spring Data MongoDB
* **Autenticação:** Spring Security 7 — login com usuário e senha (BCrypt) e token JWT (HS256) via OAuth2 Resource Server
* **Validação:** Jakarta Bean Validation
* **Testes:** JUnit 5 + Mockito

### 📐 Modelagem

* **Diagramas UML:** PlantUML (fontes `.puml` versionadas em `docs/diagramas`)

---

## 🏗 Arquitetura

O sistema é um **monólito em camadas** com front-end SPA separado:

- **Front-end (React + Vite):** SPA com rotas protegidas por perfil. O `AuthContext` guarda o usuário logado; o cliente `services/api.js` envia o token JWT no cabeçalho `Authorization` e trata erros (401 → volta para o login).
- **Back-end (Spring Boot 4):** organizado em `controller` → `service` → `model` / `repository`.
  - **controller:** endpoints REST e conversão para DTOs (`dto/`, records Java).
  - **service:** casos de uso (matrícula, currículo, cobranças, autenticação, cadastros).
  - **model:** classes de domínio do diagrama de classes, com as regras de negócio nos próprios objetos (`Oferta`, `Curriculo`, `Matricula`).
  - **repository:** interfaces Spring Data MongoDB.
  - **security:** Spring Security stateless com JWT assinado (HS256) e senhas em BCrypt; o perfil do usuário vira `ROLE_ALUNO`, `ROLE_PROFESSOR` ou `ROLE_SECRETARIA`.
  - **exception:** `GlobalExceptionHandler` devolve erros em JSON com mensagens em português (400, 401, 403, 404, 409, 422).
- **Banco de dados (MongoDB Atlas):** uma coleção por documento (`alunos`, `professores`, `secretarias`, `cursos`, `disciplinas`, `curriculos`, `ofertas`, `matriculas`, `notificacoes_cobranca`).
- **Sistema de cobranças:** representado pela interface `SistemaCobrancas`. A implementação `SistemaCobrancasService` registra cada notificação no MongoDB; trocar por uma integração externa exige apenas uma nova implementação da interface.

**Padrões adotados:** Service Layer, Repository, DTO, injeção de dependência por construtor, *Rich Domain Model* (regras nas entidades) e interface para integração externa.

### Diagramas UML

Todas as versões dos modelos estão versionadas em [`docs/diagramas`](docs/diagramas): a **v1** é a modelagem inicial (antes da implementação) e a **v2** é a versão corrigida, alinhada ao código final.

| Diagrama | v1 (modelagem inicial) | v2 (corrigido) |
| :---: | :---: | :---: |
| **Casos de Uso** | <a href="docs/diagramas/v1/casos-de-uso.png"><img src="docs/diagramas/v1/casos-de-uso.png" width="300"></a> | <a href="docs/diagramas/v2/casos-de-uso.png"><img src="docs/diagramas/v2/casos-de-uso.png" width="300"></a> |
| **Classes** | <a href="docs/diagramas/v1/classes.png"><img src="docs/diagramas/v1/classes.png" width="300"></a> | <a href="docs/diagramas/v2/classes.png"><img src="docs/diagramas/v2/classes.png" width="300"></a> |
| **Arquitetura** | <a href="docs/diagramas/v1/arquitetura.png"><img src="docs/diagramas/v1/arquitetura.png" width="300"></a> | <a href="docs/diagramas/v2/arquitetura.png"><img src="docs/diagramas/v2/arquitetura.png" width="300"></a> |
| **Sequência: Matrícula** | — | <a href="docs/diagramas/v2/sequencia-matricula.png"><img src="docs/diagramas/v2/sequencia-matricula.png" width="300"></a> |

### Correções dos modelos (v1 → v2)

| Modelo | Correção | Motivo |
| --- | --- | --- |
| Casos de uso | "Gerenciar período" foi dividido em **Abrir** e **Encerrar período de matrículas**; o encerramento inclui **Avaliar ativação das disciplinas**. | Deixar explícita a regra de mínimo de 3 alunos. |
| Casos de uso | **Cancelar matrícula** passou a incluir **Notificar sistema de cobranças**; adicionados **Encerrar inscrições (60 alunos)** como `<<extend>>`, **Validar senha**, **Sair do sistema** e **Consultar notificações de cobrança**. | O cancelamento altera o valor cobrado; requisitos de login e de lotação ficaram visíveis. |
| Classes | `StatusMatricula` ganhou `DISCIPLINA_CANCELADA`; `Matricula` ganhou `dataCancelamento` e `cancelarPorDisciplinaCancelada()`. | Diferenciar cancelamento feito pelo aluno do cancelamento automático no fim do período. |
| Classes | `Oferta` ganhou `versao` (`@Version`) e `getVagasDisponiveis()`. | Evitar ultrapassar 60 alunos em inscrições simultâneas e exibir vagas na interface. |
| Classes | `NotificacaoCobranca` ganhou `motivo` (`MotivoNotificacao`), `alunoNome` e `alunoMatricula`; `SistemaCobrancas.notificar()` recebe o motivo. | Rastrear por que cada cobrança foi recalculada (inscrição, cancelamento ou encerramento). |
| Classes | Inclusão da camada de serviços (`MatriculaService`, `CurriculoService`, `AuthService`, `SistemaCobrancasService`) e de `RegraNegocioException`. | Mostrar onde as regras são coordenadas e como as violações são sinalizadas. |
| Arquitetura | Detalhamento dos pacotes do back-end (`dto`, `exception`, `config`), do fluxo JWT e das coleções do MongoDB. | Refletir a implementação final. |

### Alinhamento entre modelo e código

| Classe do diagrama | Arquivo |
| --- | --- |
| `Usuario`, `Aluno`, `Professor`, `Secretaria` | [`backend/src/main/java/br/pucminas/matriculas/model/`](backend/src/main/java/br/pucminas/matriculas/model) |
| `Curso`, `Disciplina`, `Curriculo`, `Oferta`, `Matricula` | `model/Curso.java`, `model/Disciplina.java`, `model/Curriculo.java`, `model/Oferta.java`, `model/Matricula.java` |
| `NotificacaoCobranca`, `ItemCobranca`, enums | `model/NotificacaoCobranca.java`, `model/ItemCobranca.java`, `model/*.java` |
| `SistemaCobrancas`, `SistemaCobrancasService` | [`backend/src/main/java/br/pucminas/matriculas/service/`](backend/src/main/java/br/pucminas/matriculas/service) |
| `MatriculaService`, `CurriculoService`, `AuthService` | `service/MatriculaService.java`, `service/CurriculoService.java`, `service/AuthService.java` |

---

## 🔧 Instalação e Execução

### Pré-requisitos

* **Java JDK 25** (back-end Spring Boot)
* **Apache Maven 3.9+** (ou o Maven Wrapper `./mvnw` incluído no projeto)
* **Node.js 20.19+ ou 22.12+** e **npm** (front-end React + Vite)
* **Conta no MongoDB Atlas** com um cluster (o plano gratuito M0 é suficiente)

---

### 🔑 Variáveis de Ambiente

#### 1 Back-end (Spring Boot)

Copie [`backend/.env.example`](backend/.env.example) para **`backend/.env`** (arquivo ignorado pelo Git) e preencha. O Spring Boot importa esse arquivo automaticamente (`spring.config.import=optional:file:.env[.properties]`); também é possível definir as variáveis no sistema.

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `MONGODB_URI` | Connection string do MongoDB Atlas. | `mongodb+srv://usuario:senha@cluster0.xxxxx.mongodb.net/?retryWrites=true&w=majority` |
| `MONGODB_DATABASE` | Nome do banco de dados. | `matriculas` |
| `SERVER_PORT` | Porta da API. | `8080` |
| `JWT_SECRET` | Segredo para assinar os tokens (mínimo de 32 caracteres). | `um-segredo-longo-com-pelo-menos-32-caracteres` |
| `JWT_EXPIRATION_MINUTES` | Validade do token, em minutos. | `480` |
| `CORS_ALLOWED_ORIGINS` | Origens autorizadas (front-end). | `http://localhost:5173` |
| `APP_SEED_ENABLED` | Cria dados de demonstração quando o banco está vazio. | `true` |

#### 2 Front-end (React, Vite)

Copie [`frontend/.env.example`](frontend/.env.example) para **`frontend/.env.local`**:

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `VITE_API_URL` | URL base da API Spring Boot. | `http://localhost:8080/api` |

---

### 💾 Banco de Dados (MongoDB Atlas)

1. Crie um cluster em [cloud.mongodb.com](https://cloud.mongodb.com).
2. Em **Database Access**, crie um usuário com senha.
3. Em **Network Access**, libere o seu IP (ou `0.0.0.0/0` apenas para testes).
4. Em **Connect → Drivers**, copie a connection string e coloque em `MONGODB_URI` no `backend/.env`.

As coleções são criadas automaticamente pelo Spring Data MongoDB. Na primeira execução, com o banco vazio, o `DataInitializer` cria usuários, cursos, disciplinas e um currículo com matrículas abertas.

> [!TIP]
> Para desenvolvimento sem internet é possível usar um MongoDB local: `docker run -d --name matriculas-mongo -p 27017:27017 mongo:latest` (sem `MONGODB_URI`, a API usa `mongodb://localhost:27017/matriculas`).

---

### 📦 Instalação de Dependências

1. **Clone o repositório:**

```bash
git clone https://github.com/afplinio/diaw-laboratorio-3.git
cd diaw-laboratorio-3
```

2. **Front-end (React):**

```bash
cd frontend
npm install
cd ..
```

3. **Back-end (Spring Boot):**

```bash
cd backend
./mvnw clean install
cd ..
```

---

### ⚡ Como Executar a Aplicação

#### Terminal 1: Back-end (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```

🚀 *A API estará disponível em **http://localhost:8080/api**.*

#### Terminal 2: Front-end (React, Vite)

```bash
cd frontend
npm run dev
```

🎨 *O front-end estará disponível em **http://localhost:5173**.*

---

### 👥 Usuários de Demonstração

Criados automaticamente na primeira execução (banco vazio):

| Perfil | Usuário | Senha |
| --- | --- | --- |
| Secretaria | `secretaria` | `secretaria123` |
| Professor | `ana.souza` / `carlos.lima` | `professor123` |
| Aluno | `aluno1` … `aluno5` | `aluno123` |

> [!WARNING]
> São credenciais apenas para demonstração. Em produção, defina `APP_SEED_ENABLED=false` e um `JWT_SECRET` próprio.

---

## 🔌 Endpoints da API

Todas as rotas, exceto o login, exigem o cabeçalho `Authorization: Bearer <token>`.

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| `POST` | `/api/auth/login` | público | Valida usuário e senha e devolve o token JWT |
| `GET` | `/api/auth/me` | todos | Dados do usuário autenticado |
| `GET/POST/PUT/DELETE` | `/api/cursos` | leitura: todos · escrita: secretaria | Manter cursos |
| `GET/POST/PUT/DELETE` | `/api/disciplinas` | leitura: todos · escrita: secretaria | Manter disciplinas |
| `GET/POST/PUT/DELETE` | `/api/professores` | secretaria | Manter professores |
| `GET/POST/PUT/DELETE` | `/api/alunos` | secretaria | Manter alunos |
| `GET/POST` | `/api/curriculos` | leitura: todos · escrita: secretaria | Listar / gerar currículo semestral |
| `PUT` | `/api/curriculos/{id}` | secretaria | Alterar datas do período de matrículas |
| `GET/POST/DELETE` | `/api/curriculos/{id}/ofertas` | leitura: todos · escrita: secretaria | Ofertar / retirar disciplinas |
| `POST` | `/api/curriculos/{id}/abrir` · `/encerrar` | secretaria | Abrir / encerrar o período de matrículas |
| `GET` | `/api/cobrancas` | secretaria | Notificações enviadas ao sistema de cobranças |
| `GET` | `/api/aluno/painel` | aluno | Semestre aberto, limites e disciplinas ofertadas |
| `GET/POST` | `/api/aluno/matriculas` | aluno | Minhas matrículas / matricular-se |
| `DELETE` | `/api/aluno/matriculas/{id}` | aluno | Cancelar matrícula |
| `GET` | `/api/professor/ofertas` | professor | Disciplinas que leciono |
| `GET` | `/api/professor/ofertas/{id}/alunos` | professor | Alunos matriculados na disciplina |

**Exemplo:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
     -H 'Content-Type: application/json' \
     -d '{"login":"aluno1","senha":"aluno123"}'
```

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "expiraEmMinutos": 480,
  "usuario": { "id": "6af8...", "nome": "Bruna Alves", "login": "aluno1", "perfil": "ALUNO" }
}
```

Exemplo de regra de negócio violada (5ª disciplina obrigatória):

```json
{ "status": 422, "mensagem": "Limite de 4 disciplinas obrigatórias atingido", "erros": [], "timestamp": "..." }
```

---

## 📂 Estrutura de Pastas

```
.
├── README.md                         # 📘 Documentação principal (este arquivo)
├── docs/
│   ├── diagramas/
│   │   ├── v1/                       # 📐 Modelos iniciais (.puml + .png)
│   │   └── v2/                       # 📐 Modelos corrigidos (.puml + .png)
│   └── imagens/                      # 🖼️ Capturas de tela da aplicação
├── backend/                          # ☕ API Spring Boot 4 (Java 25, Maven)
│   ├── .env.example                  # 🧩 Variáveis de ambiente do back-end
│   ├── pom.xml                       # 🛠️ Build e dependências
│   └── src/
│       ├── main/java/br/pucminas/matriculas/
│       │   ├── config/               # 🌱 Dados iniciais (DataInitializer)
│       │   ├── controller/           # 🎮 Endpoints REST
│       │   ├── dto/                  # ✉️ Requests e responses (records)
│       │   ├── exception/            # 💥 Exceções de negócio e handler global
│       │   ├── model/                # 🧬 Classes de domínio (diagrama de classes)
│       │   ├── repository/           # 🗄️ Repositórios Spring Data MongoDB
│       │   ├── security/             # 🛡️ Spring Security + JWT
│       │   └── service/              # ⚙️ Regras e casos de uso
│       ├── main/resources/application.properties
│       └── test/java/...             # 🧪 Testes unitários (JUnit 5 + Mockito)
└── frontend/                         # ⚛️ SPA React + Vite
    ├── .env.example                  # 🧩 Variáveis de ambiente do front-end
    ├── package.json
    └── src/
        ├── components/               # 🧱 Layout, rotas protegidas, selos, mensagens
        ├── contexts/                 # 🔐 Contexto de autenticação
        ├── hooks/                    # 🎣 useAuth, useCarregar
        ├── pages/
        │   ├── aluno/                # 🧑‍🎓 Matrícula e cancelamento
        │   ├── professor/            # 👩‍🏫 Alunos por disciplina
        │   └── secretaria/           # 🏫 Cursos, disciplinas, usuários, currículos, cobranças
        ├── services/api.js           # 🔌 Cliente HTTP da API
        └── utils/                    # 🛠️ Formatação (moeda, datas, rótulos)
```

---

## 🎥 Demonstração

### 🌐 Aplicação Web

| Tela | Captura de Tela |
| :---: | :---: |
| **Login** | **Aluno: matrícula (4 obrigatórias + optativas)** |
| <img src="docs/imagens/01-login.png" alt="Tela de login" width="400"> | <img src="docs/imagens/02-aluno-matriculas.png" alt="Tela de matrícula do aluno" width="400"> |
| **Professor: alunos da disciplina** | **Secretaria: currículo e ofertas** |
| <img src="docs/imagens/03-professor-alunos.png" alt="Alunos matriculados por disciplina" width="400"> | <img src="docs/imagens/04-secretaria-curriculo.png" alt="Currículo semestral" width="400"> |
| **Secretaria: disciplinas** | **Secretaria: alunos** |
| <img src="docs/imagens/05-secretaria-disciplinas.png" alt="Cadastro de disciplinas" width="400"> | <img src="docs/imagens/06-secretaria-alunos.png" alt="Cadastro de alunos" width="400"> |
| **Sistema de cobranças** | **Encerramento do período (mín. 3 alunos)** |
| <img src="docs/imagens/07-secretaria-cobrancas.png" alt="Notificações de cobrança" width="400"> | <img src="docs/imagens/08-secretaria-encerramento.png" alt="Encerramento do período" width="400"> |
| **Secretaria: cursos** | |
| <img src="docs/imagens/09-secretaria-cursos.png" alt="Cadastro de cursos" width="400"> | |

---

## 🧪 Testes

### Testes unitários (back-end)

```bash
cd backend
./mvnw test
```

*Ferramentas: JUnit 5 e Mockito.* Os testes cobrem as regras de domínio (`OfertaTest`, `CurriculoTest`, `MatriculaTest`) e o serviço de matrícula (`MatriculaServiceTest`): limite de 60 alunos, mínimo de 3 para ativar, 4 obrigatórias e 2 optativas, período de matrículas, matrícula duplicada, cancelamento e notificação de cobrança.

### Verificação do front-end

```bash
cd frontend
npm run lint
npm run build
```

---

## 🔗 Documentações utilizadas

* 📖 **Front-end:** [React](https://react.dev/reference/react) · [Vite](https://vite.dev/config/) · [React Router](https://reactrouter.com/)
* 📖 **Back-end:** [Spring Boot](https://docs.spring.io/spring-boot/index.html) · [Spring Data MongoDB](https://docs.spring.io/spring-data/mongodb/reference/) · [Spring Security – OAuth2 Resource Server (JWT)](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
* 📖 **Banco de dados:** [MongoDB Atlas](https://www.mongodb.com/docs/atlas/)
* 📖 **Modelagem:** [PlantUML](https://plantuml.com/)
* 📖 **Template do README:** [Prof. João Paulo Aramuni](https://github.com/joaopauloaramuni/desenvolvimento-e-integracao-de-aplicacoes-web/blob/main/TEMPLATES/template_README.md)

---

## 👥 Autores

| 👤 Nome | 🖼️ Foto | :octocat: GitHub |
|---|---|---|
| Plínio Ferreira | <div align="center"><img src="https://github.com/afplinio.png" width="70px" height="70px"></div> | <div align="center"><a href="https://github.com/afplinio">afplinio</a></div> |

---

## 📄 Licença

Projeto acadêmico desenvolvido para fins educacionais na disciplina DIAW.
