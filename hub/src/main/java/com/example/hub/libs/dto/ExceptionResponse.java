package com.example.hub.libs.dto;

import lombok.Getter;

@Getter
public record ExceptionResponse<T>(
    Integer status,
    String message,
    T data
) {
    public static <T> ExceptionResponse<T> of(Integer status, String message, T data) {
        return new ExceptionResponse<>(status, message, data);
    }
}