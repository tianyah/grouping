package com.example.tr.myapplication.factory;


import com.example.tr.myapplication.model.ApiResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class JsonArrayParesTransformer<T> implements ObservableTransformer<ApiResponse<String>, List<T>> {
    private Class<T> zClass;

    public JsonArrayParesTransformer(Class<T> zClass) {
        this.zClass = zClass;
    }

    @Override
    public ObservableSource<List<T>> apply(Observable<ApiResponse<String>> upstream) {
        return upstream.compose(new NetWorkTransformer<String>())
                .observeOn(Schedulers.computation())
                .flatMap(new Function<String, ObservableSource<List<T>>>() {
                    @Override
                    public ObservableSource<List<T>> apply(String s) throws Exception {
                        ParameterTypeImpl parameterType = new ParameterTypeImpl(List.class, zClass);
                        List<T> list = JSONFactory.fromJson(s, parameterType);
                        return Observable.just(list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
