package br.pucminas.matriculas.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Oferta;

public interface OfertaRepository extends MongoRepository<Oferta, String> {

    List<Oferta> findByCurriculoId(String curriculoId);

    List<Oferta> findByProfessorId(String professorId);

    boolean existsByCurriculoIdAndDisciplinaId(String curriculoId, String disciplinaId);

    boolean existsByDisciplinaId(String disciplinaId);

    boolean existsByProfessorId(String professorId);
}
