package br.pucminas.matriculas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Notificação enviada ao sistema de cobranças após a inscrição do aluno no semestre,
 * com as disciplinas que devem ser cobradas e o valor total.
 */
@Document(collection = "notificacoes_cobranca")
public class NotificacaoCobranca {

    @Id
    private String id;
    private String alunoId;
    private String semestre;
    private List<ItemCobranca> itens = new ArrayList<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDateTime dataNotificacao;

    public NotificacaoCobranca() {
    }

    public NotificacaoCobranca(String alunoId, String semestre, List<ItemCobranca> itens) {
        this.alunoId = alunoId;
        this.semestre = semestre;
        this.itens = new ArrayList<>(itens);
        this.valorTotal = itens.stream().map(ItemCobranca::valor).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.dataNotificacao = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<ItemCobranca> getItens() {
        return itens;
    }

    public void setItens(List<ItemCobranca> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(LocalDateTime dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }
}
