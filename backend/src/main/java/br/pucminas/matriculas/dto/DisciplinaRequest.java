package br.pucminas.matriculas.dto;

import java.math.BigDecimal;

import br.pucminas.matriculas.model.Disciplina;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record DisciplinaRequest(
        @NotBlank(message = "Informe o código da disciplina") String codigo,
        @NotBlank(message = "Informe o nome da disciplina") String nome,
        @Positive(message = "O número de créditos deve ser maior que zero") int creditos,
        @NotNull(message = "Informe o valor da disciplina")
        @PositiveOrZero(message = "O valor não pode ser negativo") BigDecimal valor) {

    public Disciplina paraDisciplina() {
        return new Disciplina(codigo.trim().toUpperCase(), nome.trim(), creditos, valor);
    }
}
