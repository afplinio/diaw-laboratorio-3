package br.pucminas.matriculas.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.exception.RecursoNaoEncontradoException;
import br.pucminas.matriculas.exception.RegraNegocioException;
import br.pucminas.matriculas.model.Curso;
import br.pucminas.matriculas.model.Disciplina;
import br.pucminas.matriculas.repository.CursoRepository;
import br.pucminas.matriculas.repository.DisciplinaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;

/** Manutenção das disciplinas pela secretaria. */
@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final CursoRepository cursoRepository;
    private final OfertaRepository ofertaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, CursoRepository cursoRepository,
            OfertaRepository ofertaRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.cursoRepository = cursoRepository;
        this.ofertaRepository = ofertaRepository;
    }

    public List<Disciplina> listar() {
        return disciplinaRepository.findAll().stream()
                .sorted(Comparator.comparing(Disciplina::getCodigo))
                .toList();
    }

    public Disciplina buscar(String id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina", id));
    }

    public Disciplina criar(Disciplina disciplina) {
        if (disciplinaRepository.existsByCodigo(disciplina.getCodigo())) {
            throw new RegraNegocioException("Já existe uma disciplina com o código " + disciplina.getCodigo());
        }
        disciplina.setId(null);
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina atualizar(String id, Disciplina dados) {
        Disciplina disciplina = buscar(id);
        if (!disciplina.getCodigo().equals(dados.getCodigo()) && disciplinaRepository.existsByCodigo(dados.getCodigo())) {
            throw new RegraNegocioException("Já existe uma disciplina com o código " + dados.getCodigo());
        }
        disciplina.setCodigo(dados.getCodigo());
        disciplina.setNome(dados.getNome());
        disciplina.setCreditos(dados.getCreditos());
        disciplina.setValor(dados.getValor());
        return disciplinaRepository.save(disciplina);
    }

    public void remover(String id) {
        Disciplina disciplina = buscar(id);
        if (ofertaRepository.existsByDisciplinaId(id)) {
            throw new RegraNegocioException("A disciplina já foi ofertada em um currículo e não pode ser removida");
        }
        for (Curso curso : cursoRepository.findByDisciplinaIdsContaining(id)) {
            curso.removerDisciplina(id);
            cursoRepository.save(curso);
        }
        disciplinaRepository.delete(disciplina);
    }
}
