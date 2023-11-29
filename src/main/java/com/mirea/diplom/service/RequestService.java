package com.mirea.diplom.service;

import com.mirea.diplom.client.IWebClient;
import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import com.mirea.diplom.handler.IHandler;
import com.mirea.diplom.validation.IValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    private List<IHandler> handlers;

    private List<IValidator> validators;
    private IWebClient webClient;

    public RequestService(List<IHandler> handlers, List<IValidator> validators ,IWebClient webClient) {
        this.handlers = handlers;
        this.validators = validators;
        this.webClient = webClient;
    }

//    public BaseResponse apply(BaseRequest request) {
//        for (IHandler handler : handlers) {
//            handler.handle(request);
//        }
//        BaseResponse response = webClient.send(request);
//        for (IValidator validator : validators) {
//            validator.isValid(request);
//        }
//        return response;
//    }

}
