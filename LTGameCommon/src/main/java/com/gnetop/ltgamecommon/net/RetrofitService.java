package com.gnetop.ltgamecommon.net;


import com.gnetop.ltgamecommon.model.AliPayBean;
import com.gnetop.ltgamecommon.model.BaseEntry;
import com.gnetop.ltgamecommon.model.ResultData;
import com.gnetop.ltgamecommon.model.WeChatBean;
import com.gnetop.ltgamecommon.model.WeChatModel;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {

    /**
     * 支付支付宝支付
     *
     * @param map
     * @return
     */
    @POST("/")
    Observable<AliPayBean> aliPay(@QueryMap WeakHashMap<String, String> map);

    /**
     * 微信支付
     *
     * @param map
     * @return
     */
    @POST("/")
    Observable<WeChatBean> weChatPay(@QueryMap WeakHashMap<String, String> map);

    /**
     * 微信登录
     *
     * @return
     */
    @POST
    Observable<WeChatModel> getWeChatInfo(@Query("appid") String appid, @Query("secret") String secret,
                                          @Query("code") String code, @Query("grant_type") String grant_type);


    /**
     * 获取验证码
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @GET("/api/auth/send-code")
    Observable<BaseEntry<ResultData>> getAuthenCode(@Header("LT-AppID") String LTAppID,
                                                    @Header("LT-Token") String LTToken,
                                                    @Header("LT-T") int LTTime,
                                                    @Body Map<String, Object> map);


    /**
     * 注册
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/auth/register-phone")
    Observable<BaseEntry<ResultData>> register(@Header("LT-AppID") String LTAppID,
                                               @Header("LT-Token") String LTToken,
                                               @Header("LT-T") int LTTime,
                                               @Body Map<String, Object> map);

    /**
     * 获取设备信息
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/other/join")
    Observable<BaseEntry<ResultData>> getDeviceInfo(@Header("LT-AppID") String LTAppID,
                                                    @Header("LT-Token") String LTToken,
                                                    @Header("LT-T") int LTTime,
                                                    @Body Map<String, Object> map);

    /**
     * 登录
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/auth/login-phone")
    Observable<BaseEntry<ResultData>> login(@Header("LT-AppID") String LTAppID,
                                            @Header("LT-Token") String LTToken,
                                            @Header("LT-T") int LTTime,
                                            @Body Map<String, Object> map);

    /**
     * 更改密码
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/auth/reset-password")
    Observable<BaseEntry<ResultData>> updatePassword(@Header("LT-AppID") String LTAppID,
                                                     @Header("LT-Token") String LTToken,
                                                     @Header("LT-T") int LTTime,
                                                     @Body Map<String, Object> map);

    /**
     * google登录
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/auth/login-google")
    Observable<BaseEntry<ResultData>> googleLogin(@Header("LT-AppID") String LTAppID,
                                                  @Header("LT-Token") String LTToken,
                                                  @Header("LT-T") int LTTime,
                                                  @Body Map<String, Object> map);

    /**
     * facebook登录
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/auth/login-facebook")
    Observable<BaseEntry<ResultData>> faceBookLogin(@Header("LT-AppID") String LTAppID,
                                                    @Header("LT-Token") String LTToken,
                                                    @Header("LT-T") int LTTime,
                                                    @Body Map<String, Object> map);

    /**
     * 微信登录
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/auth/login-wechat")
    Observable<BaseEntry<ResultData>> weChatLogin(@Header("LT-AppID") String LTAppID,
                                                  @Header("LT-Token") String LTToken,
                                                  @Header("LT-T") int LTTime,
                                                  @Body Map<String, Object> map);

    /**
     * qq登录
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/auth/login-qq")
    Observable<BaseEntry<ResultData>> qqLogin(@Header("LT-AppID") String LTAppID,
                                              @Header("LT-Token") String LTToken,
                                              @Header("LT-T") int LTTime,
                                              @Body Map<String, Object> map);


    /**
     * 创建订单
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/p/order")
    Observable<BaseEntry<ResultData>> createOrder(@Header("LT-AppID") String LTAppID,
                                                  @Header("LT-Token") String LTToken,
                                                  @Header("LT-T") int LTTime,
                                                  @Body RequestBody requestBody);

    /**
     * google支付
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/p/google")
    Observable<BaseEntry<ResultData>> googlePay(@Header("LT-AppID") String LTAppID,
                                                @Header("LT-Token") String LTToken,
                                                @Header("LT-T") int LTTime,
                                                @Body RequestBody requestBody);

    /**
     * onestore支付
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/api/p/one-store")
    Observable<BaseEntry<ResultData>> oneStorePay(@Header("LT-AppID") String LTAppID,
                                                  @Header("LT-Token") String LTToken,
                                                  @Header("LT-T") int LTTime,
                                                  @Body RequestBody requestBody);


}
