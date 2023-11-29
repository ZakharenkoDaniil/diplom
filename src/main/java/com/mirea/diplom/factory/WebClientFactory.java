package com.mirea.diplom.factory;

import com.mirea.diplom.config.ClientProperty;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Component
public class WebClientFactory {

    private WebClient.Builder webClientBuilder;

    public WebClientFactory(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    public WebClient getClient(ClientProperty property) {
        if (StringUtils.isNotBlank(property.getCertPath())) {
            return getClientWithSsl();
        }
        return webClientBuilder.build();
    }

    private WebClient getSimpleClient() {
        return webClientBuilder.build();
    }

    private WebClient getClientWithSsl() {
        try {
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
            HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
            return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
        } catch (SSLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
