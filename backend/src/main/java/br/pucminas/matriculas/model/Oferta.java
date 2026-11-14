package br.pucminas.matriculas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Disciplina ofertada em um currículo semestral, lecionada por um professor.
 * Aceita no máximo 60 alunos e só fica ativa se tiver pelo menos 3 ao final do período.
 */
@Document(collection = "ofertas")
public class Oferta {

    public static final int MINIMO_ALUNOS = 3;
    public static final int MAXIMO_ALUNOS = 60;

    @Id
    private String id;
    private String curriculoId;
    private String disciplinaId;
    private String professorId;
    private int inscritos;
    private StatusOferta status = StatusOferta.ABERTA;

    public Oferta() {
    }

    public Oferta(String curriculoId, String disciplinaId, String professorId) {
        this.curriculoId = curriculoId;
        this.disciplinaId = disciplinaId;
        this.professorId = professorId;
    }

    public boolean isInscricoesAbertas() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void registrarInscricao() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void removerInscricao() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void avaliarAtivacao() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurriculoId() {
        return curriculoId;
    }

    public void setCurriculoId(String curriculoId) {
        this.curriculoId = curriculoId;
    }

    public String getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(String disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public StatusOferta getStatus() {
        return status;
    }

    public void setStatus(StatusOferta status) {
        this.status = status;
    }
}
