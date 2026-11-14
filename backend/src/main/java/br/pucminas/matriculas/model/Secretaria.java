package br.pucminas.matriculas.model;

import org.springframework.data.mongodb.core.mapping.Document;

/** Usuário da secretaria. Gera o currículo e mantém cursos, disciplinas, professores e alunos. */
@Document(collection = "secretarias")
public class Secretaria extends Usuario {

    private String setor;

    public Secretaria() {
    }

    public Secretaria(String nome, String login, String senha, String setor) {
        super(nome, login, senha);
        this.setor = setor;
    }

    @Override
    public Perfil getPerfil() {
        return Perfil.SECRETARIA;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
