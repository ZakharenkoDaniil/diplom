package com.mirea.diplom.client;

import com.mirea.diplom.entity.BaseRequest;
import com.mirea.diplom.entity.BaseResponse;

public interface IWebClient {
    BaseResponse post(BaseRequest request);
    BaseResponse get(BaseRequest request);
    BaseResponse put(BaseRequest request);
    BaseResponse delete(BaseRequest request);
}
