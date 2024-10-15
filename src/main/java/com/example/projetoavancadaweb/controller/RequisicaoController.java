package com.example.projetoavancadaweb.controller;

import com.example.projetoavancadaweb.enuns.StatusRequisicao;
import com.example.projetoavancadaweb.model.Requisicao;
import com.example.projetoavancadaweb.service.RequisicaoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {

    private final RequisicaoService requisicaoService;

    public RequisicaoController(RequisicaoService requisicaoService) {
        this.requisicaoService = requisicaoService;
    }

    @PostMapping
    public ResponseEntity<Requisicao> criarRequisicao(@RequestBody Requisicao requisicao, Principal principal) {
        Requisicao novaRequisicao = requisicaoService.criarRequisicao(requisicao, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRequisicao);
    }

    @GetMapping
    public List<Requisicao> listarRequisicoes() {
        return requisicaoService.listarRequisicoes();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Requisicao> aprovarRequisicao(@PathVariable Long id) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarStatusRequisicao(id, StatusRequisicao.APROVADA);
        return ResponseEntity.ok(requisicaoAtualizada);
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Requisicao> rejeitarRequisicao(@PathVariable Long id) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarStatusRequisicao(id, StatusRequisicao.REJEITADA);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
}