package br.pucminas.matriculas.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.pucminas.matriculas.exception.RecursoNaoEncontradoException;
import br.pucminas.matriculas.exception.RegraNegocioException;
import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.CursoRepository;
import br.pucminas.matriculas.repository.MatriculaRepository;

/** Manutenção dos alunos pela secretaria. */
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final MatriculaRepository matriculaRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AlunoService(AlunoRepository alunoRepository, CursoRepository cursoRepository,
            MatriculaRepository matriculaRepository, UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.matriculaRepository = matriculaRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Aluno> listar() {
        return alunoRepository.findAll().stream().sorted(Comparator.comparing(Aluno::getNome)).toList();
    }

    public Aluno buscar(String id) {
        return alunoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Aluno", id));
    }

    public Aluno criar(Aluno aluno, String senha) {
        if (senha == null || senha.isBlank()) {
            throw new RegraNegocioException("Informe a senha do aluno");
        }
        if (usuarioService.loginEmUso(aluno.getLogin())) {
            throw new RegraNegocioException("O login " + aluno.getLogin() + " já está em uso");
        }
        if (alunoRepository.existsByMatricula(aluno.getMatricula())) {
            throw new RegraNegocioException("Já existe um aluno com a matrícula " + aluno.getMatricula());
        }
        validarCurso(aluno.getCursoId());
        aluno.setId(null);
        aluno.setSenha(passwordEncoder.encode(senha));
        return alunoRepository.save(aluno);
    }

    /** Atualiza os dados do aluno. A senha só é alterada quando informada. */
    public Aluno atualizar(String id, Aluno dados, String novaSenha) {
        Aluno aluno = buscar(id);
        if (!aluno.getLogin().equals(dados.getLogin()) && usuarioService.loginEmUso(dados.getLogin())) {
            throw new RegraNegocioException("O login " + dados.getLogin() + " já está em uso");
        }
        if (!aluno.getMatricula().equals(dados.getMatricula())
                && alunoRepository.existsByMatricula(dados.getMatricula())) {
            throw new RegraNegocioException("Já existe um aluno com a matrícula " + dados.getMatricula());
        }
        validarCurso(dados.getCursoId());
        aluno.setNome(dados.getNome());
        aluno.setLogin(dados.getLogin());
        aluno.setMatricula(dados.getMatricula());
        aluno.setCursoId(dados.getCursoId());
        if (novaSenha != null && !novaSenha.isBlank()) {
            aluno.setSenha(passwordEncoder.encode(novaSenha));
        }
        return alunoRepository.save(aluno);
    }

    public void remover(String id) {
        Aluno aluno = buscar(id);
        if (matriculaRepository.existsByAlunoId(id)) {
            throw new RegraNegocioException("O aluno possui histórico de matrículas e não pode ser removido");
        }
        alunoRepository.delete(aluno);
    }

    private void validarCurso(String cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new RecursoNaoEncontradoException("Curso", cursoId);
        }
    }
}
