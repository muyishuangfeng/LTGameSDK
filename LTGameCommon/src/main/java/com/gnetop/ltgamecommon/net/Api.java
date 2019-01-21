package com.gnetop.ltgamecommon.net;


import com.gnetop.ltgamecommon.net.retrofit.BaseApiImpl;

public class Api extends BaseApiImpl {

    private static final String BASE_URL="http://sdk.aktgo.com";

   // private static Api api = new Api(BASE_URL);

    public Api(String baseUrl) {
        super(baseUrl);
    }

    public static RetrofitService getInstance(String url) {
        return new Api(url).getRetrofit().create(RetrofitService.class);
    }


}
