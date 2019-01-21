package com.gnetop.ltgamecommon.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Silence on 2018/1/18.
 */

public class WeChatBean {


    /**
     * appid : wxb4ba3c02aa476ea1
     * partnerid : 1900006771
     * package : Sign=WXPay
     * noncestr : 702cddc696eecd8b58a8f7f3875f074d
     * timestamp : 1516259411
     * prepayid : wx20180118151011a99500f8d30099939326
     * sign : 992D3622BBF87D6F929702A7C8334ABB
     */

    private String appid;
    private String partnerid;
    @SerializedName("package")
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
