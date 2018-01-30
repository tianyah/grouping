package com.example.tr.myapplication.callback;

import com.example.tr.myapplication.model.ApiResponse;

public interface APIExceptionCallBack {
    /**
     * @param baseResponse 网络请求数据
     * @return error 消息
     */
    String callback(ApiResponse baseResponse);
}
