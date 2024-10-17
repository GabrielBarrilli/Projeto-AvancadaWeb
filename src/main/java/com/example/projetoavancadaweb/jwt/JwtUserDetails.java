package com.example.projetoavancadaweb.jwt;

import com.example.projetoavancadaweb.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {
    private final Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.valueOf(usuario.getRole())));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    public Usuario.Role getRole() {
        return usuario.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implementar conforme necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implementar conforme necessário
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implementar conforme necessário
    }

    @Override
    public boolean isEnabled() {
        return true; // Implementar conforme necessário
    }
}
