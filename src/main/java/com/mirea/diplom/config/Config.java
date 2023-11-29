package com.mirea.diplom.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mirea.diplom.client.CustomWebClient;
import com.mirea.diplom.client.IWebClient;
import com.mirea.diplom.factory.WebClientFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    @Bean
    public ObjectMapper yamlObjectMapper() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public Properties properties(@Qualifier("yamlObjectMapper") ObjectMapper mapper) {
        Properties properties = new Properties();
        try {
            properties = mapper.readValue(new File("src/main/resources/client-properties.yaml"), Properties.class);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return properties;
    }
    @Bean
    public Map<String, IWebClient> clientMap(Properties properties, WebClientFactory webClientFactory) {
        Map<String, IWebClient> clientMap = new HashMap<>();
        properties.getClientProperties().forEach(clientProperty -> {
            IWebClient customWebClient = new CustomWebClient(webClientFactory.getClient(clientProperty), clientProperty.getUri(), 5l);
            clientMap.put(clientProperty.getUri(), customWebClient);
        });
        return clientMap;
    }
}
