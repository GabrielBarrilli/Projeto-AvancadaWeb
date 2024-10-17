package com.example.projetoavancadaweb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsServiceImplInterface {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
