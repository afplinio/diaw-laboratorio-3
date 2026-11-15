package br.pucminas.matriculas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.pucminas.matriculas.exception.RegraNegocioException;
import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Matricula;
import br.pucminas.matriculas.model.MotivoNotificacao;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.StatusMatricula;
import br.pucminas.matriculas.model.StatusOferta;
import br.pucminas.matriculas.model.TipoMatricula;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.CurriculoRepository;
import br.pucminas.matriculas.repository.MatriculaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;
    @Mock
    private OfertaRepository ofertaRepository;
    @Mock
    private CurriculoRepository curriculoRepository;
    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private SistemaCobrancas sistemaCobrancas;

    @InjectMocks
    private MatriculaService service;

    private Aluno aluno;
    private Curriculo curriculo;
    private Oferta oferta;

    @BeforeEach
    void setUp() {
        aluno = new Aluno("Ana", "ana", "hash", "2026001", "curso");
        aluno.setId("aluno");
        curriculo = new Curriculo("2027/1", LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        curriculo.setId("curriculo");
        curriculo.abrirPeriodoMatriculas();
        oferta = new Oferta("curriculo", "disciplina", "professor");
        oferta.setId("oferta");
    }

    private void cenarioBase() {
        when(alunoRepository.findById("aluno")).thenReturn(Optional.of(aluno));
        when(ofertaRepository.findById("oferta")).thenReturn(Optional.of(oferta));
        when(curriculoRepository.findById("curriculo")).thenReturn(Optional.of(curriculo));
    }

    @Test
    void matriculaRegistraInscricaoENotificaCobranca() {
        cenarioBase();
        when(matriculaRepository.save(any(Matricula.class))).thenAnswer(inv -> inv.getArgument(0));

        Matricula matricula = service.matricular("aluno", "oferta", TipoMatricula.OBRIGATORIA);

        assertEquals(StatusMatricula.ATIVA, matricula.getStatus());
        assertEquals(1, oferta.getInscritos());
        verify(ofertaRepository).save(oferta);
        verify(sistemaCobrancas).notificar(eq(aluno), eq(curriculo), anyList(), eq(MotivoNotificacao.INSCRICAO));
    }

    @Test
    void naoPermiteMaisDe4Obrigatorias() {
        cenarioBase();
        when(matriculaRepository.countByAlunoIdAndCurriculoIdAndTipoAndStatus("aluno", "curriculo",
                TipoMatricula.OBRIGATORIA, StatusMatricula.ATIVA)).thenReturn(4L);

        RegraNegocioException erro = assertThrows(RegraNegocioException.class,
                () -> service.matricular("aluno", "oferta", TipoMatricula.OBRIGATORIA));
        assertEquals("Limite de 4 disciplinas obrigatórias atingido", erro.getMessage());
        verify(matriculaRepository, never()).save(any());
    }

    @Test
    void naoPermiteMaisDe2Optativas() {
        cenarioBase();
        when(matriculaRepository.countByAlunoIdAndCurriculoIdAndTipoAndStatus("aluno", "curriculo",
                TipoMatricula.OPTATIVA, StatusMatricula.ATIVA)).thenReturn(2L);

        assertThrows(RegraNegocioException.class,
                () -> service.matricular("aluno", "oferta", TipoMatricula.OPTATIVA));
    }

    @Test
    void naoPermiteMatriculaEmDisciplinaLotada() {
        cenarioBase();
        oferta.setInscritos(Oferta.MAXIMO_ALUNOS);
        oferta.setStatus(StatusOferta.LOTADA);

        assertThrows(RegraNegocioException.class,
                () -> service.matricular("aluno", "oferta", TipoMatricula.OBRIGATORIA));
        verify(matriculaRepository, never()).save(any());
    }

    @Test
    void naoPermiteMatriculaForaDoPeriodo() {
        cenarioBase();
        curriculo.encerrarPeriodoMatriculas();

        assertThrows(RegraNegocioException.class,
                () -> service.matricular("aluno", "oferta", TipoMatricula.OBRIGATORIA));
    }

    @Test
    void naoPermiteMatriculaDuplicada() {
        cenarioBase();
        when(matriculaRepository.existsByAlunoIdAndOfertaIdAndStatus("aluno", "oferta", StatusMatricula.ATIVA))
                .thenReturn(true);

        assertThrows(RegraNegocioException.class,
                () -> service.matricular("aluno", "oferta", TipoMatricula.OPTATIVA));
    }

    @Test
    void cancelamentoLiberaVagaENotificaCobranca() {
        Matricula matricula = new Matricula("aluno", "oferta", "curriculo", TipoMatricula.OBRIGATORIA);
        matricula.setId("matricula");
        oferta.setInscritos(Oferta.MAXIMO_ALUNOS);
        oferta.setStatus(StatusOferta.LOTADA);
        when(matriculaRepository.findById("matricula")).thenReturn(Optional.of(matricula));
        when(curriculoRepository.findById("curriculo")).thenReturn(Optional.of(curriculo));
        when(ofertaRepository.findById("oferta")).thenReturn(Optional.of(oferta));
        when(alunoRepository.findById("aluno")).thenReturn(Optional.of(aluno));
        when(matriculaRepository.findByAlunoIdAndCurriculoIdAndStatus("aluno", "curriculo", StatusMatricula.ATIVA))
                .thenReturn(List.of());

        service.cancelar("aluno", "matricula");

        assertEquals(StatusMatricula.CANCELADA, matricula.getStatus());
        assertEquals(StatusOferta.ABERTA, oferta.getStatus());
        assertEquals(Oferta.MAXIMO_ALUNOS - 1, oferta.getInscritos());
        verify(sistemaCobrancas).notificar(aluno, curriculo, List.of(), MotivoNotificacao.CANCELAMENTO);
    }
}
