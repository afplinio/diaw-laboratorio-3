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

import br.pucminas.matriculas.dto.CurriculoRequest;
import br.pucminas.matriculas.dto.OfertaRequest;
import br.pucminas.matriculas.dto.OfertaResponse;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.service.CurriculoService;
import jakarta.validation.Valid;

/** Currículo semestral: ofertas de disciplinas e período de matrículas. */
@RestController
@RequestMapping("/api/curriculos")
public class CurriculoController {

    private final CurriculoService curriculoService;
    private final OfertaAssembler ofertaAssembler;

    public CurriculoController(CurriculoService curriculoService, OfertaAssembler ofertaAssembler) {
        this.curriculoService = curriculoService;
        this.ofertaAssembler = ofertaAssembler;
    }

    @GetMapping
    public List<Curriculo> listar() {
        return curriculoService.listar();
    }

    @GetMapping("/{id}")
    public Curriculo buscar(@PathVariable String id) {
        return curriculoService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('SECRETARIA')")
    public Curriculo criar(@Valid @RequestBody CurriculoRequest request) {
        return curriculoService.criar(request.paraCurriculo());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETARIA')")
    public Curriculo atualizarPeriodo(@PathVariable String id, @Valid @RequestBody CurriculoRequest request) {
        return curriculoService.atualizarPeriodo(id, request.inicioMatriculas(), request.fimMatriculas());
    }

    @GetMapping("/{id}/ofertas")
    public List<OfertaResponse> listarOfertas(@PathVariable String id) {
        return ofertaAssembler.montar(curriculoService.listarOfertas(id));
    }

    @PostMapping("/{id}/ofertas")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('SECRETARIA')")
    public OfertaResponse adicionarOferta(@PathVariable String id, @Valid @RequestBody OfertaRequest request) {
        return ofertaAssembler.montar(curriculoService.adicionarOferta(id, request.disciplinaId(), request.professorId()));
    }

    @DeleteMapping("/{id}/ofertas/{ofertaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('SECRETARIA')")
    public void removerOferta(@PathVariable String id, @PathVariable String ofertaId) {
        curriculoService.removerOferta(id, ofertaId);
    }

    @PostMapping("/{id}/abrir")
    @PreAuthorize("hasRole('SECRETARIA')")
    public Curriculo abrirPeriodo(@PathVariable String id) {
        return curriculoService.abrirPeriodoMatriculas(id);
    }

    @PostMapping("/{id}/encerrar")
    @PreAuthorize("hasRole('SECRETARIA')")
    public Curriculo encerrarPeriodo(@PathVariable String id) {
        return curriculoService.encerrarPeriodoMatriculas(id);
    }
}
