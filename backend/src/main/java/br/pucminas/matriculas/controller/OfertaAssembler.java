package br.pucminas.matriculas.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.pucminas.matriculas.dto.OfertaResponse;
import br.pucminas.matriculas.model.Disciplina;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.Professor;
import br.pucminas.matriculas.repository.DisciplinaRepository;
import br.pucminas.matriculas.repository.ProfessorRepository;

/** Monta as respostas de ofertas buscando disciplinas e professores em lote. */
@Component
public class OfertaAssembler {

    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    public OfertaAssembler(DisciplinaRepository disciplinaRepository, ProfessorRepository professorRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    public List<OfertaResponse> montar(Collection<Oferta> ofertas) {
        Map<String, Disciplina> disciplinas = disciplinaRepository
                .findAllById(ofertas.stream().map(Oferta::getDisciplinaId).distinct().toList()).stream()
                .collect(Collectors.toMap(Disciplina::getId, Function.identity()));
        Map<String, Professor> professores = professorRepository
                .findAllById(ofertas.stream().map(Oferta::getProfessorId).distinct().toList()).stream()
                .collect(Collectors.toMap(Professor::getId, Function.identity()));
        return ofertas.stream()
                .map(o -> OfertaResponse.de(o, disciplinas.get(o.getDisciplinaId()), professores.get(o.getProfessorId())))
                .sorted(Comparator.comparing(OfertaResponse::codigo))
                .toList();
    }

    public OfertaResponse montar(Oferta oferta) {
        return montar(List.of(oferta)).getFirst();
    }
}
