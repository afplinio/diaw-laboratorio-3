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

import br.pucminas.matriculas.dto.CursoRequest;
import br.pucminas.matriculas.model.Curso;
import br.pucminas.matriculas.service.CursoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public Curso buscar(@PathVariable String id) {
        return cursoService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('SECRETARIA')")
    public Curso criar(@Valid @RequestBody CursoRequest request) {
        return cursoService.criar(request.paraCurso());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIA')")
    public Curso atualizar(@PathVariable String id, @Valid @RequestBody CursoRequest request) {
        return cursoService.atualizar(id, request.paraCurso());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('SECRETARIA')")
    public void remover(@PathVariable String id) {
        cursoService.remover(id);
    }
}
