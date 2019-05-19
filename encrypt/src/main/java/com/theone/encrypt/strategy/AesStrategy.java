package com.theone.encrypt.strategy;

import android.content.Context;
import com.theone.encrypt.encrypt.AesEncrypt;

import javax.crypto.Cipher;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class AesStrategy implements IEncryptStrategy {
    private Context mContext;
    private String key;

    public AesStrategy(Context context) {
        this(context, null);
    }

    public AesStrategy(Context context, String key) {
        this.mContext = context.getApplicationContext();
        this.key = key;
    }

    @Override
    public Cipher getCipher(int mode) {
        try {
            if (mode == Cipher.DECRYPT_MODE){
                return AesEncrypt.getInstance(mContext).getDecryptCipher(key);
            }else {
                return AesEncrypt.getInstance(mContext).getEncryptCipher(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String encrypt(String str) {
        try {
            return AesEncrypt.getInstance(mContext).encrypt(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String str) {
        try {
            return AesEncrypt.getInstance(mContext).decrypt(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
