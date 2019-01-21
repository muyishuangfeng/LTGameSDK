package com.gnetop.ltgamecommon.impl;


public interface onOneStoreUploadListener {
    /**
     * 上传成功
     *
     * @param result 成功code
     */
    void onOneStoreUploadSuccess(int result);

    /**
     * 上传失败
     *
     * @param error 失败信息
     */
    void onOneStoreUploadFailed(Throwable error);

}
