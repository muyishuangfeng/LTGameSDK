package com.gnetop.ltgameonestore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.gnetop.ltgamecommon.impl.OnCreateOrderListener;
import com.gnetop.ltgamecommon.impl.onOneStoreSupportListener;
import com.gnetop.ltgamecommon.impl.onOneStoreUploadListener;
import com.gnetop.ltgamecommon.login.LoginBackManager;
import com.gnetop.ltgamecommon.model.OneStoreResult;
import com.onestore.iap.api.IapEnum;
import com.onestore.iap.api.IapResult;
import com.onestore.iap.api.PurchaseClient;
import com.onestore.iap.api.PurchaseData;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class OneStorePayManager {

    private PurchaseClient mPurchaseClient;
    private static OneStorePayManager sInstance;
    private static final int IAP_API_VERSION = 5;
    private static final String TAG = OneStorePayManager.class.getSimpleName();
    private static String devPayLoad;


    private OneStorePayManager(Context context, String publickey) {
        mPurchaseClient = new PurchaseClient(context, publickey);
    }

    /**
     * 单例
     *
     * @return
     */
    public static OneStorePayManager getInstance(Context context, String publickey) {
        if (sInstance == null) {
            synchronized (OneStorePayManager.class) {
                if (sInstance == null) {
                    sInstance = new OneStorePayManager(context, publickey);
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化
     *
     * @param context   上下文
     * @param mListener 回调
     */
    public void initOneStore(final Activity context, final String url, final String LTAppID, final String LTAppKey,
                             final String packageID, final Map<String, Object> params, final onOneStoreSupportListener mListener) {
        mPurchaseClient.connect(new PurchaseClient.ServiceConnectionListener() {
            @Override
            public void onConnected() {
                if (mListener != null) {
                    mListener.onOneStoreConnected();
                }
                checkBillingSupportedAndLoadPurchases(context, mListener);
                getLTOrderID(url, LTAppID, LTAppKey, packageID, params);
            }

            @Override
            public void onDisconnected() {
                if (mListener != null) {
                    mListener.onOneStoreDisConnected();
                }
            }

            @Override
            public void onErrorNeedUpdateException() {
                if (mListener != null) {
                    mListener.onOneStoreFailed(OneStoreResult.RESULT_CONNECTED_NEED_UPDATE);
                    PurchaseClient.launchUpdateOrInstallFlow(context);
                }
            }
        });
    }

    /**
     * 检查是否支持
     */
    private void checkBillingSupportedAndLoadPurchases(final Context context,
                                                       final onOneStoreSupportListener mListener) {
        if (mPurchaseClient == null) {
            if (mListener != null) {
                mListener.onOneStoreClientFailed("PurchaseClient is not initialized");
            }
        } else {
            mPurchaseClient.isBillingSupportedAsync(IAP_API_VERSION, new PurchaseClient.BillingSupportedListener() {
                @Override
                public void onSuccess() {
                    mListener.onOneStoreSuccess(OneStoreResult.RESULT_BILLING_OK);
                    // 然后通过对托管商品和每月采购历史记录的呼叫接收采购历史记录信息。
                    loadPurchases((Activity) context, mListener);
                }

                @Override
                public void onError(IapResult iapResult) {
                    mListener.onOneStoreError(iapResult.toString());
                }

                @Override
                public void onErrorRemoteException() {
                    Log.e(TAG, "isBillingSupportedAsync onErrorRemoteException: 远程连接失败");
                    mListener.onOneStoreFailed(OneStoreResult.RESULT_BILLING_REMOTE_ERROR);
                }

                @Override
                public void onErrorSecurityException() {
                    Log.e(TAG, "isBillingSupportedAsync onErrorSecurityException:应用状态异常下请求支付");
                    mListener.onOneStoreFailed(OneStoreResult.RESULT_BILLING_SECURITY_ERROR);
                }

                @Override
                public void onErrorNeedUpdateException() {
                    Log.e(TAG, "isBillingSupportedAsync onErrorNeedUpdateException:OneStore 需要更新");
                    mListener.onOneStoreFailed(OneStoreResult.RESULT_BILLING_NEED_UPDATE);
                    PurchaseClient.launchUpdateOrInstallFlow((Activity) context);
                }
            });
        }
    }

    /**
     * 查看购买的历史记录
     */
    private void loadPurchases(Activity context, onOneStoreSupportListener mListener) {
        loadPurchase(context, IapEnum.ProductType.IN_APP, mListener);
        loadPurchase(context, IapEnum.ProductType.AUTO, mListener);
    }

    /**
     * 加载购买数据
     *
     * @param context     上下文
     * @param productType 产品类型
     * @param mListener   接口回调
     */
    private void loadPurchase(final Activity context, final IapEnum.ProductType productType,
                              final onOneStoreSupportListener mListener) {
        if (mPurchaseClient == null) {
            mListener.onOneStoreClientFailed("PurchaseClient is not initialized");
            Log.e(TAG, "PurchaseClient is not initialized");
            return;
        }
        mPurchaseClient.queryPurchasesAsync(IAP_API_VERSION, productType.getType(),
                new PurchaseClient.QueryPurchaseListener() {
                    @Override
                    public void onSuccess(List<PurchaseData> purchaseDataList, String productType) {
                        for (int i = 0; i <purchaseDataList.size() ; i++) {
                            PurchaseData purchaseData=purchaseDataList.get(i);
                            if (purchaseData.getPurchaseState()==0){
                                Log.e(TAG,"queryPurchasesAsync onSuccess======="+purchaseData.getPurchaseId());
                            }
                        }
                        Log.e(TAG, "queryPurchasesAsync onSuccess, " + purchaseDataList.toString());

                        if (IapEnum.ProductType.IN_APP.getType().equalsIgnoreCase(productType)) {
                            onLoadPurchaseInApp(purchaseDataList, mListener);

                        } else if (IapEnum.ProductType.AUTO.getType().equalsIgnoreCase(productType)) {
                            onLoadPurchaseAuto(purchaseDataList);
                        }
                    }

                    @Override
                    public void onError(IapResult iapResult) {
                        mListener.onOneStoreError(iapResult.toString());
                        Log.e(TAG, "queryPurchasesAsync onError, " + iapResult.toString());
                    }

                    @Override
                    public void onErrorRemoteException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_PURCHASES_REMOTE_ERROR);
                        Log.e(TAG, "queryPurchasesAsync onErrorRemoteException, 查询购买远程连接失败");
                    }

                    @Override
                    public void onErrorSecurityException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_PURCHASES_SECURITY_ERROR);
                        Log.e(TAG, "queryPurchasesAsync onErrorSecurityException, 查询购买应用状态异常下请求支付");
                    }

                    @Override
                    public void onErrorNeedUpdateException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_PURCHASES_NEED_UPDATE);
                        Log.e(TAG, "queryPurchasesAsync onErrorNeedUpdateException, 查询购买需要更新");
                        PurchaseClient.launchUpdateOrInstallFlow(context);
                    }
                });
    }

    /**
     * 如果托管产品 (inapp) 是从购买历史记录中购买的, 则执行签名验证, 如果产品成功, 则会使用该产品。
     *
     * @param purchaseDataList 购买数据
     */
    private void onLoadPurchaseInApp(List<PurchaseData> purchaseDataList, onOneStoreSupportListener mListener) {
        Log.e(TAG, "onLoadPurchaseInApp() :: purchaseDataList - " + purchaseDataList.toString());
        for (PurchaseData purchase : purchaseDataList) {
            boolean result = AppSecurity.verifyPurchase(purchase.getPurchaseData(), purchase.getSignature());
            if (result) {
                consumeItem(purchase, mListener);
            }
        }
    }

    /**
     * 对于来自 "购买历史记录查询" 的每月产品 (自动), 将执行签名验证, 如果成功, 则为游戏 ui 方案保存产品信息。
     *
     * @param purchaseDataList 购买数据
     */
    private void onLoadPurchaseAuto(List<PurchaseData> purchaseDataList) {
        Log.e(TAG, "onLoadPurchaseAuto() :: purchaseDataList - " + purchaseDataList.toString());
        for (PurchaseData purchase : purchaseDataList) {
            boolean result = AppSecurity.verifyPurchase(purchase.getPurchaseData(), purchase.getSignature());
        }
    }

    /**
     * 在购买管理商品 (inapp) 后或购买历史记录视图完成后, 不消耗托管商品的消费.
     *
     * @param purchaseData 产品数据
     */
    private void consumeItem(final PurchaseData purchaseData, final onOneStoreSupportListener mListener) {
        if (mPurchaseClient == null) {
            mListener.onOneStoreFailed(OneStoreResult.RESULT_PURCHASES_NEED_UPDATE);
            Log.e(TAG, "PurchaseClient is not initialized");
            return;
        }
        mPurchaseClient.consumeAsync(IAP_API_VERSION, purchaseData,
                new PurchaseClient.ConsumeListener() {
                    @Override
                    public void onSuccess(PurchaseData purchaseData) {
                        mListener.onOneStoreSuccess(OneStoreResult.RESULT_CONSUME_OK);
                    }

                    @Override
                    public void onError(IapResult iapResult) {
                        mListener.onOneStoreError(iapResult.toString());
                        Log.e(TAG, "consumeAsync onError,  消费错误" + iapResult.toString());
                    }

                    @Override
                    public void onErrorRemoteException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_CONSUME_REMOTE_ERROR);
                        Log.e(TAG, "consumeAsync onError,  消费连接失败");
                    }

                    @Override
                    public void onErrorSecurityException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_CONSUME_SECURITY_ERROR);
                        Log.e(TAG, "consumeAsync onError,  消费应用状态异常下请求支付");
                    }

                    @Override
                    public void onErrorNeedUpdateException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_CONSUME_NEED_UPDATE);
                        Log.e(TAG, "consumeAsync onError,  消费产品需要更新");
                    }
                });
    }

    /**
     * oneStore支付回调
     *
     * @param requestCode     请求码
     * @param resultCode      结果码
     * @param data            数据
     * @param selfRequestCode 自定义请求码
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data, int selfRequestCode) {
        if (requestCode == selfRequestCode)
                /*
                  异步购买的API
                 * launchPurchaseFlowAsync API 响应值通过在调用中传递的意图数据的手持采购值进行分析。
                 * 解析后的响应结果通过 "清除量" 侦听器传递。
                 */
            if (resultCode == Activity.RESULT_OK) {
                if (!mPurchaseClient.handlePurchaseData(data)) {
                    Log.e(TAG, "onActivityResult handlePurchaseData false ");
                }
            } else {
                Log.e(TAG, "onActivityResult user canceled");
            }
    }

    /**
     * 购买商品
     *
     * @param productId       商品ID
     * @param selfRequestCode 请求码
     * @param productName     产品名称
     */
    public void buyProduct(final Activity context, int selfRequestCode, String productName,
                           final String productId,
                           final onOneStoreSupportListener mListener) {
        Log.e(TAG, "buyProduct() - productId:" + productId);
        if (mPurchaseClient == null) {
            mListener.onOneStoreClientFailed("PurchaseClient is not initialized");
            Log.e(TAG, "PurchaseClient is not initialized");
            return;
        }
        if (!mPurchaseClient.launchPurchaseFlowAsync(IAP_API_VERSION,
                context, selfRequestCode, productId, productName,
                "all", devPayLoad, "",
                false, new PurchaseClient.PurchaseFlowListener() {
                    @Override
                    public void onSuccess(PurchaseData purchaseData) {
                        Log.e(TAG, "launchPurchaseFlowAsync onSuccess, " + purchaseData.toString());

                        // 完成购买后, 开发人员有效负载验证。
                        if (!TextUtils.equals(devPayLoad, purchaseData.getDeveloperPayload())) {
                            Log.e(TAG, "launchPurchaseFlowAsync payload is not valid.");
                            return;
                        }
                        // 完成购买后, 将执行签名验证。
                        boolean validPurchase = AppSecurity.verifyPurchase(purchaseData.getPurchaseData(), purchaseData.getSignature());
                        Log.e(TAG, "launchPurchaseFlowAsync verifyPurchase " + validPurchase);
                        if (validPurchase) {
                            if (!productId.equals(purchaseData.getProductId())) {
                                // 托管商品 (inapp) 将在购买完成后使用。
                                consumeItem(purchaseData, mListener);
                            }
                        } else {
                            mListener.onOneStoreFailed(OneStoreResult.RESULT_SIGNATURE_FAILED);
                            Log.e(TAG, "launchPurchaseFlowAsync: Signature failed");
                        }
                    }

                    @Override
                    public void onError(IapResult result) {
                        Log.e(TAG, "launchPurchaseFlowAsync onError, " + result.toString());
                        mListener.onOneStoreError(result.toString());
                    }

                    @Override
                    public void onErrorRemoteException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_PURCHASES_FLOW_REMOTE_ERROR);
                    }

                    @Override
                    public void onErrorSecurityException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_PURCHASES_FLOW_SECURITY_ERROR);
                    }

                    @Override
                    public void onErrorNeedUpdateException() {
                        mListener.onOneStoreFailed(OneStoreResult.RESULT_PURCHASES_FLOW_NEED_UPDATE);
                        PurchaseClient.launchUpdateOrInstallFlow(context);
                    }
                })) {
            //loadPurchases(context,mListener);

        }
    }

    /**
     * 获取乐推订单ID
     *
     * @param url       baseUrl
     * @param LTAppID   乐推AppID
     * @param LTAppKey  乐推AppKey
     * @param packageID 项目对应的包名
     * @param params    游戏自定义内容
     */
    private static void getLTOrderID(String url, String LTAppID, String LTAppKey,
                                     String packageID, Map<String, Object> params) {
        Map<String, Object> map = new WeakHashMap<>();
        map.put("package_id", packageID);
        map.put("gid", "3");
        map.put("custom", params);
        LoginBackManager.createOrder(url, LTAppID,
                LTAppKey, map, new OnCreateOrderListener() {
                    @Override
                    public void onOrderSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            devPayLoad = result;
                            Log.e(TAG, result);
                        } else {
                            Log.e(TAG, "ltOrderID is nulll");
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

    private void uploadServer(String baseUrl, String LTAppID, String LTAppKey,
                              String purchase_id,
                              final onOneStoreUploadListener mListener) {
        if (!TextUtils.isEmpty(devPayLoad)) {
            Map<String, Object> map = new WeakHashMap<>();
            map.put("purchase_id", purchase_id);
            map.put("lt_order_id", devPayLoad);
            LoginBackManager.oneStorePay(baseUrl, LTAppID, LTAppKey, map, mListener);
        }else {
            Log.e(TAG,"LT Order ID is null");
        }
    }
}
