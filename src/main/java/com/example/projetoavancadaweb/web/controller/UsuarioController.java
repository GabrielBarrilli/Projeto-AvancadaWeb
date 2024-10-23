package com.example.projetoavancadaweb.web.controller;

import com.example.projetoavancadaweb.model.Role;
import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.service.AuthenticationService;
import com.example.projetoavancadaweb.service.UsuarioService;
import com.example.projetoavancadaweb.web.dto.request.CriarUsuarioRequest;
import com.example.projetoavancadaweb.web.dto.request.JwtTokenRequest;
import com.example.projetoavancadaweb.web.dto.request.LoginUsuarioRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final AuthenticationService authenticationService;

    public UsuarioController(UsuarioService usuarioService, AuthenticationService authenticationService) {
        this.usuarioService = usuarioService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenRequest> loginUsuario(@RequestBody LoginUsuarioRequest loginUserDto) {
        JwtTokenRequest token = authenticationService.autenticarUsuario(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> salvarUsuario(Usuario.Role role, @RequestBody CriarUsuarioRequest createUserDto) {
        usuarioService.salvarUsuario(role, createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> buscarUsuarioPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.buscarRolePorUsername(username);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarRolePorEmail(email);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Usuario>> buscarUsuariosAtivos() {
        List<Usuario> usuarios = usuarioService.buscarByAtivo(true);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Usuario>> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}