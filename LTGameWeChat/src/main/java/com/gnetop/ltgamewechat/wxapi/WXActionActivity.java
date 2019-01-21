package com.gnetop.ltgamewechat.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.gnetop.ltgamecommon.base.BaseResult;
import com.gnetop.ltgamecommon.impl.OnLoginSuccessListener;
import com.gnetop.ltgamecommon.login.LoginBackManager;
import com.gnetop.ltgamecommon.model.BaseEntry;
import com.gnetop.ltgamecommon.model.Event;
import com.gnetop.ltgamecommon.model.ResultData;
import com.gnetop.ltgamecommon.util.EventUtils;
import com.gnetop.ltgamewechat.WeChatLoginManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXActionActivity extends Activity implements IWXAPIEventHandler {

    private String appSecret;
    private String baseUrl;
    private String LTAppID;
    private String LTAppKey;
    private String appID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("weChat", "onCreate");
        IWXAPI api = WeChatLoginManager.getInstance(this).handleAPI();
        appSecret = WeChatLoginManager.getInstance(this).getAppSecret();
        appID = WeChatLoginManager.getInstance(this).getAppID();
        baseUrl = WeChatLoginManager.getInstance(this).getBaseUrl();
        LTAppID = WeChatLoginManager.getInstance(this).getLTAppID();
        LTAppKey = WeChatLoginManager.getInstance(this).getLTAppKey();
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("weChat", appSecret+"===="+appID+"=="+baseUrl+"==="+LTAppID+"=="+LTAppKey);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e("weChat", "onReq");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("weChat", baseResp.toString() + "==" + baseResp.errStr + "==" + baseResp.errCode + "==" +
                baseResp.openId + "==" + baseResp.getType());
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {//微信支付回调
            EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_COMMAND_PAY_BY_WX));
        }
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_SUCCESS));
                String code = ((SendAuth.Resp) baseResp).code;
                //获取用户信息
                getAccessToken(code);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_ERR_AUTH_DENIED));
                Log.e("weChat", "========用户拒绝授权");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_ERR_USER_CANCEL));
                Log.e("weChat", "==========用户取消");
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_ERR_UNSUPPORT));
                Log.e("weChat", "==========不支持");
                break;
            case BaseResp.ErrCode.ERR_BAN:
                EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_ERR_BAN));
                Log.e("weChat", "==========ERR_BAN");
                break;
            case BaseResp.ErrCode.ERR_COMM://没有权限
                EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_ERR_COMM));
                Log.e("weChat", "==========ERR_COMM");
                break;
            default:
                break;
        }
    }

    /**
     * 获取信息
     *
     * @param code
     */
    private void getAccessToken(String code) {
        if (!TextUtils.isEmpty(appID) &&
                !TextUtils.isEmpty(appSecret) &&
                !TextUtils.isEmpty(code) &&
                !TextUtils.isEmpty(baseUrl) &&
                !TextUtils.isEmpty(LTAppID) &&
                !TextUtils.isEmpty(LTAppKey)) {
            LoginBackManager.getAccessToken(this, appID,
                    appSecret, code, baseUrl, LTAppID,
                    LTAppKey, new OnLoginSuccessListener() {
                        @Override
                        public void onSuccess(BaseEntry<ResultData> result) {
                            Log.e("weChat", result.getMsg());
                            EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_SUCCESS, result));
                        }

                        @Override
                        public void onFailed(Throwable ex) {
                            EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_FAILED, ex));
                            Log.e("weChat", "onFailed==" + ex.getMessage());
                        }

                        @Override
                        public void onComplete() {

                            Log.e("weChat", "onComplete");
                        }

                        @Override
                        public void onParameterError(String result) {
                            EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_ERR_PARAMETER, result));
                            Log.e("weChat", "onParameterError===" + result);
                        }

                        @Override
                        public void onError(String error) {
                            EventUtils.sendEvent(new Event<>(BaseResult.MSG_RESULT_ERROR, error));
                            Log.e("weChat", "onError===" + error);
                        }
                    });
        }
    }


}
