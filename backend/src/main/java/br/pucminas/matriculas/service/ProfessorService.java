package br.pucminas.matriculas.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.pucminas.matriculas.exception.RecursoNaoEncontradoException;
import br.pucminas.matriculas.exception.RegraNegocioException;
import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.Professor;
import br.pucminas.matriculas.model.StatusMatricula;
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
        return professorRepository.findAll().stream().sorted(Comparator.comparing(Professor::getNome)).toList();
    }

    public Professor buscar(String id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Professor", id));
    }

    public Professor criar(Professor professor, String senha) {
        if (senha == null || senha.isBlank()) {
            throw new RegraNegocioException("Informe a senha do professor");
        }
        if (usuarioService.loginEmUso(professor.getLogin())) {
            throw new RegraNegocioException("O login " + professor.getLogin() + " já está em uso");
        }
        professor.setId(null);
        professor.setSenha(passwordEncoder.encode(senha));
        return professorRepository.save(professor);
    }

    public Professor atualizar(String id, Professor dados, String novaSenha) {
        Professor professor = buscar(id);
        if (!professor.getLogin().equals(dados.getLogin()) && usuarioService.loginEmUso(dados.getLogin())) {
            throw new RegraNegocioException("O login " + dados.getLogin() + " já está em uso");
        }
        professor.setNome(dados.getNome());
        professor.setLogin(dados.getLogin());
        professor.setDepartamento(dados.getDepartamento());
        if (novaSenha != null && !novaSenha.isBlank()) {
            professor.setSenha(passwordEncoder.encode(novaSenha));
        }
        return professorRepository.save(professor);
    }

    public void remover(String id) {
        Professor professor = buscar(id);
        if (ofertaRepository.existsByProfessorId(id)) {
            throw new RegraNegocioException("O professor leciona disciplinas ofertadas e não pode ser removido");
        }
        professorRepository.delete(professor);
    }

    /** Disciplinas ofertadas que o professor leciona. */
    public List<Oferta> listarOfertas(String professorId) {
        return ofertaRepository.findByProfessorId(professorId);
    }

    /** Alunos matriculados em uma disciplina lecionada pelo professor. */
    public List<Aluno> listarAlunosMatriculados(String professorId, String ofertaId) {
        Oferta oferta = ofertaRepository.findById(ofertaId)
                .filter(o -> o.getProfessorId().equals(professorId))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina do professor", ofertaId));
        List<String> alunoIds = matriculaRepository.findByOfertaIdAndStatus(oferta.getId(), StatusMatricula.ATIVA)
                .stream().map(Matricula::getAlunoId).toList();
        return alunoRepository.findAllById(alunoIds).stream()
                .sorted(Comparator.comparing(Aluno::getNome))
                .toList();
    }
}
