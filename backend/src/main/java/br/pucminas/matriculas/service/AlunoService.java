package br.pucminas.matriculas.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        throw new UnsupportedOperationException("Não implementado");
    }

    public Aluno buscar(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Aluno criar(Aluno aluno, String senha) {
        throw new UnsupportedOperationException("Não implementado");
    }

    /** Atualiza os dados do aluno. A senha só é alterada quando informada. */
    public Aluno atualizar(String id, Aluno dados, String novaSenha) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public void remover(String id) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
