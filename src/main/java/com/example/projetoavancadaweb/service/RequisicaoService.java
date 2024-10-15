package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.enuns.StatusRequisicao;
import com.example.projetoavancadaweb.model.Requisicao;
import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.repository.RequisicaoRepository;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequisicaoService {

    private final RequisicaoRepository requisicaoRepository;

    private final UsuarioRepository usuarioRepository;

    public RequisicaoService(RequisicaoRepository requisicaoRepository, UsuarioRepository usuarioRepository) {
        this.requisicaoRepository = requisicaoRepository;
        this.usuarioRepository = usuarioRepository;
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

    public Requisicao atualizarStatusRequisicao(Long id, StatusRequisicao status) {
        Requisicao requisicao = requisicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requisição não encontrada"));
        requisicao.setStatus(status);
        requisicao.setDataAtualizacao(LocalDateTime.now());
        return requisicaoRepository.save(requisicao);
    }
}
