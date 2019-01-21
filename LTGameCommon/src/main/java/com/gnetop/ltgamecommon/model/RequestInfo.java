package com.gnetop.ltgamecommon.model;

public class RequestInfo {
    // 每个应用对应的appid
    String LTAppID;
    //接口加密验证
    String LTToken;
    // Unix时间戳
    String LTT;
    // 当前应用包ID,和后台对应
    String package_id;
    //商品ID,后台配置
    int gid;
    //自定义数据
    String[] custom;

    public String getLTAppID() {
        return LTAppID;
    }

    public void setLTAppID(String LTAppID) {
        this.LTAppID = LTAppID;
    }

    public String getLTToken() {
        return LTToken;
    }

    public void setLTToken(String LTToken) {
        this.LTToken = LTToken;
    }

    public String getLTT() {
        return LTT;
    }

    public void setLTT(String LTT) {
        this.LTT = LTT;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String[] getCustom() {
        return custom;
    }

    public void setCustom(String[] custom) {
        this.custom = custom;
    }


    public RequestInfo(String LTAppID, String LTToken, String LTT, String package_id, int gid, String[] custom) {
        this.LTAppID = LTAppID;
        this.LTToken = LTToken;
        this.LTT = LTT;
        this.package_id = package_id;
        this.gid = gid;
        this.custom = custom;
    }
}
