package com.theone.encrypt.strategy;

import android.content.Context;
import com.theone.encrypt.encrypt.AesRsaEncrypt;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class AesRsaStrategy implements IEncrypt {
    private Context mContext;

    public AesRsaStrategy(Context context) {
        this.mContext = context.getApplicationContext();

    }

    @Override
    public String encrypt(String str) {
        try {
            return AesRsaEncrypt.getInstance(mContext).encrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decode(String str) {
        try {
            return AesRsaEncrypt.getInstance(mContext).decrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
