package com.example.projetoavancadaweb.controller;

import com.example.projetoavancadaweb.model.dto.request.CriarUsuarioRequest;
import com.example.projetoavancadaweb.model.dto.request.JwtTokenRequest;
import com.example.projetoavancadaweb.model.dto.request.LoginUsuarioRequest;
import com.example.projetoavancadaweb.service.AuthenticationService;
import com.example.projetoavancadaweb.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> salvarUsuario(@RequestBody CriarUsuarioRequest createUserDto) {
        usuarioService.salvarUsuario(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}