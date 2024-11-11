package com.example.projetoavancadaweb.model;

import com.example.projetoavancadaweb.enuns.StatusRequisicao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "requisicao")
public class Requisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String questoes;
    private String resposta;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String usuarioUltimaAlteracao;

    @Enumerated(EnumType.STRING)
    private StatusRequisicao status;

    @ManyToOne
    private Usuario pai;  // Referência ao pai que fez a requisição
}
