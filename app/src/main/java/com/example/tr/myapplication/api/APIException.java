package com.example.tr.myapplication.api;

import com.example.tr.myapplication.model.ApiResponse;

/**
 * 自定义异常，当接口返回的code不为200时，需要抛出此异常
 * eg：登陆时验证码错误；参数为传递等
 */
public class APIException extends Exception {
    private int code;
    private String msg;
    private ApiResponse mBaseResponse;

    public APIException(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public APIException(ApiResponse baseResponse) {
        this.mBaseResponse = baseResponse;
        this.code = baseResponse.getCode();
        this.msg = baseResponse.getMessage();
    }

    public int getCode() {
        return code;
    }

    public ApiResponse getBaseResponse() {
        return mBaseResponse;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}

