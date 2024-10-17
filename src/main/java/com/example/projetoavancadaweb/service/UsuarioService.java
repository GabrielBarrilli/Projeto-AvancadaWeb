package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.model.Role;
import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.model.dto.request.CriarUsuarioRequest;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Passar como argumento

    public UsuarioService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Receber o PasswordEncoder
    }

    public void salvarUsuario(CriarUsuarioRequest criarUsuarioRequest) {
        Usuario newUser = Usuario.builder()
                .email(criarUsuarioRequest.email())
                .password(passwordEncoder.encode(criarUsuarioRequest.password()))
                .role(Role.builder().name(criarUsuarioRequest.role()).build().getName())
                .build();

        usuarioRepository.save(newUser);
    }

    public Usuario buscarRolePorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}

