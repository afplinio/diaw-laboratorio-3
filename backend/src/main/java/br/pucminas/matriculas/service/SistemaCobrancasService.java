package br.pucminas.matriculas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.NotificacaoCobranca;
import br.pucminas.matriculas.repository.DisciplinaRepository;
import br.pucminas.matriculas.repository.NotificacaoCobrancaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;

/**
 * Implementação do sistema de cobranças: registra no MongoDB as notificações enviadas
 * para que o aluno seja cobrado pelas disciplinas do semestre.
 */
@Service
public class SistemaCobrancasService implements SistemaCobrancas {

    private final NotificacaoCobrancaRepository notificacaoRepository;
    private final OfertaRepository ofertaRepository;
    private final DisciplinaRepository disciplinaRepository;

    public SistemaCobrancasService(NotificacaoCobrancaRepository notificacaoRepository,
            OfertaRepository ofertaRepository, DisciplinaRepository disciplinaRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.ofertaRepository = ofertaRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public NotificacaoCobranca notificar(Aluno aluno, Curriculo curriculo, List<Matricula> matriculas) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public List<NotificacaoCobranca> listarNotificacoes() {
        throw new UnsupportedOperationException("Não implementado");
    }
}
