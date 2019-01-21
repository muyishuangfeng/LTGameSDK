package com.gnetop.ltgamecommon.impl;


import com.gnetop.ltgamecommon.model.AliPayBean;
import com.gnetop.ltgamecommon.model.WeChatBean;

public interface OnPayResultedListener {


    void onPayError(Throwable ex);

    void onPayComplete();

    void onAliPayResult(AliPayBean result);

    void onWeChatPayResult(WeChatBean result);
}
