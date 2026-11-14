package br.pucminas.matriculas.model;

import org.springframework.data.mongodb.core.mapping.Document;

/** Professor. Consulta os alunos matriculados nas disciplinas que leciona. */
@Document(collection = "professores")
public class Professor extends Usuario {

    private String departamento;

    public Professor() {
    }

    public Professor(String nome, String login, String senha, String departamento) {
        super(nome, login, senha);
        this.departamento = departamento;
    }

    @Override
    public Perfil getPerfil() {
        return Perfil.PROFESSOR;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
