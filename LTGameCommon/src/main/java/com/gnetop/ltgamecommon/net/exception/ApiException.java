package com.gnetop.ltgamecommon.net.exception;

/*
 * 项目名:    Pigeon
 * 包名       cn.hjtech.directory.common.retroft
 * 文件名:    ApiException
 * 创建者:    ZJB
 */
public class ApiException extends RuntimeException {

    private int code;


    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(String message) {
        super(new Throwable(message));

    }
}