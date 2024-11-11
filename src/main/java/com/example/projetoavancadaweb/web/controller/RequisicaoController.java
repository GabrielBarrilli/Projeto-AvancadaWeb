package com.example.projetoavancadaweb.web.controller;

import com.example.projetoavancadaweb.enuns.StatusRequisicao;
import com.example.projetoavancadaweb.model.Requisicao;
import com.example.projetoavancadaweb.service.RequisicaoService;
import com.example.projetoavancadaweb.service.ValidacaoService;
import com.example.projetoavancadaweb.web.dto.request.AtualizaRequisicaoRequest;
import com.example.projetoavancadaweb.web.dto.request.CriarRequisicaoRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/requisicoes")
public class RequisicaoController {

    private final ValidacaoService validacaoService;

    private final RequisicaoService requisicaoService;

    public RequisicaoController(ValidacaoService validacaoService, RequisicaoService requisicaoService) {
        this.validacaoService = validacaoService;
        this.requisicaoService = requisicaoService;
    }

    @PostMapping
    public ResponseEntity<Requisicao> criarRequisicao(@RequestBody CriarRequisicaoRequest request, Principal principal, @RequestHeader(value = "Authorization", required = false) String token) throws AccessDeniedException {
        validacaoService.validarRoleByToken(List.of("ADMIN", "RESPONSAVEL", "COORD"), token);

        Requisicao novaRequisicao = requisicaoService.criarRequisicao(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRequisicao);
    }

    @GetMapping
    public List<Requisicao> listarRequisicoes(@RequestHeader(value = "Authorization", required = false) String token) throws AccessDeniedException {
        validacaoService.validarRoleByToken(List.of("ADMIN", "RESPONSAVEL", "COORD"), token);

        return requisicaoService.listarRequisicoes();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Requisicao> aprovarRequisicao(@PathVariable Long id, @RequestBody AtualizaRequisicaoRequest request, @RequestHeader(value = "Authorization", required = false) String token ) throws AccessDeniedException {
        validacaoService.validarRoleByToken(List.of("ADMIN", "COORD"), token);

        Requisicao requisicaoAtualizada = requisicaoService.atualizarStatusRequisicao(id, StatusRequisicao.APROVADA, request);
        return ResponseEntity.ok(requisicaoAtualizada);
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Requisicao> rejeitarRequisicao(@PathVariable Long id, @RequestBody AtualizaRequisicaoRequest request, @RequestHeader(value = "Authorization", required = false) String token) throws AccessDeniedException {
        validacaoService.validarRoleByToken(List.of("ADMIN", "COORD"), token);

        Requisicao requisicaoAtualizada = requisicaoService.atualizarStatusRequisicao(id, StatusRequisicao.REJEITADA, request);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
}