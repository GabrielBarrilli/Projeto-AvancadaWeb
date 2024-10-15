package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

//    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("O email já está em uso.");
        }
        // Criptografa a senha antes de salvar
        //usuario.setPassword(passwordEncoder.encode(usuario.getSenha()));

        usuario.setPassword(usuario.getPassword());
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        try {
            return usuarioRepository.findByEmail(email);
        } catch (Exception e) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
