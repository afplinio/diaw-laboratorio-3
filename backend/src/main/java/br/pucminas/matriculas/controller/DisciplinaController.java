package br.pucminas.matriculas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.matriculas.dto.DisciplinaRequest;
import br.pucminas.matriculas.model.Disciplina;
import br.pucminas.matriculas.service.DisciplinaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public List<Disciplina> listar() {
        return disciplinaService.listar();
    }

    @GetMapping("/{id}")
    public Disciplina buscar(@PathVariable String id) {
        return disciplinaService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('SECRETARIA')")
    public Disciplina criar(@Valid @RequestBody DisciplinaRequest request) {
        return disciplinaService.criar(request.paraDisciplina());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIA')")
    public Disciplina atualizar(@PathVariable String id, @Valid @RequestBody DisciplinaRequest request) {
        return disciplinaService.atualizar(id, request.paraDisciplina());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('SECRETARIA')")
    public void remover(@PathVariable String id) {
        disciplinaService.remover(id);
    }
}
