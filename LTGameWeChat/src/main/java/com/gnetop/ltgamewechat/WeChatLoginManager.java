package com.gnetop.ltgamewechat;

import android.content.Context;

import com.gnetop.ltgamecommon.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WeChatLoginManager {

    private static WeChatLoginManager sInstance;
    private Context mContext;
    private String appSecret;
    private String baseUrl;
    private String LTAppID;
    private String LTAppKey;
    private String appID;

    private WeChatLoginManager(Context context) {
        this.mContext = context;
    }

    /**
     * 单例
     *
     * @param context
     * @return
     */
    public static WeChatLoginManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WeChatLoginManager.class) {
                if (sInstance == null) {
                    sInstance = new WeChatLoginManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 微信登录
     */
    public void weChatLogin(String appID, String appSecret, String baseUrl, String LTAppID,
                            String LTAppKey) {
        initWeChat(appID);
        this.appID = appID;
        this.appSecret = appSecret;
        this.baseUrl = baseUrl;
        this.LTAppID = LTAppID;
        this.LTAppKey = LTAppKey;
    }

    /**
     * 微信登录
     */
    private void initWeChat(String appID) {
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, null);
        // 将该app注册到微信
        api.registerApp(appID);
        if (!api.isWXAppInstalled()) {
            ToastUtils.getInstance().shortToast(mContext, R.string.wechat_not_install);
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "ltgame_sdk_wechat";
            api.sendReq(req);
        }
    }

    /**
     * 回调
     */
    public IWXAPI handleAPI() {
        if (appID != null) {
            return WXAPIFactory.createWXAPI(mContext,appID, false);
        } else {
            return null;
        }
    }

    public String getAppID() {
        return appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getLTAppID() {
        return LTAppID;
    }

    public String getLTAppKey() {
        return LTAppKey;
    }
}
