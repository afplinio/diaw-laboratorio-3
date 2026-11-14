package br.pucminas.matriculas.dto;

import br.pucminas.matriculas.model.Perfil;
import br.pucminas.matriculas.model.Usuario;

/** Dados públicos do usuário autenticado (sem a senha). */
public record UsuarioResponse(String id, String nome, String login, Perfil perfil) {

    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPerfil());
    }
}
