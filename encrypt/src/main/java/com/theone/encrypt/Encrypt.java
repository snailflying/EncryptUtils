package com.theone.encrypt;

import android.content.Context;
import com.theone.encrypt.strategy.AesRsaStrategy;
import com.theone.encrypt.strategy.IEncrypt;

/**
 * @Author zhiqiang
 * @Date 2019-05-15
 * @Email liuzhiqiang@moretickets.com
 * @Description 加密解密工具类
 */
public class Encrypt {

    public static IEncrypt with(Context context) {
        return with(new AesRsaStrategy(context));
    }

    public static IEncrypt with(IEncrypt encrypt) {
        return encrypt;
    }
}
