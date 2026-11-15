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
    private String alunoNome;
    private String alunoMatricula;
    private String semestre;
    private MotivoNotificacao motivo;
    private List<ItemCobranca> itens = new ArrayList<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDateTime dataNotificacao;

    public NotificacaoCobranca() {
    }

    public NotificacaoCobranca(Aluno aluno, String semestre, MotivoNotificacao motivo, List<ItemCobranca> itens) {
        this.alunoId = aluno.getId();
        this.alunoNome = aluno.getNome();
        this.alunoMatricula = aluno.getMatricula();
        this.semestre = semestre;
        this.motivo = motivo;
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

    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    public String getAlunoMatricula() {
        return alunoMatricula;
    }

    public void setAlunoMatricula(String alunoMatricula) {
        this.alunoMatricula = alunoMatricula;
    }

    public MotivoNotificacao getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoNotificacao motivo) {
        this.motivo = motivo;
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
