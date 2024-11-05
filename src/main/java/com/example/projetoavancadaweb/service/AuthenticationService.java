package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.model.UserDetailsImpl;
import com.example.projetoavancadaweb.web.dto.request.JwtTokenRequest;
import com.example.projetoavancadaweb.web.dto.request.LoginUsuarioRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService; // Adicione esta linha

    public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService; // Inicialize o UserDetailsService
    }

    public JwtTokenRequest autenticarUsuario(LoginUsuarioRequest loginUserDto) {
        UserDetailsImpl modelUserDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginUserDto.usuario());

        // Cria um token de autenticação com o username e a senha fornecida
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(modelUserDetails.getUsername(), loginUserDto.password());

        // Realiza a autenticação
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Gera e retorna o token JWT
        return new JwtTokenRequest(jwtTokenService.generateToken(modelUserDetails));
    }

}
