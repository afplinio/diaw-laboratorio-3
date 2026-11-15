package br.pucminas.matriculas.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Curso da universidade: tem nome, número de créditos e é constituído por diversas disciplinas. */
@Document(collection = "cursos")
public class Curso {

    @Id
    private String id;
    private String nome;
    private int creditos;
    private List<String> disciplinaIds = new ArrayList<>();

    public Curso() {
    }

    public Curso(String nome, int creditos) {
        this.nome = nome;
        this.creditos = creditos;
    }

    public void adicionarDisciplina(String disciplinaId) {
        if (disciplinaId != null && !disciplinaIds.contains(disciplinaId)) {
            disciplinaIds.add(disciplinaId);
        }
    }

    public void removerDisciplina(String disciplinaId) {
        disciplinaIds.remove(disciplinaId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getDisciplinaIds() {
        return disciplinaIds;
    }

    public void setDisciplinaIds(List<String> disciplinaIds) {
        this.disciplinaIds = disciplinaIds == null ? new ArrayList<>() : new ArrayList<>(disciplinaIds);
    }
}
