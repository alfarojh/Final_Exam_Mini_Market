package com.example.mini_market_wgs.models;

public class ApiResponse {
    private String message;   // Pesan yang akan ditampilkan dalam bentuk JSON
    private Object data;            // Objek yang akan ditampilkan dalam bentuk JSON

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
