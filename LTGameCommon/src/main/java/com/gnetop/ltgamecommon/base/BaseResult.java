package com.gnetop.ltgamecommon.base;

public class BaseResult {

    private static final int BASE_RESULT = 0X01;
    /**
     * 成功
     */
    public static final int MSG_RESULT_SUCCESS = BASE_RESULT + 1;
    /**
     * 失败
     */
    public static final int MSG_RESULT_FAILED = BASE_RESULT + 2;
    /**
     * 错误
     */
    public static final int MSG_RESULT_ERROR = BASE_RESULT + 3;
    /**
     * 不支持
     */
    public static final int MSG_RESULT_ERR_UNSUPPORT = BASE_RESULT + 4;
    /**
     * 微信签名不对
     */
    public static final int MSG_RESULT_ERR_BAN = BASE_RESULT + 5;
    /**
     * 没有权限
     */
    public static final int MSG_RESULT_ERR_COMM = BASE_RESULT + 6;
    /**
     * 用户取消
     */
    public static final int MSG_RESULT_ERR_USER_CANCEL = BASE_RESULT + 7;
    /**
     * 参数不对
     */
    public static final int MSG_RESULT_ERR_PARAMETER = BASE_RESULT + 8;
    /**
     * 用户拒绝授权
     */
    public static final int MSG_RESULT_ERR_AUTH_DENIED = BASE_RESULT + 9;
    /**
     * 微信支付回调
     */
    public static final int MSG_RESULT_COMMAND_PAY_BY_WX = BASE_RESULT + 10;
    /**
     * 进入游戏
     */
    public static final int MSG_RESULT_JUMP_INTO_GAME = BASE_RESULT + 11;
    /**
     * 请求成功
     */
    public static final int WEB_RESP_CODE_SUCCESS = BASE_RESULT + 12;
    /**
     * 微信传递成功
     */
    public static final int WE_CHAT_SEND_SUCCESS = BASE_RESULT + 13;
}
