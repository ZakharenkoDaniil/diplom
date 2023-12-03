package com.mirea.diplom.service;

import com.mirea.diplom.client.IWebClient;
import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;
import com.mirea.diplom.handler.IHandler;
import com.mirea.diplom.validation.IValidator;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutputRequestService extends AbstractRequestService {
    private List<IHandler> handlers;
    private List<IValidator> validators;
    private IWebClient webClient;

    public OutputRequestService() {}

    public OutputRequestService(List<IHandler> handlers, List<IValidator> validators, IWebClient webClient) {
        super(webClient);
        this.handlers = handlers;
        this.validators = validators;
    }

    public BaseResponse apply(BaseRequest request, HttpMethod method) {
        for (IHandler handler : handlers) {
            handler.handle(request);
        }
        BaseResponse response = doRequest(request, method);
        try {
            for (IValidator validator : validators) {
                validator.isValid(response);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            response = new BaseResponse();
        }

        return response;
    }

}
