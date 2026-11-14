package br.pucminas.matriculas.service;

import java.util.List;

import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.NotificacaoCobranca;

/**
 * Ponto de integração com o sistema de cobranças da universidade.
 * O sistema de matrículas notifica as disciplinas do aluno no semestre para que ele seja cobrado.
 */
public interface SistemaCobrancas {

    NotificacaoCobranca notificar(Aluno aluno, Curriculo curriculo, List<Matricula> matriculas);
}
