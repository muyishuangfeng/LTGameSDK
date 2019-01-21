package com.gnetop.ltgamecommon.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.gnetop.ltgamecommon.R;
import com.gnetop.ltgamecommon.impl.OnCreateOrderListener;
import com.gnetop.ltgamecommon.impl.OnGooglePayResultListener;
import com.gnetop.ltgamecommon.impl.OnLoginSuccessListener;
import com.gnetop.ltgamecommon.impl.OnPayResultedListener;
import com.gnetop.ltgamecommon.impl.onOneStoreUploadListener;
import com.gnetop.ltgamecommon.model.AliPayBean;
import com.gnetop.ltgamecommon.model.BaseEntry;
import com.gnetop.ltgamecommon.model.ResultData;
import com.gnetop.ltgamecommon.model.WeChatBean;
import com.gnetop.ltgamecommon.model.WeChatModel;
import com.gnetop.ltgamecommon.net.Api;
import com.gnetop.ltgamecommon.util.MD5Util;
import com.google.gson.Gson;

import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * 手机登录方法
 */
public class LoginBackManager {

    /**
     * 获取验证码
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  appKey
     * @param params    手机号
     * @param mListener 接口回调
     */
    public static void getAuthenticationCode(Context context, String baseUrl, String LTAppID, String LTAppKey,
                                             Map<String, Object> params,
                                             final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                params != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("GET" + LTAppID + LTTime + LTAppKey);
            Api.getInstance(baseUrl)
                    .getAuthenCode(LTAppID, LTToken, (int) LTTime, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * 注册
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     *                  //@param phone     手机号
     *                  //@param auth_code 验证码
     *                  //@param password  密码
     * @param mListener 接口回调
     */
    public static void register(Context context, String baseUrl, String LTAppID, String LTAppKey,
                                Map<String, Object> params,
                                final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                params != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);

            Api.getInstance(baseUrl)
                    .register(LTAppID, LTToken, (int) LTTime, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * 登录
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  appKey
     *                  //@param phone     手机号
     *                  //@param password  密码
     * @param mListener 接口回调
     */
    public static void login(Context context, String baseUrl, String LTAppID, String LTAppKey,
                             Map<String, Object> map,
                             final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                map != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);

            Api.getInstance(baseUrl)
                    .login(LTAppID, LTToken, (int) LTTime, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }

                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * 更新密码
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  APPkey
     *                  //@param phone     手机号
     *                  //@param auth_code 验证码
     *                  //@param password  密码
     * @param mListener 接口回调
     */
    public static void updatePassword(Context context, String baseUrl, String LTAppID, String LTAppKey,
                                      Map<String, Object> map,
                                      final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                map != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);

            Api.getInstance(baseUrl)
                    .updatePassword(LTAppID, LTToken, (int) LTTime, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * 获取设备信息
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  appKey
     *                  //@param platform   平台
     *                  //@param adid       设备唯一广告标识符
     *                  //@param package_id 包ID
     *                  //@param device     设备
     *                  //@param system     系统
     *                  //@param district   地区
     *                  //@param lang       语言
     * @param mListener 接口回调
     */
    public static void getDeviceInfo(Context context, String baseUrl, String LTAppID, String LTAppKey, Map<String, Object> map,
                                     final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                map != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);

            Api.getInstance(baseUrl)
                    .getDeviceInfo(LTAppID, LTToken, (int) LTTime,
                            map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * Google登录
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  appKey
     *                  //@param accessToken google返回的Token
     *                  //@param platform    平台
     * @param mListener 接口回调
     */
    public static void googleLogin(Context context, String baseUrl, String LTAppID,
                                   String LTAppKey, Map<String, Object> map,
                                   final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                map != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);

            Api.getInstance(baseUrl)
                    .googleLogin(LTAppID, LTToken, (int) LTTime, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * facebook登录
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  Token
     *                  //@param accessToken facebook返回的Token
     * @param mListener 接口回调
     */
    public static void facebookLogin(Context context, String baseUrl, String LTAppID,
                                     String LTAppKey, Map<String, Object> map,
                                     final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                map != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);

            Api.getInstance(baseUrl)
                    .faceBookLogin(LTAppID, LTToken, (int) LTTime, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {

                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * 微信登录
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  appKEY
     *                  //@param accessToken 微信返回的Token
     * @param mListener 接口回调
     */
    private static void weChatLogin(Context context, String baseUrl, String LTAppID,
                                    String LTAppKey, Map<String, Object> map,
                                    final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                map != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);
            Api.getInstance(baseUrl)
                    .weChatLogin(LTAppID, LTToken, (int) LTTime, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * QQ登录
     *
     * @param baseUrl   服务器地址
     * @param LTAppID   APPID
     * @param LTAppKey  appKey
     *                  //@param accessToken qq的Token
     *                  //@param openID      qq的openID
     * @param mListener 接口回调
     */
    public static void qqLogin(Context context, String baseUrl, String LTAppID,
                               String LTAppKey, Map<String, Object> map,
                               final OnLoginSuccessListener mListener) {
        if (!TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey) &&
                map != null) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);

            Api.getInstance(baseUrl)
                    .qqLogin(LTAppID, LTToken, (int) LTTime, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                if (TextUtils.equals(result.getResult(), "OK")) {
                                    if (mListener != null) {
                                        mListener.onSuccess(result);
                                    }
                                } else if (TextUtils.equals(result.getResult(), "NO")) {
                                    if (mListener != null) {
                                        mListener.onError(result.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onComplete();
                            }
                        }
                    });
        } else {
            if (mListener != null) {
                mListener.onParameterError(context.getResources().getString(R.string.text_parameter_error));
            }
        }
    }

    /**
     * 获取token
     *
     * @param appID     appID
     * @param appSecret appSecret
     * @param code      请求码
     */
    public static void getAccessToken(final Context context, String appID, String appSecret,
                                      String code, final String baseUrl, final String LTAppID,
                                      final String LTAppKey,
                                      final OnLoginSuccessListener listener) {
        Api.getInstance("https://api.weixin.qq.com/sns/oauth2/access_token")
                .getWeChatInfo(appID, appSecret, code,
                        "authorization_code")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatModel token) {
                        if (token != null && !TextUtils.isEmpty(token.getToken())) {
                            Map<String, Object> map = new WeakHashMap<>();
                            map.put("accessToken", token.getToken());
                            LoginBackManager.weChatLogin(context, baseUrl, LTAppID, LTAppKey,
                                    map, listener);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 支付宝支付
     *
     * @param url
     */
    public static void aliPay(String url, WeakHashMap<String, String> map, final OnPayResultedListener mListener) {
        Api.getInstance(url)
                .aliPay(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AliPayBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AliPayBean aliPayBean) {
                        if (aliPayBean != null) {
                            if (mListener != null) {
                                mListener.onAliPayResult(aliPayBean);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mListener != null) {
                            mListener.onPayError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mListener != null) {
                            mListener.onPayComplete();
                        }
                    }
                });
    }

    /**
     * 微信支付
     *
     * @param url
     */
    public static void weChatPay(String url, WeakHashMap<String, String> map,
                                 final OnPayResultedListener mListener) {
        Api.getInstance(url)
                .weChatPay(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatBean aliPayBean) {
                        if (aliPayBean != null) {
                            if (mListener != null) {
                                mListener.onWeChatPayResult(aliPayBean);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mListener != null) {
                            mListener.onPayError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mListener != null) {
                            mListener.onPayComplete();
                        }
                    }
                });
    }


    /**
     * 创建订单
     */
    public static void createOrder(String baseUrl, String LTAppID, String LTAppKey,
                                   Map<String, Object> params,
                                   final OnCreateOrderListener mListener) {
        Log.e("GooglePayActivity", "start");
        if (params != null &&
                !TextUtils.isEmpty(baseUrl) &&
                !TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey)) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);
            String json = new Gson().toJson(params);//要传递的json
            final RequestBody requestBody = RequestBody
                    .create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
            Api.getInstance(baseUrl)
                    .createOrder(LTAppID, LTToken, (int) LTTime, requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            Log.e("GooglePayActivity", result.getMsg() + "===" +
                                    result.getData().getLt_order_id() + "===" + result.getResult() + "==="
                                    + result.getCode());
                            if (result.getCode() == 200) {
                                if (result.getData().getLt_order_id() != null) {
                                    mListener.onOrderSuccess(result.getData().getLt_order_id());
                                }
                            } else {
                                mListener.onOrderError(result.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("GooglePayActivity", e.getMessage());
                            mListener.onOrderFailed(e);
                        }

                        @Override
                        public void onComplete() {
                            Log.e("GooglePayActivity", "onComplete");
                        }
                    });
        }
    }

    /**
     * google支付
     */
    public static void googlePay(String baseUrl, String LTAppID, String LTAppKey,
                                 Map<String, Object> params,
                                 final OnGooglePayResultListener mListener) {
        if (params != null &&
                !TextUtils.isEmpty(baseUrl) &&
                !TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey)) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);
            String json = new Gson().toJson(params);//要传递的json
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
            Api.getInstance(baseUrl)
                    .googlePay(LTAppID, LTToken, (int) LTTime, requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                mListener.onPaySuccess(result.getMsg());
                                Log.e("GooglePayActivity", result.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onPayFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (mListener != null) {
                                mListener.onPayComplete();
                            }
                        }
                    });
        }
    }

    /**
     * oneStore支付
     */
    public static void oneStorePay(String baseUrl, String LTAppID, String LTAppKey,
                                   Map<String, Object> params,
                                   final onOneStoreUploadListener mListener) {
        if (params != null &&
                !TextUtils.isEmpty(baseUrl) &&
                !TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey)) {
            long LTTime = System.currentTimeMillis() / 1000L;
            String LTToken = MD5Util.md5Decode("POST" + LTAppID + LTTime + LTAppKey);
            String json = new Gson().toJson(params);//要传递的json
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType
                    .parse("application/json; charset=utf-8"), json);
            Api.getInstance(baseUrl)
                    .oneStorePay(LTAppID, LTToken, (int) LTTime, requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseEntry<ResultData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntry<ResultData> result) {
                            if (result != null) {
                                mListener.onOneStoreUploadSuccess(result.getCode());
                                Log.e("OneStore", result.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mListener != null) {
                                mListener.onOneStoreUploadFailed(e);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

}
