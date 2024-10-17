package com.example.projetoavancadaweb.model.dto.request;

import com.example.projetoavancadaweb.model.Usuario;

public record CriarUsuarioRequest(
        String email,
        String password,
        Usuario.Role role
){
}
