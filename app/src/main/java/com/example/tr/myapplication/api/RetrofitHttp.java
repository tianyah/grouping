package com.example.tr.myapplication.api;

import android.app.Application;
import android.text.TextUtils;

import com.example.tr.myapplication.MyAPP;
import com.example.tr.myapplication.factory.StringConverterFactory;
import com.example.tr.myapplication.fillteradapter.FilterFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ${creators} M X
 * on ${project}.procurement
 */

public class RetrofitHttp {
    private static final int DEFAULT_TIMEOUT = 60;
    private static final int DEFAULT_READ_TIME_OUT = 30;
    //服务器地址
    private static String API_HOST;
    private Retrofit mRetrofit;

    private static RetrofitHttp mInstance;
    private static final Map<Class<ApiService>, Object> apis = new HashMap<Class<ApiService>, Object>();
    //设缓存有效期为1天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=";


    /**
     * 私有构造方法
     */
    private RetrofitHttp() {

        // 指定缓存路径,缓存大小100Mb
//        Cache cache = new Cache(new File(MyAPP.getContext().getCacheDir(), "HttpCache"),
//                1024 * 1024 * 100);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)   //连接超时时间
                .writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.MINUTES)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(com.example.tr.myapplication.factory.GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(FilterFactory.buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_HOST)
                .build();


    }

    public static synchronized void init(String baseUrl, Application context) {
        if (TextUtils.isEmpty(baseUrl)) {
            return;
        }
        API_HOST = baseUrl;
        apis.clear();
    }

    /**
     * 单例
     *
     * @return
     */
    public static RetrofitHttp getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitHttp.class) {
                mInstance = new RetrofitHttp();
            }
        }
        return mInstance;
    }

    public static ApiService getApi() {
        return RetrofitHttp.getJApi(ApiService.class);
    }

    private static <C> C getJApi(Class<ApiService> s) {
        Object o = apis.get(s);

        if (o == null) {
            o = getInstance().create(s);
            apis.put(s, o);
        }
        return (C) o;
    }

    private <T> T create(Class<T> services) {
        return mRetrofit.create(services);
    }



}
