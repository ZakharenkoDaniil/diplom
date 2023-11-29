package com.mirea.diplom.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientProperty {
    private String uri;
    private String certPath;
    private String ottClientName;
    private String requestSchemePath;
    private String responseSchemePath;
}
