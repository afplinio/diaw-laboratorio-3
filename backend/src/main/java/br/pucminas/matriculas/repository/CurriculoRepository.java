package br.pucminas.matriculas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.StatusCurriculo;

public interface CurriculoRepository extends MongoRepository<Curriculo, String> {

    Optional<Curriculo> findBySemestre(String semestre);

    boolean existsBySemestre(String semestre);

    List<Curriculo> findByStatus(StatusCurriculo status);
}
