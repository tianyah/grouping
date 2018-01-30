package com.example.tr.myapplication.factory;

import com.example.tr.myapplication.api.StringResponseDeserializer;
import com.example.tr.myapplication.model.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class JSONFactory {
    private static Type type = new TypeToken<ApiResponse<String>>() {
    }.getType();


    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(type, new StringResponseDeserializer())
            .create();

    private JSONFactory() {

    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String string, Class<T> tClass) {
        return gson.fromJson(string, tClass);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }



}
