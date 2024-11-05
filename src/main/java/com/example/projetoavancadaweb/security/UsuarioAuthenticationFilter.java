package com.example.projetoavancadaweb.security;

import com.example.projetoavancadaweb.jwt.JwtUserDetailsService;
import com.example.projetoavancadaweb.jwt.JwtUtils;
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
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String extractSub(String token) {
        // Divide o token em partes
        String[] parts = token.split("\\.");

        // O payload é a segunda parte do token (índice 1)
        if (parts.length < 2) {
            throw new IllegalArgumentException("Token JWT inválido");
        }

        // Decodifica o payload de Base64 para String
        String payloadJson = new String(Base64.getDecoder().decode(parts[1]));

        // Usa uma expressão regular para encontrar o valor de "sub"
        Pattern pattern = Pattern.compile("\"sub\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(payloadJson);

        if (matcher.find()) {
            return matcher.group(1);  // Retorna o valor encontrado
        } else {
            throw new IllegalArgumentException("\"sub\" não encontrado no payload");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (verificaEndpointsPublicos(request)) {
            String token = recuperaToken(request);
            if (token != null) {

               // String username = JwtUtils.getUsernameFromToken(token);
                var username = extractSub(token);
                String subject = String.valueOf(jwtTokenService.getTokenAuthenticated(username));
                Usuario modelUser = userRepository.findByUsername(username);
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