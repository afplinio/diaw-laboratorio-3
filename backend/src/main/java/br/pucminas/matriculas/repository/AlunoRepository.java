package br.pucminas.matriculas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.Aluno;

public interface AlunoRepository extends MongoRepository<Aluno, String> {

    Optional<Aluno> findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByMatricula(String matricula);

    List<Aluno> findByCursoId(String cursoId);
}
