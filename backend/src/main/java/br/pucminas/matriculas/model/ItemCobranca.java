package br.pucminas.matriculas.model;

import java.math.BigDecimal;

/** Disciplina cobrada do aluno em uma notificação de cobrança. */
public record ItemCobranca(String disciplinaCodigo, String disciplinaNome, BigDecimal valor) {
}
