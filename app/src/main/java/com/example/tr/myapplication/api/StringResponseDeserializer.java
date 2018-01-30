package com.example.tr.myapplication.api;


import com.example.tr.myapplication.model.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class StringResponseDeserializer implements JsonDeserializer<ApiResponse<String>> {
    private Gson gson = new Gson();

        @Override
    public ApiResponse<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ApiResponse<String> baseResponse = new ApiResponse<>();
        if (json.isJsonObject()) {
            JsonObject asJsonObject = json.getAsJsonObject();
            JsonElement data = asJsonObject.get("data");
            JsonElement code = asJsonObject.get("code");
            JsonElement msg = asJsonObject.get("msg");

            baseResponse.setCode(code.getAsInt());
            baseResponse.setMessage(msg.getAsString() == null ? "" : msg.getAsString());
            if (data != null) {
                if (data.isJsonArray() || data.isJsonObject()) {
                    String s = gson.toJson(data);
                    baseResponse.setData(s);
                    return baseResponse;
                } else if (data.isJsonNull()) {
                    //data为null,不做处理
                } else {
                    baseResponse.setData(data.getAsString());
                }
            }
        }
        return baseResponse;
        }
}