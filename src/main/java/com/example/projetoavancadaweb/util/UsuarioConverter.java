package com.example.projetoavancadaweb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class UsuarioConverter implements Converter<String, UsuarioToken> {

    @Override
    public UsuarioToken convert(String token) {
        String[] pieces = token.split("\\.");
        String jsonString;
        try {
            jsonString = new String(Base64.decodeBase64(pieces[1]), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            return objectMapper.readValue(jsonString, UsuarioToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
