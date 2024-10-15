package com.example.projetoavancadaweb.repository;

import com.example.projetoavancadaweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

    Usuario findByEmail(String emailUsuario);
}
