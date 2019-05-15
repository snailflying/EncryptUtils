package com.theone.encrypt.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * @Author zhiqiang
 * @Email liuzhiqiang@moretickets.com
 * @Date 2019-05-15
 * @Description
 */
public class SpUtils {

    private static final String ENCRYPT_INFO = "encrypt_info";

    private static SharedPreferences getSpSetting(Context context) {
        if (null == context) return null;
        return context.getSharedPreferences(ENCRYPT_INFO, Context.MODE_PRIVATE);
    }


    ////*************************************************////
    public static void setAESKey(Context context, String value) {
        if (TextUtils.isEmpty(value)) return;
        SharedPreferences spUser = getSpSetting(context);
        SharedPreferences.Editor editor = spUser.edit();
        editor.putString("aes_key", value);
        editor.apply();
    }

    public static String getAESKey(Context context) {
        SharedPreferences spUser = getSpSetting(context);
        return spUser.getString("aes_key", "");
    }

    public static void setIV(Context context, String value) {
        if (TextUtils.isEmpty(value)) return;
        SharedPreferences spUser = getSpSetting(context);
        SharedPreferences.Editor editor = spUser.edit();
        editor.putString("security_iv", value);
        editor.apply();
    }

    public static String getIV(Context context) {
        SharedPreferences spUser = getSpSetting(context);
        return spUser.getString("security_iv", "");
    }
}