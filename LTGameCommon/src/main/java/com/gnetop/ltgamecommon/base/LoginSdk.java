package com.gnetop.ltgamecommon.base;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class LoginSdk {

    private static final String TAG = LoginSdk.class.getSimpleName();

    /**
     * 初始化SDK
     */
    public static void initSDK() {
        initLog();
    }

    /**
     * 初始化log
     */
    private static void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
