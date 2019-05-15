package com.theone.encrypt.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author zhiqiang
 * @Date 2019-05-15
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class GenKey {

    /**
     * Android ID在8.0后会根据签名计算，各APP不会相同，利用此特性作为key.
     *
     * @return Settings.Secure.ANDROID_ID or serial number if not available.
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        try {
            String deviceSerial = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            if (TextUtils.isEmpty(deviceSerial)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O && context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return Build.getSerial();
                } else {
                    return (String) Build.class.getField("SERIAL").get(null);
                }
            } else {
                return deviceSerial;
            }
        } catch (Exception ignored) {
            // Fall back  to Android_ID
        }
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * SHA加密
     *
     * @param strText 明文
     * @return SHA-256
     */
    public static String SHA(final String strText) {
        // 返回值
        String strResult = null;
        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                byte byteBuffer[] = messageDigest.digest();
                StringBuilder strHexString = new StringBuilder();
                for (byte aByteBuffer : byteBuffer) {
                    String hex = Integer.toHexString(0xff & aByteBuffer);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                Log.e("Exception", e.getMessage());
            }
        }

        return strResult;
    }

}
