package com.example.projetoavancadaweb.service;

import com.example.projetoavancadaweb.util.UsuarioConverter;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class ValidacaoService {

    private final UsuarioConverter usuarioConverter;

    public ValidacaoService(UsuarioConverter usuarioConverter) {
        this.usuarioConverter = usuarioConverter;
    }

    public void validarRoleByToken(List<String> roles, String token) throws AccessDeniedException {
        var usuarioToken = usuarioConverter.convert(token);

        if (!roles.contains(usuarioToken.role())) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
    }
}
