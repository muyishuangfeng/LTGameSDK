package com.gnetop.ltgamecommon.net.retrofit;


public class ApiException extends RuntimeException {

    private int code;


    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        code = errorCode;
    }

    public ApiException(String message) {
        super(new Throwable(message));

    }
}