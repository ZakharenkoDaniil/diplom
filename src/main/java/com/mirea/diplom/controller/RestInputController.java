package com.mirea.diplom.controller;

import com.mirea.diplom.client.IWebClient;
import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RestInputController {

    private final Map<String, IWebClient> clientMap;

    public RestInputController(Map<String, IWebClient> clientMap) {
        this.clientMap = clientMap;
    }

    @PostMapping("/")
    public String postMappingExample(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleRequest(headers, request, HttpMethod.POST);
    }

    @GetMapping("/")
    public String getInputMapping(@RequestHeader Map<String, String> headers) {
        return handleRequest(headers, null, HttpMethod.GET);
    }

    @PutMapping("/")
    public String putInputMapping(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleRequest(headers, request, HttpMethod.PUT);
    }

    @DeleteMapping("/")
    public String deleteInputMapping(@RequestHeader Map<String, String> headers) {
        return handleRequest(headers, null, HttpMethod.DELETE);
    }

    public String handleRequest(Map<String, String> headers, String request, HttpMethod method) {
        String destinationUri = headers.get("Destination-Uri");
        IWebClient client = clientMap.get(destinationUri);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setHeaders(headers);
        baseRequest.setBody(request);
        BaseResponse response = new BaseResponse();
        switch (method) {
            case POST:
                response = client.post(baseRequest);
            case GET:
                response = client.get(baseRequest);
                break;
            case PUT:
                response = client.put(baseRequest);
                break;
            case DELETE:
                response = client.delete(baseRequest);
                break;
        }
        return response.getBody();
    }
}
