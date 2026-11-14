package br.pucminas.matriculas.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.Professor;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.MatriculaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;
import br.pucminas.matriculas.repository.ProfessorRepository;

/** Manutenção dos professores e consultas feitas pelo professor. */
@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final OfertaRepository ofertaRepository;
    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public ProfessorService(ProfessorRepository professorRepository, OfertaRepository ofertaRepository,
            MatriculaRepository matriculaRepository, AlunoRepository alunoRepository, UsuarioService usuarioService,
            PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.ofertaRepository = ofertaRepository;
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Professor> listar() {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Professor buscar(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Professor criar(Professor professor, String senha) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Professor atualizar(String id, Professor dados, String novaSenha) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void remover(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }

    /** Disciplinas ofertadas que o professor leciona. */
    public List<Oferta> listarOfertas(String professorId) {
        throw new UnsupportedOperationException("Não implementado");
    }

    /** Alunos matriculados em uma disciplina lecionada pelo professor. */
    public List<Aluno> listarAlunosMatriculados(String professorId, String ofertaId) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
