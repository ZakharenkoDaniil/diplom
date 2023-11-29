package com.mirea.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractMessage {

    protected Map<String, String> headers;
    protected String body;
    protected String bodyType;

}
