package br.pucminas.matriculas.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.matriculas.dto.AlunoMatriculadoResponse;
import br.pucminas.matriculas.dto.OfertaProfessorResponse;
import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Curso;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.repository.CurriculoRepository;
import br.pucminas.matriculas.repository.CursoRepository;
import br.pucminas.matriculas.service.ProfessorService;

/** Funcionalidades do professor autenticado: ver os alunos matriculados em cada disciplina. */
@RestController
@RequestMapping("/api/professor")
@PreAuthorize("hasRole('PROFESSOR')")
public class AreaProfessorController {

    private final ProfessorService professorService;
    private final OfertaAssembler ofertaAssembler;
    private final CurriculoRepository curriculoRepository;
    private final CursoRepository cursoRepository;

    public AreaProfessorController(ProfessorService professorService, OfertaAssembler ofertaAssembler,
            CurriculoRepository curriculoRepository, CursoRepository cursoRepository) {
        this.professorService = professorService;
        this.ofertaAssembler = ofertaAssembler;
        this.curriculoRepository = curriculoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping("/ofertas")
    public List<OfertaProfessorResponse> minhasDisciplinas(@AuthenticationPrincipal Jwt jwt) {
        List<Oferta> ofertas = professorService.listarOfertas(jwt.getSubject());
        Map<String, Curriculo> curriculos = curriculoRepository
                .findAllById(ofertas.stream().map(Oferta::getCurriculoId).distinct().toList()).stream()
                .collect(Collectors.toMap(Curriculo::getId, Function.identity()));
        return ofertaAssembler.montar(ofertas).stream()
                .map(o -> {
                    Curriculo c = curriculos.get(o.curriculoId());
                    return new OfertaProfessorResponse(c != null ? c.getSemestre() : "?",
                            c != null ? c.getStatus() : null, o);
                })
                .sorted(Comparator.comparing(OfertaProfessorResponse::semestre).reversed())
                .toList();
    }

    @GetMapping("/ofertas/{ofertaId}/alunos")
    public List<AlunoMatriculadoResponse> alunosMatriculados(@AuthenticationPrincipal Jwt jwt,
            @PathVariable String ofertaId) {
        List<Aluno> alunos = professorService.listarAlunosMatriculados(jwt.getSubject(), ofertaId);
        Map<String, String> cursos = cursoRepository
                .findAllById(alunos.stream().map(Aluno::getCursoId).distinct().toList()).stream()
                .collect(Collectors.toMap(Curso::getId, Curso::getNome));
        return alunos.stream()
                .map(a -> AlunoMatriculadoResponse.de(a, cursos.getOrDefault(a.getCursoId(), "—")))
                .toList();
    }
}
