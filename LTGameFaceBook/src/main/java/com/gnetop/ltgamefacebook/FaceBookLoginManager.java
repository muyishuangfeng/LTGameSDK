package com.gnetop.ltgamefacebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.gnetop.ltgamecommon.impl.OnLoginSuccessListener;
import com.gnetop.ltgamecommon.login.LoginBackManager;

import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;


public class FaceBookLoginManager {

    private static CallbackManager mFaceBookCallBack;
    private static FaceBookLoginManager sInstance;

    private FaceBookLoginManager() {

    }

    /**
     * 单例
     *
     * @return
     */
    public static FaceBookLoginManager getInstance() {
        if (sInstance == null) {
            synchronized (FaceBookLoginManager.class) {
                if (sInstance == null) {
                    sInstance = new FaceBookLoginManager();

                }
            }
        }
        return sInstance;
    }


    /**
     * 开始
     *
     * @param context
     */
    public void faceBookLogin(Context context) {
        LoginManager.getInstance()
                .logInWithReadPermissions((Activity) context,
                        Arrays.asList("public_profile"));
    }


    /**
     * 初始化
     */
    public void initFaceBook(final Context context, final String baseUrl,
                              final String LTAppID, final String LTAppKey,
                              final OnLoginSuccessListener mListener) {
        mFaceBookCallBack = CallbackManager.Factory.create();
        if (mFaceBookCallBack != null) {
            LoginManager.getInstance().registerCallback(mFaceBookCallBack,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            if (loginResult != null) {
                                Map<String,Object>map=new WeakHashMap<>();
                                map.put("access_token",loginResult.getAccessToken().getToken());
                                LoginBackManager.facebookLogin(context, baseUrl, LTAppID, LTAppKey,
                                        map, mListener);
                            }

                        }

                        @Override
                        public void onCancel() {
                            mListener.onError("onCancel");
                            Log.e("facebook", "onCancel");
                        }

                        @Override
                        public void onError(FacebookException error) {
                            mListener.onError(error.toString());
                            Log.e("facebook", "onError");
                        }
                    });

        }
    }


    /**
     * 设置登录结果回调
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    public void setOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFaceBookCallBack != null) {
            mFaceBookCallBack.onActivityResult(requestCode, resultCode, data);
        }
    }
}
