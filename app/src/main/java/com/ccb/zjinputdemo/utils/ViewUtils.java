package com.ccb.zjinputdemo.utils;

import android.content.Context;

public class ViewUtils {
    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    private static int screenWidth = 0;
    public static int getScreenWidth(Context context) {
        if (0 == screenWidth)
            screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        return screenWidth;
    }
}
