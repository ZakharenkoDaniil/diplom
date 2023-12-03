package com.mirea.diplom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirea.diplom.client.CustomWebClient;
import com.mirea.diplom.client.IWebClient;
import com.mirea.diplom.factory.JsonSchemaToPojoFactory;
import com.mirea.diplom.factory.WebClientFactory;
import com.mirea.diplom.handler.IHandler;
import com.mirea.diplom.service.OutputRequestService;
import com.mirea.diplom.utils.ServiceUtils;
import com.mirea.diplom.validation.IValidator;
import com.mirea.diplom.validation.JsonSchemeValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.*;

import static com.mirea.diplom.utils.Constants.EXTENSION_DELIMITER;
import static com.mirea.diplom.utils.Constants.PATH_DELIMITER;

@Configuration
public class Config {

//    @Bean
//    public ObjectMapper yamlObjectMapper() {
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        return mapper;
//    }

//    @Bean
//    public Properties properties(ObjectMapper mapper) {
//        Properties properties = new Properties();
//        try {
//            properties = mapper.readValue(new File("src/main/resources/client-properties.yaml"), Properties.class);
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//        return properties;
//    }

    @Bean
    public Properties getProperties() {
        Properties properties = new Properties();
        ClientProperty clientProperty = new ClientProperty("localhost:8081/", null, null, "src/main/resources/json/requestScheme.json", "src/main/resources/json/responseScheme.json");
        List<ClientProperty> listClientProperties = new ArrayList<>();
        listClientProperties.add(clientProperty);
        properties.setClientProperties(listClientProperties);
        try {
            generatePojos(properties);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return properties;
    }
    @Bean
    public Map<String, OutputRequestService> getServiceRequestMap(Properties properties, WebClientFactory webClientFactory) {
        Map<String, OutputRequestService> serviceRequestMap = new HashMap<>();
        properties.getClientProperties().forEach(clientProperty -> {
            IWebClient customWebClient = new CustomWebClient(webClientFactory.getClient(clientProperty), clientProperty.getUri(), 5l);
            List<IValidator> validators = new ArrayList<>();
            String responseScheme = clientProperty.getResponseSchemePath();
            if (StringUtils.isNotBlank(responseScheme)) {
                String className = ServiceUtils.getClassNameFromPath(responseScheme);
                JsonSchemeValidator jsonSchemeValidator = new JsonSchemeValidator(className, new ObjectMapper());
                validators.add(jsonSchemeValidator);
            }
            List<IHandler> handlers = new ArrayList<>();
            OutputRequestService outputRequestService = new OutputRequestService(handlers, validators, customWebClient);
            serviceRequestMap.put(clientProperty.getUri(), outputRequestService);
        });
        return serviceRequestMap;
    }


    public void generatePojos(Properties properties) throws IOException {
        List<ClientProperty> clientProperties = properties.getClientProperties();
        for (ClientProperty clientProperty : clientProperties) {
            String requestSchemePath = clientProperty.getRequestSchemePath();
            String responseSchemePath = clientProperty.getResponseSchemePath();
            if (StringUtils.isNotBlank(requestSchemePath)) {
                String className = StringUtils.substringAfterLast(requestSchemePath, PATH_DELIMITER);
                className = StringUtils.substringBefore(className, EXTENSION_DELIMITER);
                JsonSchemaToPojoFactory.convertJsonToJavaClass(requestSchemePath, className);
            }
            if (StringUtils.isNotBlank(responseSchemePath)) {
                String className = StringUtils.substringAfterLast(responseSchemePath, PATH_DELIMITER);
                className = StringUtils.substringBefore(className, EXTENSION_DELIMITER);
                JsonSchemaToPojoFactory.convertJsonToJavaClass(responseSchemePath, className);
            }
        }
    }
}
