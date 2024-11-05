package com.example.projetoavancadaweb.security;

import com.example.projetoavancadaweb.jwt.JwtUserDetailsService;
import com.example.projetoavancadaweb.model.UserDetailsImpl;
import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UsuarioAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret-key}")
    private String secret_Key;

    private final JwtUserDetailsService jwtTokenService;

    private final UsuarioRepository userRepository;

    public UsuarioAuthenticationFilter(JwtUserDetailsService jwtTokenService, UsuarioRepository userRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    public String getSubjectFromToken(String token) {
        // Extrai as reivindicações (claims) do token
        Claims claims = Jwts.parser()
                .setSigningKey(secret_Key)
                .parseClaimsJws(token)
                .getBody();

        // Retorna o valor do 'sub'
        return claims.getSubject();
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (verificaEndpointsPublicos(request)) {
            String token = recuperaToken(request);
            if (token != null) {
                var username = getSubjectFromToken(token);

                String subject = String.valueOf(jwtTokenService.getTokenAuthenticated(username));
                Usuario modelUser = userRepository.findByUsername(subject);
                UserDetailsImpl modelUserDetails = new UserDetailsImpl(modelUser);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                modelUserDetails.getUsername(),
                                null,
                                modelUserDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean verificaEndpointsPublicos(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList("/api/usuarios/login", "/api/usuarios").contains(requestURI);
    }

    private String recuperaToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}