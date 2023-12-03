package com.mirea.diplom.controller;

import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import com.mirea.diplom.service.InputRequestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.mirea.diplom.utils.Constants.URI_HEADER;

@RestController("/input")
public class InputRestController {

    private final Map<String, InputRequestService> requestServiceMap;
    private final Map<String, InputRequestService> secureRequestServiceMap;
    private final Map<String, InputRequestService> secureJwtRequestServiceMap;

    public InputRestController(@Qualifier("inputRequestServiceMap") Map<String, InputRequestService> requestServiceMap,
                               @Qualifier("inputSecureRequestServiceMap") Map<String, InputRequestService> secureRequestServiceMap,
                               @Qualifier("inputSecureJwtRequestServiceMap") Map<String, InputRequestService> secureJwtRequestServiceMap) {
        this.requestServiceMap = requestServiceMap;
        this.secureRequestServiceMap = secureRequestServiceMap;
        this.secureJwtRequestServiceMap = secureJwtRequestServiceMap;
    }

    @PostMapping("/")
    public String postMappingExample(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleRequest(headers, request, HttpMethod.POST);
    }

    @PostMapping("/secure/")
    public String postSecureMappingExample(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleSecureRequest(headers, request, HttpMethod.POST);
    }

    @PostMapping("/secure/jwt/")
    public String postSecureJwtMappingExample(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleSecureJwtRequest(headers, request, HttpMethod.POST);
    }

    @GetMapping("/")
    public String getInputMapping(@RequestHeader Map<String, String> headers) {
        return handleRequest(headers, null, HttpMethod.GET);
    }

    @GetMapping("/secure/")
    public String getSecurityInputMapping(@RequestHeader Map<String, String> headers) {
        return handleSecureRequest(headers, null, HttpMethod.GET);
    }

    @GetMapping("/secure/jwt/")
    public String getSecureJwtInputMapping(@RequestHeader Map<String, String> headers) {
        return handleSecureJwtRequest(headers, null, HttpMethod.GET);
    }

    @PutMapping("/")
    public String putInputMapping(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleRequest(headers, request, HttpMethod.PUT);
    }

    @PutMapping("/secure/")
    public String putSecureInputMapping(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleSecureRequest(headers, request, HttpMethod.PUT);
    }

    @PutMapping("/secure/jwt/")
    public String putSecureJwtInputMapping(@RequestHeader Map<String, String> headers, @RequestBody String request) {
        return handleSecureJwtRequest(headers, request, HttpMethod.PUT);
    }

    @DeleteMapping("/")
    public String deleteInputMapping(@RequestHeader Map<String, String> headers) {
        return handleRequest(headers, null, HttpMethod.DELETE);
    }

    @DeleteMapping("/secure/")
    public String deleteSecureInputMapping(@RequestHeader Map<String, String> headers) {
        return handleSecureRequest(headers, null, HttpMethod.DELETE);
    }

    @DeleteMapping("/secure/jwt/")
    public String deleteSecureJwtInputMapping(@RequestHeader Map<String, String> headers) {
        return handleSecureJwtRequest(headers, null, HttpMethod.DELETE);
    }

    public String handleRequest(Map<String, String> headers, String request, HttpMethod method) {
        return baseHandleRequest(headers, request, method, requestServiceMap);
    }

    public String handleSecureRequest(Map<String, String> headers, String request, HttpMethod method) {
        return baseHandleRequest(headers, request, method, secureRequestServiceMap);
    }

    public String handleSecureJwtRequest(Map<String, String> headers, String request, HttpMethod method) {
        return baseHandleRequest(headers, request, method, secureJwtRequestServiceMap);
    }

    public String baseHandleRequest(Map<String, String> headers, String request, HttpMethod method, Map<String, InputRequestService> currentRequestServiceMap) {
        String destinationUri = headers.get(URI_HEADER);
        InputRequestService inputRequestService = currentRequestServiceMap.get(destinationUri);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setHeaders(headers);
        baseRequest.setBody(request);
        BaseResponse response = inputRequestService.apply(baseRequest, method);
        return response.getBody();
    }
}
