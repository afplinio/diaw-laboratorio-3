package br.pucminas.matriculas.dto;

import br.pucminas.matriculas.model.StatusCurriculo;

/** Disciplina lecionada pelo professor, com o semestre do currículo. */
public record OfertaProfessorResponse(String semestre, StatusCurriculo statusCurriculo, OfertaResponse oferta) {
}
