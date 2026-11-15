package br.pucminas.matriculas.dto;

import java.util.List;

import br.pucminas.matriculas.model.Curriculo;

/** Informações que o aluno precisa para se matricular no semestre com período aberto. */
public record PainelMatriculaResponse(
        Curriculo curriculo,
        boolean periodoAberto,
        int maximoObrigatorias,
        int maximoOptativas,
        List<OfertaResponse> ofertas) {
}
