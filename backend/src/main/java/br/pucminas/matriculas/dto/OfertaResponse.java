package br.pucminas.matriculas.dto;

import java.math.BigDecimal;

import br.pucminas.matriculas.model.Disciplina;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.Professor;
import br.pucminas.matriculas.model.StatusOferta;

/** Disciplina ofertada com os dados de disciplina e professor já resolvidos para a interface. */
public record OfertaResponse(
        String id,
        String curriculoId,
        String disciplinaId,
        String codigo,
        String nome,
        int creditos,
        BigDecimal valor,
        String professorId,
        String professorNome,
        int inscritos,
        int vagasDisponiveis,
        int minimoAlunos,
        int maximoAlunos,
        StatusOferta status) {

    public static OfertaResponse de(Oferta oferta, Disciplina disciplina, Professor professor) {
        return new OfertaResponse(
                oferta.getId(),
                oferta.getCurriculoId(),
                oferta.getDisciplinaId(),
                disciplina != null ? disciplina.getCodigo() : "?",
                disciplina != null ? disciplina.getNome() : "Disciplina removida",
                disciplina != null ? disciplina.getCreditos() : 0,
                disciplina != null ? disciplina.getValor() : BigDecimal.ZERO,
                oferta.getProfessorId(),
                professor != null ? professor.getNome() : "—",
                oferta.getInscritos(),
                oferta.getVagasDisponiveis(),
                Oferta.MINIMO_ALUNOS,
                Oferta.MAXIMO_ALUNOS,
                oferta.getStatus());
    }
}
