package com.example.ronaldhowl.cloudnote.utils;

/**
 * 输入内容判断是否为空工具类
 * Created by RonaldHowl on 2018/1/28.
 */

public class TextUtils {

    public static boolean isNoEmpty(String string) {
        if (!string.isEmpty() && string.equals(" ")) {
            return true;
        } else {
            return false;
        }
    }
}
