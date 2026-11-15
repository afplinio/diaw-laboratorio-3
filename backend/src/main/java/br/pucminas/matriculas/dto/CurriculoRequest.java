package br.pucminas.matriculas.dto;

import java.time.LocalDate;

import br.pucminas.matriculas.model.Curriculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CurriculoRequest(
        @NotBlank(message = "Informe o semestre")
        @Pattern(regexp = "\\d{4}/[12]", message = "O semestre deve estar no formato AAAA/1 ou AAAA/2") String semestre,
        @NotNull(message = "Informe o início do período de matrículas") LocalDate inicioMatriculas,
        @NotNull(message = "Informe o fim do período de matrículas") LocalDate fimMatriculas) {

    public Curriculo paraCurriculo() {
        return new Curriculo(semestre, inicioMatriculas, fimMatriculas);
    }
}
