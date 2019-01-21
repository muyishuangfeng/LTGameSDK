package com.gnetop.ltgamecommon.model;


public class WeChatPayModel  {

    private String appid;
    private String partnerid;
    private String packageX;
    private String noncestr;
    private String timestamp;
    private String prepayid;
    private String sign;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public String getPackageX() {
        return packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public String getSign() {
        return sign;
    }
}
