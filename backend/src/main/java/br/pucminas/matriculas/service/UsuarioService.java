package br.pucminas.matriculas.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Usuario;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.ProfessorRepository;
import br.pucminas.matriculas.repository.SecretariaRepository;

/** Operações comuns a todos os usuários (alunos, professores e secretaria). */
@Service
public class UsuarioService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final SecretariaRepository secretariaRepository;

    public UsuarioService(AlunoRepository alunoRepository, ProfessorRepository professorRepository,
            SecretariaRepository secretariaRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.secretariaRepository = secretariaRepository;
    }

    /** Procura o usuário pelo login em todos os perfis. */
    public Optional<Usuario> buscarPorLogin(String login) {
        return alunoRepository.findByLogin(login).<Usuario>map(u -> u)
                .or(() -> professorRepository.findByLogin(login))
                .or(() -> secretariaRepository.findByLogin(login));
    }

    /** Indica se o login já é usado por algum usuário do sistema. */
    public boolean loginEmUso(String login) {
        return alunoRepository.existsByLogin(login)
                || professorRepository.existsByLogin(login)
                || secretariaRepository.existsByLogin(login);
    }
}
