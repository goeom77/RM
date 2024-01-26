package org.gyu.solution.global.base;

public record Response<T>  (
        T data,
        String message,
        String code
){

    public static <T> Response<T> ofSuccess(T data) {
        return new Response<>(data, "성공", "S001");
    }
    public static <T> Response<T> ofSuccess() {
        return new Response<>(null, "성공", "S001");
    }
}