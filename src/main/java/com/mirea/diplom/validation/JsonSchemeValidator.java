package com.mirea.diplom.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirea.diplom.entity.AbstractMessage;
import org.springframework.stereotype.Component;

@Component
public class JsonSchemeValidator implements IValidator {

    private String className;
    private ObjectMapper objectMapper;

    public JsonSchemeValidator() {};

    public JsonSchemeValidator(String className, ObjectMapper objectMapper) {
        this.className = className;
        this.objectMapper = objectMapper;
    }
    @Override
    public void isValid(AbstractMessage message) throws ClassNotFoundException, JsonProcessingException {
        objectMapper.readValue(message.getBody(), Class.forName(className));
    }
}
