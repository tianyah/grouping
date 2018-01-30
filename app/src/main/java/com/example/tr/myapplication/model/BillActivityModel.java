package com.example.tr.myapplication.model;

import com.example.tr.myapplication.api.Api;
import com.example.tr.myapplication.api.RetrofitHttp;
import com.example.tr.myapplication.factory.JsonParesTransformer;
import com.example.tr.myapplication.factory.NetWorkTransformer;
import com.example.tr.myapplication.presenter.BaseModel;

import io.reactivex.Observable;

/**
 * Created by TR on 2018/1/28.
 */

public class BillActivityModel implements BaseModel {
    /**
     * 只需要几代码,这是不需要data的请求，比如只需要知道code 1 或者不为1的情况
     * 这里url 和是否请求带参数 || 是否需要data数据
     * @return
     */
    public Observable<String> getData() {
        return RetrofitHttp
                .getApi()
                .get(Api.Test)
                .compose(new NetWorkTransformer());
    }

}
