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

![Versão](https://img.shields.io/badge/Versão-v0.1.0-blue?style=for-the-badge) ![Java](https://img.shields.io/badge/Java-25-007ec6?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.9-007ec6?style=for-the-badge&logo=springboot&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.9-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white) ![React](https://img.shields.io/badge/React-19-007ec6?style=for-the-badge&logo=react&logoColor=white) ![Vite](https://img.shields.io/badge/Vite-8-007ec6?style=for-the-badge&logo=vite&logoColor=white) ![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-007ec6?style=for-the-badge&logo=mongodb&logoColor=white)

> 🛠️ Em desenvolvimento: modelagem de análise.

---

## 📚 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
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

## 🛠 Tecnologias Utilizadas

### 💻 Front-end

* **Biblioteca:** React 19
* **Linguagem:** JavaScript (ES2022+)
* **Build Tool:** Vite

### 🖥️ Back-end

* **Linguagem:** Java 25 (JDK 25)
* **Framework:** Spring Boot 4.0.x
* **Build e dependências:** Apache Maven
* **Banco de Dados:** MongoDB Atlas
* **Acesso a dados:** Spring Data MongoDB
* **Autenticação:** Spring Security (usuário e senha)

---

## 👥 Autores

| 👤 Nome | 🖼️ Foto | :octocat: GitHub |
|---|---|---|
| Plínio Ferreira | <div align="center"><img src="https://github.com/afplinio.png" width="70px" height="70px"></div> | <div align="center"><a href="https://github.com/afplinio">afplinio</a></div> |

---

## 📄 Licença

Projeto acadêmico desenvolvido para fins educacionais na disciplina DIAW.
