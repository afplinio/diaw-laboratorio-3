package br.pucminas.matriculas.model;

/**
 * Situação de uma disciplina ofertada no semestre.
 * ABERTA: aceitando inscrições; LOTADA: atingiu 60 alunos;
 * ATIVA: vai ocorrer (3+ alunos ao fim do período); CANCELADA: menos de 3 alunos.
 */
public enum StatusOferta {
    ABERTA,
    LOTADA,
    ATIVA,
    CANCELADA
}
