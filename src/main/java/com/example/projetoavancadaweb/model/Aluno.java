package com.example.projetoavancadaweb.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "aluno")
public class Aluno {

    private String nomeAluno;
    private LocalDate dataNascimento;
    private String grauParentesco;
    private String turma;
    private String anoLetivo;
}
