package br.pucminas.matriculas.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.exception.RecursoNaoEncontradoException;
import br.pucminas.matriculas.exception.RegraNegocioException;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.StatusCurriculo;
import br.pucminas.matriculas.model.StatusMatricula;
import br.pucminas.matriculas.model.StatusOferta;
import br.pucminas.matriculas.repository.CurriculoRepository;
import br.pucminas.matriculas.repository.DisciplinaRepository;
import br.pucminas.matriculas.repository.MatriculaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;
import br.pucminas.matriculas.repository.ProfessorRepository;

/** Geração do currículo semestral e controle do período de matrículas. */
@Service
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    private final OfertaRepository ofertaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final MatriculaRepository matriculaRepository;

    public CurriculoService(CurriculoRepository curriculoRepository, OfertaRepository ofertaRepository,
            DisciplinaRepository disciplinaRepository, ProfessorRepository professorRepository,
            MatriculaRepository matriculaRepository) {
        this.curriculoRepository = curriculoRepository;
        this.ofertaRepository = ofertaRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public List<Curriculo> listar() {
        return curriculoRepository.findAll().stream()
                .sorted(Comparator.comparing(Curriculo::getSemestre).reversed())
                .toList();
    }

    public Curriculo buscar(String id) {
        return curriculoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Currículo", id));
    }

    public Curriculo criar(Curriculo curriculo) {
        if (curriculoRepository.existsBySemestre(curriculo.getSemestre())) {
            throw new RegraNegocioException("Já existe um currículo para o semestre " + curriculo.getSemestre());
        }
        validarDatas(curriculo.getInicioMatriculas(), curriculo.getFimMatriculas());
        curriculo.setId(null);
        curriculo.setStatus(StatusCurriculo.PLANEJADO);
        return curriculoRepository.save(curriculo);
    }

    /** Altera as datas do período de matrículas (não permitido após o encerramento). */
    public Curriculo atualizarPeriodo(String id, LocalDate inicio, LocalDate fim) {
        Curriculo curriculo = buscar(id);
        exigirNaoEncerrado(curriculo);
        validarDatas(inicio, fim);
        curriculo.setInicioMatriculas(inicio);
        curriculo.setFimMatriculas(fim);
        return curriculoRepository.save(curriculo);
    }

    /** Currículo cujo período de matrículas está aberto, se houver. */
    public Optional<Curriculo> buscarComMatriculasAbertas() {
        return curriculoRepository.findByStatus(StatusCurriculo.MATRICULAS_ABERTAS).stream().findFirst();
    }

    public List<Oferta> listarOfertas(String curriculoId) {
        buscar(curriculoId);
        return ofertaRepository.findByCurriculoId(curriculoId);
    }

    public Oferta adicionarOferta(String curriculoId, String disciplinaId, String professorId) {
        Curriculo curriculo = buscar(curriculoId);
        exigirNaoEncerrado(curriculo);
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new RecursoNaoEncontradoException("Disciplina", disciplinaId);
        }
        if (!professorRepository.existsById(professorId)) {
            throw new RecursoNaoEncontradoException("Professor", professorId);
        }
        if (ofertaRepository.existsByCurriculoIdAndDisciplinaId(curriculoId, disciplinaId)) {
            throw new RegraNegocioException("Esta disciplina já foi ofertada neste currículo");
        }
        return ofertaRepository.save(new Oferta(curriculoId, disciplinaId, professorId));
    }

    public void removerOferta(String curriculoId, String ofertaId) {
        Curriculo curriculo = buscar(curriculoId);
        exigirNaoEncerrado(curriculo);
        Oferta oferta = ofertaRepository.findById(ofertaId)
                .filter(o -> o.getCurriculoId().equals(curriculoId))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Oferta", ofertaId));
        if (oferta.getInscritos() > 0) {
            throw new RegraNegocioException("Não é possível remover uma disciplina que já tem alunos matriculados");
        }
        ofertaRepository.delete(oferta);
    }

    public Curriculo abrirPeriodoMatriculas(String curriculoId) {
        Curriculo curriculo = buscar(curriculoId);
        buscarComMatriculasAbertas()
                .filter(aberto -> !aberto.getId().equals(curriculoId))
                .ifPresent(aberto -> {
                    throw new RegraNegocioException(
                            "O período de matrículas do semestre " + aberto.getSemestre() + " ainda está aberto");
                });
        if (ofertaRepository.findByCurriculoId(curriculoId).isEmpty()) {
            throw new RegraNegocioException("Oferte pelo menos uma disciplina antes de abrir as matrículas");
        }
        curriculo.abrirPeriodoMatriculas();
        return curriculoRepository.save(curriculo);
    }

    /**
     * Encerra o período: disciplinas com pelo menos 3 alunos ficam ativas e as demais são canceladas.
     */
    public Curriculo encerrarPeriodoMatriculas(String curriculoId) {
        Curriculo curriculo = buscar(curriculoId);
        curriculo.encerrarPeriodoMatriculas();
        for (Oferta oferta : ofertaRepository.findByCurriculoId(curriculoId)) {
            oferta.avaliarAtivacao();
            ofertaRepository.save(oferta);
            if (oferta.getStatus() == StatusOferta.CANCELADA) {
                List<Matricula> afetadas = matriculaRepository.findByOfertaIdAndStatus(oferta.getId(),
                        StatusMatricula.ATIVA);
                afetadas.forEach(Matricula::cancelarPorDisciplinaCancelada);
                matriculaRepository.saveAll(afetadas);
            }
        }
        return curriculoRepository.save(curriculo);
    }

    private static void exigirNaoEncerrado(Curriculo curriculo) {
        if (curriculo.getStatus() == StatusCurriculo.MATRICULAS_ENCERRADAS) {
            throw new RegraNegocioException("O currículo do semestre " + curriculo.getSemestre() + " já foi encerrado");
        }
    }

    private static void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            throw new RegraNegocioException("A data de fim das matrículas deve ser igual ou posterior à de início");
        }
    }
}
