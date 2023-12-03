package com.mirea.diplom.service;

import com.mirea.diplom.client.IWebClient;
import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import org.springframework.http.HttpMethod;

public abstract class AbstractRequestService {

    private IWebClient webClient;

    public AbstractRequestService() {};

    public AbstractRequestService(IWebClient webClient) {
        this.webClient = webClient;
    }

    protected BaseResponse doRequest(BaseRequest request, HttpMethod method) {
        BaseResponse response = new BaseResponse();
        switch (method) {
            case POST:
                response = webClient.post(request);
                break;
            case GET:
                response = webClient.get(request);
                break;
            case PUT:
                response = webClient.put(request);
                break;
            case DELETE:
                response = webClient.delete(request);
                break;
        }
        return response;
    }
}
