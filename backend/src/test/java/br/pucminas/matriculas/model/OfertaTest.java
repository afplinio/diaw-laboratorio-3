package br.pucminas.matriculas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.pucminas.matriculas.exception.RegraNegocioException;

class OfertaTest {

    private Oferta ofertaCom(int inscritos) {
        Oferta oferta = new Oferta("curriculo", "disciplina", "professor");
        for (int i = 0; i < inscritos; i++) {
            oferta.registrarInscricao();
        }
        return oferta;
    }

    @Test
    void novaOfertaComecaAbertaESemInscritos() {
        Oferta oferta = ofertaCom(0);
        assertEquals(StatusOferta.ABERTA, oferta.getStatus());
        assertTrue(oferta.isInscricoesAbertas());
        assertEquals(60, oferta.getVagasDisponiveis());
    }

    @Test
    void encerraInscricoesAoAtingir60Alunos() {
        Oferta oferta = ofertaCom(60);
        assertEquals(StatusOferta.LOTADA, oferta.getStatus());
        assertFalse(oferta.isInscricoesAbertas());
        assertEquals(0, oferta.getVagasDisponiveis());
        assertThrows(RegraNegocioException.class, oferta::registrarInscricao);
    }

    @Test
    void cancelamentoReabreOfertaLotada() {
        Oferta oferta = ofertaCom(60);
        oferta.removerInscricao();
        assertEquals(StatusOferta.ABERTA, oferta.getStatus());
        assertEquals(59, oferta.getInscritos());
        assertTrue(oferta.isInscricoesAbertas());
    }

    @Test
    void ficaAtivaComPeloMenos3Alunos() {
        Oferta oferta = ofertaCom(3);
        oferta.avaliarAtivacao();
        assertEquals(StatusOferta.ATIVA, oferta.getStatus());
    }

    @Test
    void eCanceladaComMenosDe3Alunos() {
        Oferta oferta = ofertaCom(2);
        oferta.avaliarAtivacao();
        assertEquals(StatusOferta.CANCELADA, oferta.getStatus());
        assertFalse(oferta.isInscricoesAbertas());
    }
}
