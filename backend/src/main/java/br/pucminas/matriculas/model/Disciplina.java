package br.pucminas.matriculas.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Disciplina que pode ser ofertada nos currículos semestrais. O valor é usado na cobrança. */
@Document(collection = "disciplinas")
public class Disciplina {

    @Id
    private String id;
    private String codigo;
    private String nome;
    private int creditos;
    private BigDecimal valor;

    public Disciplina() {
    }

    public Disciplina(String codigo, String nome, int creditos, BigDecimal valor) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
