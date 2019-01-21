package com.gnetop.ltgamecommon.model;

public class ResultData {
    //验证码
    private String auth_code;
    //乐推UID
    private String lt_uid;
    //token
    private String api_token;
    //乐推订单ID
    private String lt_order_id;


    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getLt_uid() {
        return lt_uid;
    }

    public void setLt_uid(String lt_uid) {
        this.lt_uid = lt_uid;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getLt_order_id() {
        return lt_order_id;
    }

    public void setLt_order_id(String lt_order_id) {
        this.lt_order_id = lt_order_id;
    }
}
