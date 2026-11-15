package br.pucminas.matriculas.model;

/**
 * Situação de uma matrícula do aluno em uma oferta.
 * DISCIPLINA_CANCELADA: a disciplina não atingiu o mínimo de alunos ao fim do período.
 */
public enum StatusMatricula {
    ATIVA,
    CANCELADA,
    DISCIPLINA_CANCELADA
}
