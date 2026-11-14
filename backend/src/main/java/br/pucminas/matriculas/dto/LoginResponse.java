package br.pucminas.matriculas.dto;

public record LoginResponse(String token, long expiraEmMinutos, UsuarioResponse usuario) {
}
