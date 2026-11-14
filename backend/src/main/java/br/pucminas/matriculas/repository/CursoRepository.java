package br.pucminas.matriculas.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Curso;

public interface CursoRepository extends MongoRepository<Curso, String> {

    List<Curso> findByDisciplinaIdsContaining(String disciplinaId);
}
