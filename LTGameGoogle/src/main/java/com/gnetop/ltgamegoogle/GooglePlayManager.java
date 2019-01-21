package com.gnetop.ltgamegoogle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gnetop.ltgamecommon.impl.OnCreateOrderListener;
import com.gnetop.ltgamecommon.impl.OnGoogleInitListener;
import com.gnetop.ltgamecommon.impl.OnGooglePayResultListener;
import com.gnetop.ltgamecommon.impl.OnGoogleResultListener;
import com.gnetop.ltgamecommon.login.LoginBackManager;
import com.gnetop.ltgamecommon.model.GoogleModel;
import com.gnetop.ltgamegoogle.util.IabHelper;
import com.gnetop.ltgamegoogle.util.IabResult;
import com.gnetop.ltgamegoogle.util.Inventory;
import com.gnetop.ltgamegoogle.util.Purchase;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class GooglePlayManager {
    private static final String TAG = GooglePlayManager.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static IabHelper mHelper;
    private static String payload;

    /**
     * 初始化google支付
     */
    public static void initGooglePay(Context context, String publicKey, final String url,
                                     final String LTAppID, final String LTAppKey,
                                     final String packageId, final Map<String, Object> params,
                                     final OnGoogleInitListener mListener) {
        //创建谷歌支付帮助类
        mHelper = new IabHelper(context, publicKey);
        mHelper.enableDebugLogging(true);
        /**
         * 初始化和连接谷歌服务
         */
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    mListener.onGoogleInitFailed(result.getMessage());
                } else {
                    mListener.onGoogleInitSuccess("init Success");
                    getLTOrderID(url, LTAppID, LTAppKey,packageId, params);
                }
            }
        });
    }

    /**
     * 查询是否有未消费的商品
     *
     * @param context      上下文
     * @param requestCode 请求码
     * @param goodsList    商品集合
     * @param productID    内购产品唯一id, 填写你自己添加的内购商品id
     * @param mListener    回调
     */
    public static void checkUnConsume(final Context context, final int requestCode, List<String> goodsList,
                                      final String productID, final OnGooglePayResultListener mListener) {
        try {
            List<String> subSku = new ArrayList<>();
            mHelper.queryInventoryAsync(true, goodsList, subSku,
                    new IabHelper.QueryInventoryFinishedListener() {
                        @Override
                        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                            if (result != null) {
                                if (result.isSuccess() && inv.hasPurchase(productID)) {
                                    //消费, 并下一步, 这里Demo里面我没做提示,将购买了,但是没消费掉的商品直接消费掉, 正常应该
                                    //给用户一个提示,存在未完成的支付订单,是否完成支付
                                    consumeProduct(context, inv.getPurchase(productID),
                                            false, "Consumption success",
                                            "Consumption failed");
                                } else {
                                    buyProduct((Activity) context, requestCode, productID, mListener);
                                }
                            }
                        }

                    });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费掉已购买商品
     *
     * @param purchase
     * @param needNext
     * @param tipmsg1
     * @param tipmsg2
     */
    private static void consumeProduct(final Context context, Purchase purchase, final boolean needNext,
                                       final String tipmsg1, final String tipmsg2) {
        try {
            mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener() {
                @Override
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                    if (mHelper == null) {
                        return;
                    }
                    if (result.isSuccess()) {
                        if (!needNext) {
                            //处理内购中断的情况, 仅仅只是消费掉上一次未正常完成的商品
                            Toast.makeText(context, tipmsg1, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, tipmsg2, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }


    /**
     * 产品购买
     *
     * @param context      上下文
     * @param REQUEST_CODE 请求码
     * @param SKU          内购产品唯一id, 填写你自己添加的内购商品id
     * @param mListener    回调监听
     */
    private static void buyProduct(final Activity context, int REQUEST_CODE,
                                   final String SKU, final OnGooglePayResultListener mListener) {
        if (!TextUtils.isEmpty(payload)) {
            try {
                mHelper.launchPurchaseFlow(context, SKU, REQUEST_CODE, new IabHelper.OnIabPurchaseFinishedListener() {
                    @Override
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        if (result.isFailure()) {
                            Toast.makeText(context, "Purchase Failed", Toast.LENGTH_SHORT).show();
                            mListener.onPayError(result.getMessage());
                            return;
                        }
                        mListener.onPaySuccess("Purchase successful");
                        if (purchase.getSku().equals(SKU)) {
                            //购买成功，调用消耗
                            consumeProduct(context, purchase, false, "Payment success",
                                    "Payment Failed");
                        }
                    }
                }, payload);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        } else {
            mListener.onPayError("Order creation failed");
            Toast.makeText(context, "Order creation failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 释放资源
     */
    public static void release() {
        /**
         * 释放掉资源
         */
        if (mHelper != null) {
            try {
                mHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
        mHelper = null;
    }


    public static void onActivityResult(int requestCode, Intent data, int selfRequestCode, final String url,
                                        final String LTAppID, final String LTAppKey, OnGoogleResultListener
                                                mListener) {
        /**
         * 将回调交给帮助类来处理, 否则会出现支付正在进行的错误
         */
        if (mHelper == null) return;
        if (requestCode == selfRequestCode) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            //订单信息
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
            if (!TextUtils.isEmpty(purchaseData)) {
                GoogleModel googleModel = new Gson().fromJson(purchaseData, GoogleModel.class);
                Log.e(TAG, googleModel.getPurchaseToken());
                Map<String, Object> params = new WeakHashMap<>();
                params.put("purchase_token", googleModel.getPurchaseToken());
                params.put("lt_order_id", payload);
                uploadToServer(url, LTAppID, LTAppKey, params, mListener);
            }
        }

    }

    /**
     * 获取乐推订单ID
     *
     * @param url       url
     * @param LTAppID   appID
     * @param LTAppKey  appKey
     * @param packageId 应用包名
     * @param params    集合
     */
    private static void getLTOrderID(String url, String LTAppID, String LTAppKey,
                                     String packageId, Map<String, Object> params) {
        Map<String, Object> map = new WeakHashMap<>();
        map.put("package_id", packageId);
        map.put("gid", "4");
        map.put("custom", params);
        LoginBackManager.createOrder(url, LTAppID,
                LTAppKey, map, new OnCreateOrderListener() {
                    @Override
                    public void onOrderSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            payload = result;
                        } else {
                            Log.e(TAG, "ltOrderID is null");
                        }
                    }

                    @Override
                    public void onOrderFailed(Throwable ex) {
                        Log.e(TAG, ex.getMessage());
                    }

                    @Override
                    public void onOrderError(String error) {
                        Log.e(TAG, error);
                    }
                });
    }

    private static void uploadToServer(final String url,
                                       final String LTAppID,
                                       final String LTAppKey,
                                       Map<String, Object> params,
                                       final OnGoogleResultListener mListener) {
        LoginBackManager.googlePay(url,
                LTAppID, LTAppKey, params
                , new OnGooglePayResultListener() {
                    @Override
                    public void onPaySuccess(String result) {
                        mListener.onResultSuccess(result);
                    }

                    @Override
                    public void onPayFailed(Throwable ex) {
                        mListener.onResultError(ex);
                    }

                    @Override
                    public void onPayComplete() {

                    }

                    @Override
                    public void onPayError(String result) {
                        mListener.onResultFailed(result);
                    }
                });
    }

}
