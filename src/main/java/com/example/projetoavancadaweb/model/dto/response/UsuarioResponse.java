package com.example.projetoavancadaweb.model.dto.response;

import com.example.projetoavancadaweb.model.Usuario;

public record UsuarioResponse (
        Long id,
        String email,
        Usuario.Role role
){
}