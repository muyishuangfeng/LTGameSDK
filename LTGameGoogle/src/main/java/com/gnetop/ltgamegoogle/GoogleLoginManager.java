package com.gnetop.ltgamegoogle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.gnetop.ltgamecommon.impl.OnLoginSuccessListener;
import com.gnetop.ltgamecommon.login.LoginBackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Map;
import java.util.WeakHashMap;

public class GoogleLoginManager {

    private static final int RC_SIGN_IN = 0x0001;
    private static GoogleApiClient mGoogleApiClient;

    /**
     * google登录
     *
     * @param context
     * @param server_client_id
     */
    public static void googleLogin(Activity context, String server_client_id) {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(server_client_id)
                .build();

        mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addConnectionCallbacks(mCallBack)
                .addOnConnectionFailedListener(mListener)
                .enableAutoManage((FragmentActivity) context, mListener)/* FragmentActivity *//* OnConnectionFailedListener */
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * 回调
     */
    static GoogleApiClient.ConnectionCallbacks mCallBack = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            if (bundle != null) {
                Log.e("GoogleLogin", "onConnected" + bundle.toString());
            } else {
                Log.e("GoogleLogin", "onConnected");
            }
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };
    /**
     * 连接失败
     */
    static GoogleApiClient.OnConnectionFailedListener mListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.e("GoogleLogin", "连接失败" + connectionResult.getErrorMessage());
        }
    };


    private static void handleSignInResult(Context context, GoogleSignInResult result, String url,
                                           String LTAppID, String LTAppKey,
                                           OnLoginSuccessListener mListener) {
        Log.e("googlLogin", "robinhandleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            Log.e("googlLogin", "robin成功");
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                Log.e("googleLogin", "robin用户名是:" + acct.getDisplayName());
                Log.e("googleLogin", "robin用户email是:" + acct.getEmail());
                Log.e("googleLogin", "robin用户头像是:" + acct.getPhotoUrl());
                Log.e("googleLogin", "robin用户Id是:" + acct.getId());//之后就可以更新UI了
                Log.e("googleLogin", "robin用户IdToken是:" + acct.getIdToken());
                Log.e("googleLogin", "robin用户IdToken是:" + acct.getServerAuthCode());
                Map<String,Object>map=new WeakHashMap<>();
                map.put("access_token",acct.getIdToken());
                map.put("platform",2);
                LoginBackManager.googleLogin(context, url, LTAppID,
                        LTAppKey, map, mListener);
            }
        } else {
            mListener.onError(result.getStatus().toString());
            Log.e("googleLogin", "robin没有成功" + result.getStatus());
        }
    }

    /**
     * 设置登录结果回调
     *
     * @param data 数据
     */
    public static void onGoogleResult(int requestCode, Intent data, Context context, String url,
                                      String LTAppID, String LTAppKey,
                                      OnLoginSuccessListener mListener) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("googleLogin","回调");
            handleSignInResult(context, result, url, LTAppID, LTAppKey,   mListener);
        }
    }

    /**
     * 断开连接
     */
    public static void stopConnection(Context context) {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage((FragmentActivity) context);
            mGoogleApiClient.disconnect();
            mGoogleApiClient=null;
        }
    }
}
