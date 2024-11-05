package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.enuns.StatusRequisicao;
import com.example.projetoavancadaweb.model.Requisicao;
import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.repository.RequisicaoRepository;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import com.example.projetoavancadaweb.security.SecurityUtil;
import com.example.projetoavancadaweb.web.dto.request.AtualizaRequisicaoRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequisicaoService {

    private final RequisicaoRepository requisicaoRepository;

    private final UsuarioRepository usuarioRepository;

    private final AtualizaService atualizaService;

    public RequisicaoService(RequisicaoRepository requisicaoRepository, UsuarioRepository usuarioRepository, AtualizaService atualizaService) {
        this.requisicaoRepository = requisicaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.atualizaService = atualizaService;
    }

    public Requisicao criarRequisicao(Requisicao requisicao, String emailUsuario) {
        Usuario pai = usuarioRepository.findByEmail(emailUsuario);
        requisicao.setPai(pai);
        requisicao.setStatus(StatusRequisicao.PENDENTE);
        requisicao.setDataCriacao(LocalDateTime.now());
        return requisicaoRepository.save(requisicao);
    }

    public List<Requisicao> listarRequisicoes() {
        return requisicaoRepository.findAll();
    }

    public Requisicao atualizarStatusRequisicao(Long id, StatusRequisicao status, AtualizaRequisicaoRequest request) {
        if (status.equals(StatusRequisicao.PENDENTE)) {
            throw new IllegalArgumentException("Status não pode ser PENDENTE");
        }
        else if (status.equals(StatusRequisicao.APROVADA) || status.equals(StatusRequisicao.REJEITADA)) {
            Requisicao requisicao = requisicaoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Requisição não encontrada"));

            requisicao.setStatus(status);
            requisicao.setDataAtualizacao(LocalDateTime.now());
            requisicao.setResposta(request.resposta());

            var usuarioUltimaAlteracao = SecurityUtil.getCurrentUsername();
            requisicao.setUsuarioUltimaAlteracao(usuarioUltimaAlteracao);

            atualizaService.salvarAtualizacao(usuarioUltimaAlteracao);

            return requisicaoRepository.save(requisicao);
        }
        else {
            throw new IllegalArgumentException("Status inválido");
        }

    }
}
