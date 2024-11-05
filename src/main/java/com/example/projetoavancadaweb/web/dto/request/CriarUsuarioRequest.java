package com.example.projetoavancadaweb.web.dto.request;

import com.example.projetoavancadaweb.model.Endereco;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record CriarUsuarioRequest(
        // Criar usuário com username, usuario, senha e role
        String username,
        @Email
        String email,
        String password,

        // Criar pessoa com nome, cpf, data de nascimento, telefone, gênero e endereço
        String nome,
        @CPF
        String cpf,
        String dataNascimento,
        String telefone,
        String genero,

        // Criar endereço com estado, cidade, cep, logradouro, número, complemento e bairro
        Endereco endereco
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
