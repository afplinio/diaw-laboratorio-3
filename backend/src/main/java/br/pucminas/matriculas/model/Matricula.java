package br.pucminas.matriculas.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Inscrição de um aluno em uma disciplina ofertada no semestre. */
@Document(collection = "matriculas")
public class Matricula {

    public static final int MAXIMO_OBRIGATORIAS = 4;
    public static final int MAXIMO_OPTATIVAS = 2;

    @Id
    private String id;
    private String alunoId;
    private String ofertaId;
    private String curriculoId;
    private TipoMatricula tipo;
    private StatusMatricula status = StatusMatricula.ATIVA;
    private LocalDateTime dataMatricula;

    public Matricula() {
    }

    public Matricula(String alunoId, String ofertaId, String curriculoId, TipoMatricula tipo) {
        this.alunoId = alunoId;
        this.ofertaId = ofertaId;
        this.curriculoId = curriculoId;
        this.tipo = tipo;
        this.dataMatricula = LocalDateTime.now();
    }

    public void cancelar() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public boolean isAtiva() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public String getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(String ofertaId) {
        this.ofertaId = ofertaId;
    }

    public String getCurriculoId() {
        return curriculoId;
    }

    public void setCurriculoId(String curriculoId) {
        this.curriculoId = curriculoId;
    }

    public TipoMatricula getTipo() {
        return tipo;
    }

    public void setTipo(TipoMatricula tipo) {
        this.tipo = tipo;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public LocalDateTime getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDateTime dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
}
