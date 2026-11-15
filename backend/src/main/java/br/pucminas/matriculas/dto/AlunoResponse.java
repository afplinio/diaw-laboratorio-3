package br.pucminas.matriculas.dto;

import br.pucminas.matriculas.model.Aluno;

public record AlunoResponse(String id, String nome, String login, String matricula, String cursoId) {

    public static AlunoResponse de(Aluno aluno) {
        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getLogin(), aluno.getMatricula(),
                aluno.getCursoId());
    }
}
