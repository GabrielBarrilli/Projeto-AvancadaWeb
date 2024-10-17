package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.model.UserDetailsImpl;
import com.example.projetoavancadaweb.model.Usuario;
import com.example.projetoavancadaweb.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsServiceImplInterface {

    private final UsuarioRepository userRepository;

    public UserDetailsServiceImpl(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario modelUser = userRepository.findByEmail(username);
        return new UserDetailsImpl(modelUser);
    }
}

