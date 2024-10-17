package com.example.projetoavancadaweb.jwt;

import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarRolePorUsername(username);
        return new JwtUserDetails(usuario); // Certifique-se de que está chamando a classe correta
    }

    public JwtToken getTokenAuthenticated(String username) {
        Usuario role = usuarioService.buscarRolePorUsername(username);
        return JwtUtils.createToken(username, role.getRole().substring("ROLE_".length()));
    }
}
