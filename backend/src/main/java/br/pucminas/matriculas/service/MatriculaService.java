package br.pucminas.matriculas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.TipoMatricula;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.CurriculoRepository;
import br.pucminas.matriculas.repository.MatriculaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;

/** Matrícula e cancelamento de matrícula pelos alunos durante o período de matrículas. */
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final OfertaRepository ofertaRepository;
    private final CurriculoRepository curriculoRepository;
    private final AlunoRepository alunoRepository;
    private final SistemaCobrancas sistemaCobrancas;

    public MatriculaService(MatriculaRepository matriculaRepository, OfertaRepository ofertaRepository,
            CurriculoRepository curriculoRepository, AlunoRepository alunoRepository,
            SistemaCobrancas sistemaCobrancas) {
        this.matriculaRepository = matriculaRepository;
        this.ofertaRepository = ofertaRepository;
        this.curriculoRepository = curriculoRepository;
        this.alunoRepository = alunoRepository;
        this.sistemaCobrancas = sistemaCobrancas;
    }

    public Matricula matricular(String alunoId, String ofertaId, TipoMatricula tipo) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public Matricula cancelar(String alunoId, String matriculaId) {
        throw new UnsupportedOperationException("Não implementado");
    }

    public List<Matricula> listarDoAluno(String alunoId) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
