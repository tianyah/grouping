package com.example.tr.myapplication.fillteradapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by TR on 2018/1/24.
 * 过滤器
 */

public final class FilterFactory {
    private static Gson gson;

    public static Gson buildGson(){
        if (gson ==null){
            gson = new GsonBuilder()
                    .registerTypeAdapter(String.class,new StringDefaultAdapter())
                    .registerTypeAdapter(Integer.class,new integerDefaultAdapter())
                    .registerTypeAdapter(Double.class,new DoubleDefaultAdapter())
                    .registerTypeAdapter(Long.class,new LongleDefaultAdapter())
                    .create();
        }
        return gson;
    }


}
