package com.example.projetoavancadaweb.web.dto.request;

public record CriarRequisicaoRequest(
        String titulo,
        String descricao,
        String questoes,
        String resposta
) {
}
