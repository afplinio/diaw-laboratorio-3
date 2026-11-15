package br.pucminas.matriculas.exception;

/** Violação de uma regra de negócio do sistema de matrículas (resulta em HTTP 422). */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
