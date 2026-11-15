package br.pucminas.matriculas.exception;

import java.time.Instant;
import java.util.List;

/** Corpo padronizado das respostas de erro da API. */
public record ErroResponse(int status, String mensagem, List<String> erros, Instant timestamp) {

    public static ErroResponse de(int status, String mensagem) {
        return new ErroResponse(status, mensagem, List.of(), Instant.now());
    }
}
