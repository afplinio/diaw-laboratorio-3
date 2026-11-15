package br.pucminas.matriculas.dto;

import br.pucminas.matriculas.model.TipoMatricula;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MatriculaRequest(
        @NotBlank(message = "Informe a disciplina") String ofertaId,
        @NotNull(message = "Informe se a matrícula é obrigatória ou optativa") TipoMatricula tipo) {
}
