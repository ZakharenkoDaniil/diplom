package com.mirea.diplom.validation;

import com.mirea.diplom.entity.AbstractMessage;

public interface IValidator {
    void isValid(AbstractMessage message);
}
