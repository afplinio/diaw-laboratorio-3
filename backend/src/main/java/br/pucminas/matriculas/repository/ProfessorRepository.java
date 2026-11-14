package br.pucminas.matriculas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Professor;

public interface ProfessorRepository extends MongoRepository<Professor, String> {

    Optional<Professor> findByLogin(String login);

    boolean existsByLogin(String login);
}
