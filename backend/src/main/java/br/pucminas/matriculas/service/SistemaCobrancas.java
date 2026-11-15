package br.pucminas.matriculas.service;

import java.util.List;

import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.MotivoNotificacao;
import br.pucminas.matriculas.model.NotificacaoCobranca;

/**
 * Ponto de integração com o sistema de cobranças da universidade.
 * O sistema de matrículas notifica as disciplinas do aluno no semestre para que ele seja cobrado.
 */
public interface SistemaCobrancas {

    /**
     * Notifica as disciplinas atualmente ativas do aluno no semestre.
     *
     * @param matriculas matrículas ativas do aluno no currículo (pode ser vazia após cancelamentos)
     */
    NotificacaoCobranca notificar(Aluno aluno, Curriculo curriculo, List<Matricula> matriculas,
            MotivoNotificacao motivo);
}
