package com.example.tr.myapplication.factory;


import com.example.tr.myapplication.model.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class JsonParesTransformer<T> implements ObservableTransformer<ApiResponse<String>, T> {
    private Class<T> zClass;

    public JsonParesTransformer(Class<T> zClass) {
        this.zClass = zClass;
    }

    @Override
    public ObservableSource<T> apply(Observable<ApiResponse<String>> upstream) {
        return upstream.compose(new NetWorkTransformer<String>())
                .observeOn(Schedulers.computation())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String s) throws Exception {
//                        ParameterTypeImpl parameterType = new ParameterTypeImpl(List.class, zClass);
//                        List<T> list =
                        return Observable.just(JSONFactory.fromJson(s, zClass));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
