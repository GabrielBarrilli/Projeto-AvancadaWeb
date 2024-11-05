package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.model.Atualiza;
import com.example.projetoavancadaweb.repository.AtualizaRepository;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AtualizaService {

    private final AtualizaRepository atualizaRepository;

    private final UsuarioRepository usuarioRepository;

    public AtualizaService(AtualizaRepository atualizaRepository, UsuarioRepository usuarioRepository) {
        this.atualizaRepository = atualizaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void salvarAtualizacao(String username) {
        Atualiza atualiza = new Atualiza();

        var usuario = usuarioRepository.findByUsername(username);

        atualiza.setPessoa(usuario);

        atualizaRepository.save(atualiza);
    }
}
