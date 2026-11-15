package br.pucminas.matriculas.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.exception.RecursoNaoEncontradoException;
import br.pucminas.matriculas.exception.RegraNegocioException;
import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.StatusMatricula;
import br.pucminas.matriculas.model.TipoMatricula;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.CurriculoRepository;
import br.pucminas.matriculas.repository.MatriculaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;

/** Matrícula e cancelamento de matrícula pelos alunos durante o período de matrículas. */
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final OfertaRepository ofertaRepository;
    private final CurriculoRepository curriculoRepository;
    private final AlunoRepository alunoRepository;
    private final SistemaCobrancas sistemaCobrancas;

    public MatriculaService(MatriculaRepository matriculaRepository, OfertaRepository ofertaRepository,
            CurriculoRepository curriculoRepository, AlunoRepository alunoRepository,
            SistemaCobrancas sistemaCobrancas) {
        this.matriculaRepository = matriculaRepository;
        this.ofertaRepository = ofertaRepository;
        this.curriculoRepository = curriculoRepository;
        this.alunoRepository = alunoRepository;
        this.sistemaCobrancas = sistemaCobrancas;
    }

    public Matricula matricular(String alunoId, String ofertaId, TipoMatricula tipo) {
        Aluno aluno = buscarAluno(alunoId);
        Oferta oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Oferta", ofertaId));
        Curriculo curriculo = buscarCurriculo(oferta.getCurriculoId());
        exigirPeriodoAberto(curriculo);

        if (matriculaRepository.existsByAlunoIdAndOfertaIdAndStatus(alunoId, ofertaId, StatusMatricula.ATIVA)) {
            throw new RegraNegocioException("Você já está matriculado nesta disciplina");
        }
        long jaMatriculadas = matriculaRepository.countByAlunoIdAndCurriculoIdAndTipoAndStatus(alunoId,
                curriculo.getId(), tipo, StatusMatricula.ATIVA);
        int limite = tipo == TipoMatricula.OBRIGATORIA ? Matricula.MAXIMO_OBRIGATORIAS : Matricula.MAXIMO_OPTATIVAS;
        if (jaMatriculadas >= limite) {
            throw new RegraNegocioException(tipo == TipoMatricula.OBRIGATORIA
                    ? "Limite de " + limite + " disciplinas obrigatórias atingido"
                    : "Limite de " + limite + " disciplinas optativas atingido");
        }

        oferta.registrarInscricao();
        ofertaRepository.save(oferta);
        return matriculaRepository.save(new Matricula(aluno.getId(), ofertaId, curriculo.getId(), tipo));
    }

    public Matricula cancelar(String alunoId, String matriculaId) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .filter(m -> m.getAlunoId().equals(alunoId))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula", matriculaId));
        Curriculo curriculo = buscarCurriculo(matricula.getCurriculoId());
        exigirPeriodoAberto(curriculo);

        matricula.cancelar();
        ofertaRepository.findById(matricula.getOfertaId()).ifPresent(oferta -> {
            oferta.removerInscricao();
            ofertaRepository.save(oferta);
        });
        return matriculaRepository.save(matricula);
    }

    public List<Matricula> listarDoAluno(String alunoId) {
        return matriculaRepository.findByAlunoId(alunoId).stream()
                .sorted(Comparator.comparing(Matricula::getDataMatricula).reversed())
                .toList();
    }

    private Aluno buscarAluno(String alunoId) {
        return alunoRepository.findById(alunoId).orElseThrow(() -> new RecursoNaoEncontradoException("Aluno", alunoId));
    }

    private Curriculo buscarCurriculo(String curriculoId) {
        return curriculoRepository.findById(curriculoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Currículo", curriculoId));
    }

    private static void exigirPeriodoAberto(Curriculo curriculo) {
        if (!curriculo.isPeriodoMatriculasAberto(LocalDate.now())) {
            throw new RegraNegocioException("Fora do período de matrículas do semestre " + curriculo.getSemestre());
        }
    }
}
