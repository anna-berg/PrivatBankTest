package com.example.privatBank.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.stereotype.Service;


import javax.persistence.AttributeConverter;
import java.util.HashMap;

@Service

public class HashMapConverter implements AttributeConverter<HashMap<String, Object>, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(HashMap<String, Object> attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public HashMap<String, Object> convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, HashMap.class);
    }
}
