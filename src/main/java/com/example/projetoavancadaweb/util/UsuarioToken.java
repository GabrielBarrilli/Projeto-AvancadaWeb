package com.example.projetoavancadaweb.util;

public record UsuarioToken(
        String iss,
        Integer iat,
        Integer exp,
        String role,
        String sub
) {
}
