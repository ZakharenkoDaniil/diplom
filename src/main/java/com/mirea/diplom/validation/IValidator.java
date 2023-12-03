package com.mirea.diplom.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mirea.diplom.entity.AbstractMessage;

public interface IValidator {
    void isValid(AbstractMessage message) throws ClassNotFoundException, JsonProcessingException;
}
