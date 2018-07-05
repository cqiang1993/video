package com.cq.video.entity.RespData;

public class RespError<T> {

    private T detail;

    private String message;

    private String code;

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
