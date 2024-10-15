package com.example.projetoavancadaweb.model;

import com.example.projetoavancadaweb.enuns.StatusRequisicao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requisicao")
public class Requisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario pai;  // Referência ao pai que fez a requisição

    private String titulo;  // Título da requisição
    private String descricao;  // Descrição detalhada
    private LocalDateTime dataCriacao;  // Data de criação
    private LocalDateTime dataAtualizacao;  // Data da última atualização

    @Enumerated(EnumType.STRING)
    private StatusRequisicao status;

}
