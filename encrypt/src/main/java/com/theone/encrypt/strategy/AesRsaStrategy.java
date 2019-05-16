package com.theone.encrypt.strategy;

import android.content.Context;
import com.theone.encrypt.encrypt.AesRsaEncrypt;

import javax.crypto.Cipher;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class AesRsaStrategy implements IEncryptStrategy {
    private Context mContext;
    private String key;

    public AesRsaStrategy(Context context) {
        this(context, null);
    }
    public AesRsaStrategy(Context context, String key) {
        this.mContext = context.getApplicationContext();
        if (key != null) {
            this.key = key;
        }
    }
    @Override
    public Cipher getCipher(int mode) {
        try {
            return AesRsaEncrypt.getInstance(mContext).getCipher(key, mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String encrypt(String str) {
        try {
            return AesRsaEncrypt.getInstance(mContext).encrypt(key,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String str) {
        try {
            return AesRsaEncrypt.getInstance(mContext).decrypt(key,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
