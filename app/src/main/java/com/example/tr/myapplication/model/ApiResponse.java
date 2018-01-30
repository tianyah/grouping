package com.example.tr.myapplication.model;

/**
 * Created by TR on 2018/1/27.
 */

public class ApiResponse<D> {
    private int code;

    private String msg;

    private D data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public void setData(D data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public D getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
