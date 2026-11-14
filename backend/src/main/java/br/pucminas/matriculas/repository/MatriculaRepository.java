package br.pucminas.matriculas.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.StatusMatricula;
import br.pucminas.matriculas.model.TipoMatricula;

public interface MatriculaRepository extends MongoRepository<Matricula, String> {

    List<Matricula> findByAlunoId(String alunoId);

    List<Matricula> findByAlunoIdAndCurriculoIdAndStatus(String alunoId, String curriculoId, StatusMatricula status);

    List<Matricula> findByOfertaIdAndStatus(String ofertaId, StatusMatricula status);

    boolean existsByAlunoIdAndOfertaIdAndStatus(String alunoId, String ofertaId, StatusMatricula status);

    long countByAlunoIdAndCurriculoIdAndTipoAndStatus(String alunoId, String curriculoId, TipoMatricula tipo,
            StatusMatricula status);

    boolean existsByAlunoId(String alunoId);
}
