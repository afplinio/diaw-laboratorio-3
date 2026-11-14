package br.pucminas.matriculas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Disciplina;

public interface DisciplinaRepository extends MongoRepository<Disciplina, String> {

    boolean existsByCodigo(String codigo);
}
