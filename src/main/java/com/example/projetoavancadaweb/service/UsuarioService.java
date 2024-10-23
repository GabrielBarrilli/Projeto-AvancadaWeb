package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.model.Pessoa;
import com.example.projetoavancadaweb.model.Role;
import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import com.example.projetoavancadaweb.web.dto.request.CriarUsuarioRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void salvarUsuario(Usuario.Role role, CriarUsuarioRequest criarUsuarioRequest) {
        Usuario newUser = Usuario.builder()
                .email(criarUsuarioRequest.email())
                .password(passwordEncoder.encode(criarUsuarioRequest.password()))
                .role(Role.builder().name(Usuario.Role.valueOf(role.name())).build().getName())
                .ativo(true)
                .build();

        Pessoa pessoa = Pessoa.builder()
                .nome(criarUsuarioRequest.nome())
                .genero(criarUsuarioRequest.genero())
                .nome(criarUsuarioRequest.nome())
                .cpf(criarUsuarioRequest.cpf())
                .dataNascimento(criarUsuarioRequest.getDataNascimentoAsLocalDate())
                .endereco(criarUsuarioRequest.endereco())
                .telefone(criarUsuarioRequest.telefone())
                .genero(criarUsuarioRequest.genero())
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        newUser.setPessoa(pessoa);
        usuarioRepository.save(newUser);
    }

    public Usuario buscarRolePorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario buscarRolePorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> buscarByAtivo(Boolean ativo) {
        return usuarioRepository.findByAtivo(ativo);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Usuário não encontrado")
                );
    }
}

