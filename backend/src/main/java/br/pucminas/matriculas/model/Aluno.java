package br.pucminas.matriculas.model;

import org.springframework.data.mongodb.core.mapping.Document;

/** Aluno de um curso. Pode se matricular em 4 disciplinas obrigatórias e 2 optativas por semestre. */
@Document(collection = "alunos")
public class Aluno extends Usuario {

    /** Número de matrícula do aluno na universidade. */
    private String matricula;
    private String cursoId;

    public Aluno() {
    }

    public Aluno(String nome, String login, String senha, String matricula, String cursoId) {
        super(nome, login, senha);
        this.matricula = matricula;
        this.cursoId = cursoId;
    }

    @Override
    public Perfil getPerfil() {
        return Perfil.ALUNO;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }
}
