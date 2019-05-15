package com.theone.encrypt.util;

import android.util.Base64;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * @Author zhiqiang
 * @Date 2019-05-15
 * @Email liuzhiqiang@moretickets.com
 * @Description Base64工具类，以保持跟后台统一
 */
public class Base64Util {

    public static String encode(byte[] data) {
        return Base64.encodeToString(data,Base64.DEFAULT);
    }

    public static byte[] decode(String decode) {
        return Base64.decode(decode,Base64.DEFAULT);
    }

}
