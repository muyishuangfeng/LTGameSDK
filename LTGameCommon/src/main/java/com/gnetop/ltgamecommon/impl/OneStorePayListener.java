package com.gnetop.ltgamecommon.impl;

public interface OneStorePayListener {

    void onOneStoreSuccess();

    void onOneStoreConnected();

    void onOneStoreDisConnected();

    void onOneStoreUpdate();

    void onOneStoreClientFailed();
}
