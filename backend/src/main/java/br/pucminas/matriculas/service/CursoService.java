package br.pucminas.matriculas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.exception.RecursoNaoEncontradoException;
import br.pucminas.matriculas.exception.RegraNegocioException;
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
        return cursoRepository.findAll();
    }

    public Curso buscar(String id) {
        return cursoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Curso", id));
    }

    public Curso criar(Curso curso) {
        validarDisciplinas(curso);
        curso.setId(null);
        return cursoRepository.save(curso);
    }

    public Curso atualizar(String id, Curso dados) {
        Curso curso = buscar(id);
        validarDisciplinas(dados);
        curso.setNome(dados.getNome());
        curso.setCreditos(dados.getCreditos());
        curso.setDisciplinaIds(dados.getDisciplinaIds());
        return cursoRepository.save(curso);
    }

    public void remover(String id) {
        Curso curso = buscar(id);
        if (!alunoRepository.findByCursoId(id).isEmpty()) {
            throw new RegraNegocioException("Não é possível remover um curso que possui alunos");
        }
        cursoRepository.delete(curso);
    }

    private void validarDisciplinas(Curso curso) {
        for (String disciplinaId : curso.getDisciplinaIds()) {
            if (!disciplinaRepository.existsById(disciplinaId)) {
                throw new RecursoNaoEncontradoException("Disciplina", disciplinaId);
            }
        }
    }
}
