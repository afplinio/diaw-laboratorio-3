package br.pucminas.matriculas.exception;

/** Registro inexistente (resulta em HTTP 404). */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, String id) {
        super(recurso + " não encontrado(a): " + id);
    }
}
