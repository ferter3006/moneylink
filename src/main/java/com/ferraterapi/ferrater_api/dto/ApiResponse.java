package com.ferraterapi.ferrater_api.dto;


public class ApiResponse<T> {
    private int status;
    private String who;
    private String message;
    private T data;

    public ApiResponse(int status, String who, String message, T data) {
        this.status = status;
        this.who = who;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getWho() {
        return who;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

}
