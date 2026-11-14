package br.pucminas.matriculas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Curso;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.CursoRepository;
import br.pucminas.matriculas.repository.DisciplinaRepository;

/** Manutenção dos cursos pela secretaria. */
@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final AlunoRepository alunoRepository;

    public CursoService(CursoRepository cursoRepository, DisciplinaRepository disciplinaRepository,
            AlunoRepository alunoRepository) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<Curso> listar() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Curso buscar(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Curso criar(Curso curso) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Curso atualizar(String id, Curso dados) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void remover(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
