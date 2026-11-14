package br.pucminas.matriculas.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
        throw new UnsupportedOperationException("Não implementado");
    }

    public Disciplina buscar(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Disciplina criar(Disciplina disciplina) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Disciplina atualizar(String id, Disciplina dados) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void remover(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
