package com.example.tr.myapplication.callback;


import com.example.tr.myapplication.model.ApiResponse;

public enum APISuccessCallback implements APIExceptionCallBack {

      INSTANCE;

    @Override
    public String callback(ApiResponse baseResponse) {
        return null;
    }
}
