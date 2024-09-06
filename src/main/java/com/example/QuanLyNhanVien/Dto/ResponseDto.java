package com.example.QuanLyNhanVien.Dto;

import lombok.Data;

@Data
public class ResponseDto <T>{

    private String message;
    private String messageCode;
    private T data;
    private int page;
    private long size;
    public ResponseDto(String message, String messageCode) {
        this.messageCode = messageCode;
        this.message = message;
    }
    public ResponseDto(T data, String message, String messageCode) {
        this.data = data;
        this.messageCode = messageCode;
        this.message = message;
    }

    public ResponseDto(T data, String message, String messageCode, int page, long size) {
        this.data = data;
        this.messageCode = messageCode;
        this.message = message;
        this.page = page;
        this.size = size;
    }

}
