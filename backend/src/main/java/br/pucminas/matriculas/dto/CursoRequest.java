package br.pucminas.matriculas.dto;

import java.util.List;

import br.pucminas.matriculas.model.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CursoRequest(
        @NotBlank(message = "Informe o nome do curso") String nome,
        @Positive(message = "O número de créditos deve ser maior que zero") int creditos,
        List<String> disciplinaIds) {

    public Curso paraCurso() {
        Curso curso = new Curso(nome.trim(), creditos);
        curso.setDisciplinaIds(disciplinaIds);
        return curso;
    }
}
