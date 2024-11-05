package com.example.projetoavancadaweb.repository;

import com.example.projetoavancadaweb.model.Atualiza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtualizaRepository extends JpaRepository<Atualiza, Long> {
}