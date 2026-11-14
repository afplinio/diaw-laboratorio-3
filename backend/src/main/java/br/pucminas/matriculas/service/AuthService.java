package br.pucminas.matriculas.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucminas.matriculas.dto.LoginResponse;
import br.pucminas.matriculas.dto.UsuarioResponse;
import br.pucminas.matriculas.model.Usuario;
import br.pucminas.matriculas.security.TokenService;

/** Autenticação de alunos, professores e secretaria por usuário e senha. */
@Service
public class AuthService {

    private static final String CREDENCIAIS_INVALIDAS = "Usuário ou senha inválidos";

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UsuarioService usuarioService, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponse login(String login, String senha) {
        Usuario usuario = usuarioService.buscarPorLogin(login.trim())
                .filter(u -> u.validarSenha(senha, passwordEncoder))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, CREDENCIAIS_INVALIDAS));
        return new LoginResponse(tokenService.gerarToken(usuario), tokenService.getExpiracaoMinutos(),
                UsuarioResponse.de(usuario));
    }

    public UsuarioResponse usuarioAtual(String login) {
        return usuarioService.buscarPorLogin(login)
                .map(UsuarioResponse::de)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão inválida"));
    }
}
