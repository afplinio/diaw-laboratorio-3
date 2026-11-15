package br.pucminas.matriculas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.pucminas.matriculas.exception.RegraNegocioException;

class CurriculoTest {

    private static final LocalDate INICIO = LocalDate.of(2027, 1, 10);
    private static final LocalDate FIM = LocalDate.of(2027, 1, 20);

    @Test
    void periodoSoFicaAbertoDepoisDeAbertoPelaSecretaria() {
        Curriculo curriculo = new Curriculo("2027/1", INICIO, FIM);
        assertFalse(curriculo.isPeriodoMatriculasAberto(INICIO));
        curriculo.abrirPeriodoMatriculas();
        assertEquals(StatusCurriculo.MATRICULAS_ABERTAS, curriculo.getStatus());
        assertTrue(curriculo.isPeriodoMatriculasAberto(INICIO));
        assertTrue(curriculo.isPeriodoMatriculasAberto(FIM));
    }

    @Test
    void foraDasDatasOPeriodoEstaFechado() {
        Curriculo curriculo = new Curriculo("2027/1", INICIO, FIM);
        curriculo.abrirPeriodoMatriculas();
        assertFalse(curriculo.isPeriodoMatriculasAberto(INICIO.minusDays(1)));
        assertFalse(curriculo.isPeriodoMatriculasAberto(FIM.plusDays(1)));
    }

    @Test
    void naoReabrePeriodoEncerrado() {
        Curriculo curriculo = new Curriculo("2027/1", INICIO, FIM);
        curriculo.abrirPeriodoMatriculas();
        curriculo.encerrarPeriodoMatriculas();
        assertFalse(curriculo.isPeriodoMatriculasAberto(INICIO));
        assertThrows(RegraNegocioException.class, curriculo::abrirPeriodoMatriculas);
    }

    @Test
    void naoEncerraPeriodoQueNaoFoiAberto() {
        Curriculo curriculo = new Curriculo("2027/1", INICIO, FIM);
        assertThrows(RegraNegocioException.class, curriculo::encerrarPeriodoMatriculas);
    }

    @Test
    void exigeDatasValidasParaAbrir() {
        Curriculo curriculo = new Curriculo("2027/1", FIM, INICIO);
        assertThrows(RegraNegocioException.class, curriculo::abrirPeriodoMatriculas);
    }
}
