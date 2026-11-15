package br.pucminas.matriculas.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.matriculas.dto.MatriculaRequest;
import br.pucminas.matriculas.dto.MatriculaResponse;
import br.pucminas.matriculas.dto.OfertaResponse;
import br.pucminas.matriculas.dto.PainelMatriculaResponse;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.repository.CurriculoRepository;
import br.pucminas.matriculas.repository.OfertaRepository;
import br.pucminas.matriculas.service.CurriculoService;
import br.pucminas.matriculas.service.MatriculaService;
import jakarta.validation.Valid;

/** Funcionalidades do aluno autenticado: consultar ofertas, matricular-se e cancelar matrículas. */
@RestController
@RequestMapping("/api/aluno")
@PreAuthorize("hasRole('ALUNO')")
public class AreaAlunoController {

    private final MatriculaService matriculaService;
    private final CurriculoService curriculoService;
    private final OfertaAssembler ofertaAssembler;
    private final OfertaRepository ofertaRepository;
    private final CurriculoRepository curriculoRepository;

    public AreaAlunoController(MatriculaService matriculaService, CurriculoService curriculoService,
            OfertaAssembler ofertaAssembler, OfertaRepository ofertaRepository,
            CurriculoRepository curriculoRepository) {
        this.matriculaService = matriculaService;
        this.curriculoService = curriculoService;
        this.ofertaAssembler = ofertaAssembler;
        this.ofertaRepository = ofertaRepository;
        this.curriculoRepository = curriculoRepository;
    }

    @GetMapping("/painel")
    public PainelMatriculaResponse painel() {
        return curriculoService.buscarComMatriculasAbertas()
                .map(c -> new PainelMatriculaResponse(c, c.isPeriodoMatriculasAberto(LocalDate.now()),
                        Matricula.MAXIMO_OBRIGATORIAS, Matricula.MAXIMO_OPTATIVAS,
                        ofertaAssembler.montar(ofertaRepository.findByCurriculoId(c.getId()))))
                .orElseGet(() -> new PainelMatriculaResponse(null, false, Matricula.MAXIMO_OBRIGATORIAS,
                        Matricula.MAXIMO_OPTATIVAS, List.of()));
    }

    @GetMapping("/matriculas")
    public List<MatriculaResponse> minhasMatriculas(@AuthenticationPrincipal Jwt jwt) {
        return montar(matriculaService.listarDoAluno(jwt.getSubject()));
    }

    @PostMapping("/matriculas")
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaResponse matricular(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody MatriculaRequest request) {
        return montar(List.of(matriculaService.matricular(jwt.getSubject(), request.ofertaId(), request.tipo())))
                .getFirst();
    }

    @DeleteMapping("/matriculas/{id}")
    public MatriculaResponse cancelar(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        return montar(List.of(matriculaService.cancelar(jwt.getSubject(), id))).getFirst();
    }

    private List<MatriculaResponse> montar(List<Matricula> matriculas) {
        List<Oferta> ofertas = ofertaRepository.findAllById(matriculas.stream().map(Matricula::getOfertaId).toList());
        Map<String, OfertaResponse> ofertasPorId = ofertaAssembler.montar(ofertas).stream()
                .collect(Collectors.toMap(OfertaResponse::id, Function.identity()));
        Map<String, String> semestres = curriculoRepository
                .findAllById(matriculas.stream().map(Matricula::getCurriculoId).distinct().toList()).stream()
                .collect(Collectors.toMap(Curriculo::getId, Curriculo::getSemestre));
        return matriculas.stream()
                .map(m -> MatriculaResponse.de(m, semestres.get(m.getCurriculoId()), ofertasPorId.get(m.getOfertaId())))
                .toList();
    }
}
