package br.pucminas.matriculas.dto;

import br.pucminas.matriculas.model.Aluno;

public record AlunoMatriculadoResponse(String id, String nome, String matricula, String curso) {

    public static AlunoMatriculadoResponse de(Aluno aluno, String curso) {
        return new AlunoMatriculadoResponse(aluno.getId(), aluno.getNome(), aluno.getMatricula(), curso);
    }
}
