package br.pucminas.matriculas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Secretaria;

public interface SecretariaRepository extends MongoRepository<Secretaria, String> {

    Optional<Secretaria> findByLogin(String login);

    boolean existsByLogin(String login);
}
