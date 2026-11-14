package br.pucminas.matriculas.model;

import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Usuário do sistema. Todo usuário possui login e senha usados na autenticação.
 */
public abstract class Usuario {

    @Id
    private String id;
    private String nome;
    private String login;
    /** Hash da senha (BCrypt). A senha nunca é armazenada em texto puro. */
    private String senha;

    protected Usuario() {
    }

    protected Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public abstract Perfil getPerfil();

    /** Verifica se a senha informada no login corresponde ao hash armazenado. */
    public boolean validarSenha(String senhaInformada, PasswordEncoder encoder) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
