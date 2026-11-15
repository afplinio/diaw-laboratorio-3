package br.pucminas.matriculas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.pucminas.matriculas.exception.RegraNegocioException;

class MatriculaTest {

    @Test
    void cancelarMatriculaAtiva() {
        Matricula matricula = new Matricula("aluno", "oferta", "curriculo", TipoMatricula.OBRIGATORIA);
        assertTrue(matricula.isAtiva());
        matricula.cancelar();
        assertEquals(StatusMatricula.CANCELADA, matricula.getStatus());
        assertNotNull(matricula.getDataCancelamento());
        assertThrows(RegraNegocioException.class, matricula::cancelar);
    }

    @Test
    void cancelamentoPorDisciplinaCancelada() {
        Matricula matricula = new Matricula("aluno", "oferta", "curriculo", TipoMatricula.OPTATIVA);
        matricula.cancelarPorDisciplinaCancelada();
        assertFalse(matricula.isAtiva());
        assertEquals(StatusMatricula.DISCIPLINA_CANCELADA, matricula.getStatus());
    }

    @Test
    void validaSenhaDoUsuarioComHash() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Aluno aluno = new Aluno("Ana", "ana", encoder.encode("segredo"), "2026001", "curso");
        assertTrue(aluno.validarSenha("segredo", encoder));
        assertFalse(aluno.validarSenha("errada", encoder));
        assertEquals(Perfil.ALUNO, aluno.getPerfil());
    }

    @Test
    void cursoNaoRepeteDisciplinas() {
        Curso curso = new Curso("Engenharia de Software", 3200);
        curso.adicionarDisciplina("d1");
        curso.adicionarDisciplina("d1");
        curso.adicionarDisciplina("d2");
        curso.removerDisciplina("d1");
        assertEquals(1, curso.getDisciplinaIds().size());
    }
}
