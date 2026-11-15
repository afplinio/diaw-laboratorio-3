package br.pucminas.matriculas.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.pucminas.matriculas.exception.RegraNegocioException;

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
    private LocalDateTime dataCancelamento;

    public Matricula() {
    }

    public Matricula(String alunoId, String ofertaId, String curriculoId, TipoMatricula tipo) {
        this.alunoId = alunoId;
        this.ofertaId = ofertaId;
        this.curriculoId = curriculoId;
        this.tipo = tipo;
        this.dataMatricula = LocalDateTime.now();
    }

    /** Cancelamento solicitado pelo aluno durante o período de matrículas. */
    public void cancelar() {
        if (!isAtiva()) {
            throw new RegraNegocioException("Esta matrícula não está ativa");
        }
        status = StatusMatricula.CANCELADA;
        dataCancelamento = LocalDateTime.now();
    }

    /** Cancelamento automático porque a disciplina não atingiu o mínimo de alunos. */
    public void cancelarPorDisciplinaCancelada() {
        if (isAtiva()) {
            status = StatusMatricula.DISCIPLINA_CANCELADA;
            dataCancelamento = LocalDateTime.now();
        }
    }

    public boolean isAtiva() {
        return status == StatusMatricula.ATIVA;
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

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }
}
