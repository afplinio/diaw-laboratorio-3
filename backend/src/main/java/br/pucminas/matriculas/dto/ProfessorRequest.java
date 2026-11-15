package br.pucminas.matriculas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dados do professor. A senha é obrigatória no cadastro e opcional na edição. */
public record ProfessorRequest(
        @NotBlank(message = "Informe o nome") String nome,
        @NotBlank(message = "Informe o login") String login,
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres") String senha,
        String departamento) {
}
