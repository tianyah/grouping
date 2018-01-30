package com.example.tr.myapplication.fillteradapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by TR on 2018/1/24.
 */

public class StringDefaultAdapter implements JsonSerializer<String>,JsonDeserializer<String> {

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsString().equals("")  || json.getAsString() == null){
                return "";
            }
        }catch (Exception e){
            throw new RuntimeException("发生了未知的异常");
        }
        try {
            return json.getAsString();
        }catch (NullPointerException e){
            throw new NullPointerException("服务器后台那智障返回了null过来");
        }
    }

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
