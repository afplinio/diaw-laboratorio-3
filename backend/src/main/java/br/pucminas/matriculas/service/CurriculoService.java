package br.pucminas.matriculas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Oferta;
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
        throw new UnsupportedOperationException("Não implementado");
    }

    public Curriculo buscar(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Curriculo criar(Curriculo curriculo) {
        throw new UnsupportedOperationException("Não implementado");
    }

    /** Currículo cujo período de matrículas está aberto, se houver. */
    public Optional<Curriculo> buscarComMatriculasAbertas() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public List<Oferta> listarOfertas(String curriculoId) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Oferta adicionarOferta(String curriculoId, String disciplinaId, String professorId) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void removerOferta(String curriculoId, String ofertaId) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Curriculo abrirPeriodoMatriculas(String curriculoId) {
        throw new UnsupportedOperationException("Não implementado");
    }

    /**
     * Encerra o período: disciplinas com pelo menos 3 alunos ficam ativas e as demais são canceladas.
     */
    public Curriculo encerrarPeriodoMatriculas(String curriculoId) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
