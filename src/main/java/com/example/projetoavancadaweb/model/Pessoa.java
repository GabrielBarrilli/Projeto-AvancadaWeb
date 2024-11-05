package com.example.projetoavancadaweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    @Embedded
    private Endereco endereco;
    private String telefone;
    private String genero;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
