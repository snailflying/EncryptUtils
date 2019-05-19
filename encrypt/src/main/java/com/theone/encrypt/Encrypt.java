package com.theone.encrypt;

import android.content.Context;
import com.theone.encrypt.strategy.IEncryptStrategy;
import com.theone.encrypt.strategy.RsaStrategy;

/**
 * @Author zhiqiang
 * @Date 2019-05-15
 * @Email liuzhiqiang@moretickets.com
 * @Description 加密解密工具类, 默认用RsaEncrypt加密
 */
public class Encrypt {

    public static IEncryptStrategy with(Context context) {
        return with(new RsaStrategy(context));
    }

    public static IEncryptStrategy with(IEncryptStrategy encrypt) {
        return encrypt;
    }
}
