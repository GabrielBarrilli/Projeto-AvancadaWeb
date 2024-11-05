package com.example.projetoavancadaweb.web.controller;

import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.service.AuthenticationService;
import com.example.projetoavancadaweb.service.UsuarioService;
import com.example.projetoavancadaweb.web.dto.request.CriarUsuarioRequest;
import com.example.projetoavancadaweb.web.dto.request.JwtTokenRequest;
import com.example.projetoavancadaweb.web.dto.request.LoginUsuarioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
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

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/usuarios/criar/{role}")
    public ResponseEntity<Void> salvarUsuario(@PathVariable Usuario.Role role, @Valid @RequestBody CriarUsuarioRequest createUserDto) {
        usuarioService.salvarUsuario(role, createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/usuarios/username/{username}")
    public ResponseEntity<Usuario> buscarUsuarioPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.buscarRolePorUsername(username);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/api/usuarios/email/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarRolePorEmail(email);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/api/usuarios/ativos")
    public ResponseEntity<List<Usuario>> buscarUsuariosAtivos() {
        List<Usuario> usuarios = usuarioService.buscarByAtivo(true);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/api/usuarios/getAll")
    public ResponseEntity<List<Usuario>> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/api/usuarios/porId/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}