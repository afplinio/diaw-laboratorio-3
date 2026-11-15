package br.pucminas.matriculas.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Disciplina;
import br.pucminas.matriculas.model.ItemCobranca;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.MotivoNotificacao;
import br.pucminas.matriculas.model.NotificacaoCobranca;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.repository.DisciplinaRepository;
import br.pucminas.matriculas.repository.NotificacaoCobrancaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;

/**
 * Implementação do sistema de cobranças: registra no MongoDB as notificações enviadas
 * para que o aluno seja cobrado pelas disciplinas do semestre.
 */
@Service
public class SistemaCobrancasService implements SistemaCobrancas {

    private static final Logger log = LoggerFactory.getLogger(SistemaCobrancasService.class);

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
    public NotificacaoCobranca notificar(Aluno aluno, Curriculo curriculo, List<Matricula> matriculas,
            MotivoNotificacao motivo) {
        List<Oferta> ofertas = ofertaRepository.findAllById(matriculas.stream().map(Matricula::getOfertaId).toList());
        Map<String, Disciplina> disciplinas = disciplinaRepository
                .findAllById(ofertas.stream().map(Oferta::getDisciplinaId).toList()).stream()
                .collect(Collectors.toMap(Disciplina::getId, Function.identity()));
        List<ItemCobranca> itens = ofertas.stream()
                .map(oferta -> disciplinas.get(oferta.getDisciplinaId()))
                .filter(d -> d != null)
                .map(d -> new ItemCobranca(d.getCodigo(), d.getNome(), d.getValor()))
                .toList();

        NotificacaoCobranca notificacao = notificacaoRepository
                .save(new NotificacaoCobranca(aluno, curriculo.getSemestre(), motivo, itens));
        log.info("Sistema de cobranças notificado: aluno {} | semestre {} | {} disciplina(s) | total R$ {}",
                aluno.getMatricula(), curriculo.getSemestre(), itens.size(), notificacao.getValorTotal());
        return notificacao;
    }

    public List<NotificacaoCobranca> listarNotificacoes() {
        return notificacaoRepository.findAllByOrderByDataNotificacaoDesc();
    }
}
