package com.mirea.diplom.controller;

import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import com.mirea.diplom.service.OutputRequestService;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.mirea.diplom.utils.Constants.URI_HEADER;

@RestController("/output")
public class OutputRestController {

    private final Map<String, OutputRequestService> requestServiceMap;

    public OutputRestController(Map<String, OutputRequestService> requestServiceMap) {
        this.requestServiceMap = requestServiceMap;
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
        String destinationUri = headers.get(URI_HEADER);
        OutputRequestService outputRequestService = requestServiceMap.get(destinationUri);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setHeaders(headers);
        baseRequest.setBody(request);
        BaseResponse response = outputRequestService.apply(baseRequest, method);
        return response.getBody();
    }
}
