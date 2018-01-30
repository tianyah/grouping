package com.example.tr.myapplication.api;

import com.example.tr.myapplication.model.ApiResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 统一访问接口
 */


public interface ApiService {

    /*
      这些是 返回的data是 {}  jsonObject类型
     */

    /**
     * 不需要请求头Post请求&不需要传递参数 一般用在登录
     *
     * @return
     */
    @POST
    Observable<ApiResponse<String>> BasePost(
            @Url String url,
            @Body SimpleParams params
    );


    //测试
    @GET
    Observable<String> get(
            @Url String url
    );

    /**
     * 需要请求头并且需要传递参数
     * @param url
     * @param headerMaps
     * @param params
     * @return
     */
    @POST
    Observable<ApiResponse<String>> BasePost(
            @Url String url,
            @HeaderMap SimpleParams headerMaps,
            @Body SimpleParams params
    );

    /**
     * 需要请求头不需要传递参数
     * @param url
     * @param headerMaps
     * @return
     */
    @POST
    Observable<ApiResponse<String>> Post(
            @Url String url,
            @HeaderMap SimpleParams headerMaps
    );


    @GET
    Observable<ApiResponse<String>> BaseGet(
            @Url String UrL,
            @HeaderMap SimpleParams headerMap
    );

    /**
     * 需要请求头get请求&需要传递参数
     *
     * @return
     */
    @GET
    Observable<ApiResponse<String>> BaseGet(
            @Url String url,
            @HeaderMap SimpleParams headerMap,
            @QueryMap SimpleParams map
    );

    /**
     * 需要请求头get请不需要传递参数
     *
     * @return
     */
    @GET
    Observable<ApiResponse<String>> Get(
            @Url String url,
            @HeaderMap SimpleParams headerMap
    );

    /**
     * 请求数组
     *
     * @return
     */

    @GET
    Observable<ApiResponse<String>> getArr(
            @HeaderMap SimpleParams headerMap,
            @QueryMap SimpleParams map
            , @Query("type[]") Integer[] ids
    );

    /**
     * 下载
     */
    @Streaming
    @GET
    Observable<ResponseBody> GetDown(@Url String url);

    /**
     * 下载
     */
    @POST
    Observable<ResponseBody> PostDown(@Url String url);



    // -------------------------------------下面的是 jsonArr类型
    /**
     * 当返回的data 是[]  jsonArr
     * @param headerMap
     * @return
     */
    @GET
    Observable<ApiResponse<List<String>>> BaseListGet(@Url String url, @HeaderMap SimpleParams headerMap);


    /**
     * 很少用
     * @param UrL
     * @param map
     * @return
     */
    @GET
    Observable<List<String>> ListGet(
            @Url String UrL,
            @QueryMap SimpleParams map
    );

    /**
     * 需要请求头get请求&需要传递参数
     *
     * @return
     */
    @GET
    Observable<List<String>> BaseListGet(
            @Url String url,
            @HeaderMap SimpleParams headerMap,
            @QueryMap SimpleParams map
    );

    /**
     * 不需要请求头Post请求&不需要传递参数
     *
     * @return
     */
    @POST
    Observable<List<String>> ListPost(
            @Url String url,
            @HeaderMap SimpleParams headerMaps
    );

    @POST
    Observable<List<String>> BaseListPost(
            @Url String url,
            @Body SimpleParams params
    );


    @POST
    Observable<List<String>> BaseListPost(
            @Url String url,
            @HeaderMap SimpleParams headerMaps,
            @Body SimpleParams params
    );






    /**
     * 请求数组
     *
     * @return
     */

    @GET
    Observable<List<String>> getListArr(
            @HeaderMap SimpleParams headerMap,
            @QueryMap SimpleParams map
            , @Query("type[]") Integer[] ids
    );


}
