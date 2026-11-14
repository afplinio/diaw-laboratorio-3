package br.pucminas.matriculas.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.pucminas.matriculas.model.NotificacaoCobranca;

public interface NotificacaoCobrancaRepository extends MongoRepository<NotificacaoCobranca, String> {

    List<NotificacaoCobranca> findAllByOrderByDataNotificacaoDesc();

    List<NotificacaoCobranca> findByAlunoIdOrderByDataNotificacaoDesc(String alunoId);
}
