package br.pucminas.matriculas.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.matriculas.model.NotificacaoCobranca;
import br.pucminas.matriculas.service.SistemaCobrancasService;

/** Consulta das notificações enviadas ao sistema de cobranças (secretaria). */
@RestController
@RequestMapping("/api/cobrancas")
@PreAuthorize("hasRole('SECRETARIA')")
public class CobrancaController {

    private final SistemaCobrancasService sistemaCobrancasService;

    public CobrancaController(SistemaCobrancasService sistemaCobrancasService) {
        this.sistemaCobrancasService = sistemaCobrancasService;
    }

    @GetMapping
    public List<NotificacaoCobranca> listar() {
        return sistemaCobrancasService.listarNotificacoes();
    }
}
