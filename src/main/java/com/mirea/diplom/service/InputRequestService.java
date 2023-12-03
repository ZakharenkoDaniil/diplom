package com.mirea.diplom.service;

import com.mirea.diplom.client.IWebClient;
import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import com.mirea.diplom.handler.IHandler;
import com.mirea.diplom.validation.IValidator;
import org.springframework.http.HttpMethod;

import java.util.List;

public class InputRequestService extends AbstractRequestService{
    private List<IHandler> handlers;
    private List<IValidator> validators;

    public InputRequestService() {}

    public InputRequestService(List<IHandler> handlers, List<IValidator> validators, IWebClient webClient) {
        super(webClient);
        this.handlers = handlers;
        this.validators = validators;
    }

    public BaseResponse apply(BaseRequest request, HttpMethod method) {
        BaseResponse response = new BaseResponse();
        try {
            for (IValidator validator : validators) {
                validator.isValid(request);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return response;
        }
        response = doRequest(request, method);
        try {
            for (IValidator validator : validators) {
                validator.isValid(request);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            response = new BaseResponse();
        }

        return response;
    }
}
