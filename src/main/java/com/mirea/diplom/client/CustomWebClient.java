package com.mirea.diplom.client;

import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

public class CustomWebClient implements IWebClient{

    private final WebClient webClient;
    private final String uri;

    private final Long timeout;

    public CustomWebClient(WebClient webClient, String uri, Long timeout) {
        this.webClient = webClient;
        this.uri = uri;
        this.timeout = timeout;
    }

    @Override
    public BaseResponse post(BaseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::add);
        String responseAsString = webClient.post()
                .uri(uri)
                .body(BodyInserters.fromValue(request.getBody()))
                .headers(httpHeaders -> httpHeaders = headers)
                .retrieve().bodyToMono(String.class)
                .timeout(Duration.ofSeconds(timeout))
                .block();
        BaseResponse response = new BaseResponse();
        response.setBody(responseAsString);
        return response;
    }

    @Override
    public BaseResponse get(BaseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::add);
        String responseAsString = webClient.get()
                .uri(uri)
                .headers(httpHeaders -> httpHeaders = headers)
                .retrieve().bodyToMono(String.class)
                .timeout(Duration.ofSeconds(timeout))
                .block();
        BaseResponse response = new BaseResponse();
        response.setBody(responseAsString);
        return response;
    }

    @Override
    public BaseResponse put(BaseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::add);
        String responseAsString = webClient.put()
                .uri(uri)
                .body(BodyInserters.fromValue(request.getBody()))
                .headers(httpHeaders -> httpHeaders = headers)
                .retrieve().bodyToMono(String.class)
                .timeout(Duration.ofSeconds(timeout))
                .block();
        BaseResponse response = new BaseResponse();
        response.setBody(responseAsString);
        return response;
    }

    @Override
    public BaseResponse delete(BaseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::add);
        String responseAsString = webClient.delete()
                .uri(uri)
                .headers(httpHeaders -> httpHeaders = headers)
                .retrieve().bodyToMono(String.class)
                .timeout(Duration.ofSeconds(timeout))
                .block();
        BaseResponse response = new BaseResponse();
        response.setBody(responseAsString);
        return response;
    }
}
