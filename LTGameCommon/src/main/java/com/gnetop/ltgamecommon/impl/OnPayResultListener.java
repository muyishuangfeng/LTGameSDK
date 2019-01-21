package com.gnetop.ltgamecommon.impl;


public interface OnPayResultListener<T> {

    public interface OnAliPayResultListener<T> extends OnPayResultListener<T> {
        T onSuccessResult(T t);

        void onFailed(Throwable ex);

        void onComplete();
    }

    public interface OnWeChatPayResultListener<T> extends OnPayResultListener<T> {
        T onSuccessResult(T t);

        void onFailed(Throwable ex);

        void onComplete();
    }
}
