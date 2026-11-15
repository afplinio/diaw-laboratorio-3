package br.pucminas.matriculas.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.pucminas.matriculas.exception.RegraNegocioException;

/**
 * Currículo de um semestre, gerado pela secretaria. Define o período de matrículas
 * durante o qual os alunos podem se matricular ou cancelar matrículas.
 */
@Document(collection = "curriculos")
public class Curriculo {

    @Id
    private String id;
    /** Semestre letivo no formato AAAA/S, por exemplo 2027/1. */
    private String semestre;
    private LocalDate inicioMatriculas;
    private LocalDate fimMatriculas;
    private StatusCurriculo status = StatusCurriculo.PLANEJADO;

    public Curriculo() {
    }

    public Curriculo(String semestre, LocalDate inicioMatriculas, LocalDate fimMatriculas) {
        this.semestre = semestre;
        this.inicioMatriculas = inicioMatriculas;
        this.fimMatriculas = fimMatriculas;
    }

    public void abrirPeriodoMatriculas() {
        if (status == StatusCurriculo.MATRICULAS_ENCERRADAS) {
            throw new RegraNegocioException("O período de matrículas do semestre " + semestre + " já foi encerrado");
        }
        if (inicioMatriculas == null || fimMatriculas == null || fimMatriculas.isBefore(inicioMatriculas)) {
            throw new RegraNegocioException("Defina datas válidas para o período de matrículas");
        }
        status = StatusCurriculo.MATRICULAS_ABERTAS;
    }

    public void encerrarPeriodoMatriculas() {
        if (status != StatusCurriculo.MATRICULAS_ABERTAS) {
            throw new RegraNegocioException("O período de matrículas não está aberto");
        }
        status = StatusCurriculo.MATRICULAS_ENCERRADAS;
    }

    public boolean isPeriodoMatriculasAberto(LocalDate hoje) {
        return status == StatusCurriculo.MATRICULAS_ABERTAS
                && !hoje.isBefore(inicioMatriculas)
                && !hoje.isAfter(fimMatriculas);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public LocalDate getInicioMatriculas() {
        return inicioMatriculas;
    }

    public void setInicioMatriculas(LocalDate inicioMatriculas) {
        this.inicioMatriculas = inicioMatriculas;
    }

    public LocalDate getFimMatriculas() {
        return fimMatriculas;
    }

    public void setFimMatriculas(LocalDate fimMatriculas) {
        this.fimMatriculas = fimMatriculas;
    }

    public StatusCurriculo getStatus() {
        return status;
    }

    public void setStatus(StatusCurriculo status) {
        this.status = status;
    }
}
