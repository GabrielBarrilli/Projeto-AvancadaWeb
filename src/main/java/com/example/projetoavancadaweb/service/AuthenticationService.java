package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.model.UserDetailsImpl;
import com.example.projetoavancadaweb.model.dto.request.JwtTokenRequest;
import com.example.projetoavancadaweb.model.dto.request.LoginUsuarioRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public JwtTokenRequest autenticarUsuario(LoginUsuarioRequest loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl modelUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenRequest(jwtTokenService.generateToken(modelUserDetails));
    }
}
