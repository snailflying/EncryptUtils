package com.theone.encrypt.strategy;

import android.content.Context;
import com.theone.encrypt.encrypt.Des3Encrypt;

import javax.crypto.Cipher;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class Des3Strategy implements IEncryptStrategy {
    private Context mContext;
    private String key;

    public Des3Strategy(Context context) {
        this(context, null);
    }

    public Des3Strategy(Context context, String key) {
        this.mContext = context.getApplicationContext();
        if (key != null) {
            this.key = key;
        }
    }

    @Override
    public Cipher getCipher(int mode) {
        try {
            return Des3Encrypt.getInstance(mContext).getCipher(key, mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String encrypt(String str) {
        try {
            return Des3Encrypt.getInstance(mContext).encrypt(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String str) {
        try {
            return Des3Encrypt.getInstance(mContext).decrypt(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
