package com.example.projetoavancadaweb.web.dto.request;

import com.example.projetoavancadaweb.model.Endereco;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record CriarUsuarioRequest(
        // Criar usuário com email, senha e role
        String email,
        String password,

        // Criar pessoa com nome, cpf, data de nascimento, endereço, telefone e gênero
        String nome,
        String cpf,
        String dataNascimento,
        Endereco endereco,
        String telefone,
        String genero,

        // Criar endereço com estado, cidade, cep, logradouro, número, complemento e bairro
        String estado,
        String cidade,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro
) {
    // Converter a string dataNascimento para LocalDate no formato dd/MM/yyyy
    public LocalDate getDataNascimentoAsLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(dataNascimento, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data de nascimento no formato inválido: " + dataNascimento);
        }
    }
}
