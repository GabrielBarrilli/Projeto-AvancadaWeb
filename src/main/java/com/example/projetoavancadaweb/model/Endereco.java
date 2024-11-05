package com.example.projetoavancadaweb.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "endereco")
public class Endereco {
    private String estado;
    private String cidade;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
}
