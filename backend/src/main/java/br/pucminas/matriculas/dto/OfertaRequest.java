package br.pucminas.matriculas.dto;

import jakarta.validation.constraints.NotBlank;

public record OfertaRequest(
        @NotBlank(message = "Informe a disciplina") String disciplinaId,
        @NotBlank(message = "Informe o professor") String professorId) {
}
