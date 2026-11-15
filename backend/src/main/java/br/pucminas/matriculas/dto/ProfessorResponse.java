package br.pucminas.matriculas.dto;

import br.pucminas.matriculas.model.Professor;

public record ProfessorResponse(String id, String nome, String login, String departamento) {

    public static ProfessorResponse de(Professor professor) {
        return new ProfessorResponse(professor.getId(), professor.getNome(), professor.getLogin(),
                professor.getDepartamento());
    }
}
