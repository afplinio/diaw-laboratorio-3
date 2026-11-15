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

import br.pucminas.matriculas.dto.ProfessorRequest;
import br.pucminas.matriculas.dto.ProfessorResponse;
import br.pucminas.matriculas.model.Professor;
import br.pucminas.matriculas.service.ProfessorService;
import jakarta.validation.Valid;

/** Cadastro de professores (acesso exclusivo da secretaria). */
@RestController
@RequestMapping("/api/professores")
@PreAuthorize("hasRole('SECRETARIA')")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public List<ProfessorResponse> listar() {
        return professorService.listar().stream().map(ProfessorResponse::de).toList();
    }

    @GetMapping("/{id}")
    public ProfessorResponse buscar(@PathVariable String id) {
        return ProfessorResponse.de(professorService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessorResponse criar(@Valid @RequestBody ProfessorRequest request) {
        return ProfessorResponse.de(professorService.criar(paraProfessor(request), request.senha()));
    }

    @PutMapping("/{id}")
    public ProfessorResponse atualizar(@PathVariable String id, @Valid @RequestBody ProfessorRequest request) {
        return ProfessorResponse.de(professorService.atualizar(id, paraProfessor(request), request.senha()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable String id) {
        professorService.remover(id);
    }

    private static Professor paraProfessor(ProfessorRequest request) {
        return new Professor(request.nome().trim(), request.login().trim(), null, request.departamento());
    }
}
