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

import br.pucminas.matriculas.dto.AlunoRequest;
import br.pucminas.matriculas.dto.AlunoResponse;
import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.service.AlunoService;
import jakarta.validation.Valid;

/** Cadastro de alunos (acesso exclusivo da secretaria). */
@RestController
@RequestMapping("/api/alunos")
@PreAuthorize("hasRole('SECRETARIA')")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<AlunoResponse> listar() {
        return alunoService.listar().stream().map(AlunoResponse::de).toList();
    }

    @GetMapping("/{id}")
    public AlunoResponse buscar(@PathVariable String id) {
        return AlunoResponse.de(alunoService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse criar(@Valid @RequestBody AlunoRequest request) {
        return AlunoResponse.de(alunoService.criar(paraAluno(request), request.senha()));
    }

    @PutMapping("/{id}")
    public AlunoResponse atualizar(@PathVariable String id, @Valid @RequestBody AlunoRequest request) {
        return AlunoResponse.de(alunoService.atualizar(id, paraAluno(request), request.senha()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable String id) {
        alunoService.remover(id);
    }

    private static Aluno paraAluno(AlunoRequest request) {
        return new Aluno(request.nome().trim(), request.login().trim(), null, request.matricula().trim(),
                request.cursoId());
    }
}
