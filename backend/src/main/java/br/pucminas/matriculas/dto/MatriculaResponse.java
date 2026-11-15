package br.pucminas.matriculas.dto;

import java.time.LocalDateTime;

import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.StatusMatricula;
import br.pucminas.matriculas.model.TipoMatricula;

public record MatriculaResponse(
        String id,
        String semestre,
        TipoMatricula tipo,
        StatusMatricula status,
        LocalDateTime dataMatricula,
        LocalDateTime dataCancelamento,
        OfertaResponse oferta) {

    public static MatriculaResponse de(Matricula matricula, String semestre, OfertaResponse oferta) {
        return new MatriculaResponse(matricula.getId(), semestre, matricula.getTipo(), matricula.getStatus(),
                matricula.getDataMatricula(), matricula.getDataCancelamento(), oferta);
    }
}
