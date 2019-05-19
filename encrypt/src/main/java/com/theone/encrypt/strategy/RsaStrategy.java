package com.theone.encrypt.strategy;

import android.content.Context;
import com.theone.encrypt.encrypt.RsaEncrypt;

import javax.crypto.Cipher;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class RsaStrategy implements IEncryptStrategy {
    private Context mContext;
    private String key;

    public RsaStrategy(Context context) {
        this(context, null);
    }

    public RsaStrategy(Context context, String key) {
        this.mContext = context.getApplicationContext();
        this.key = key;
    }

    @Override
    public Cipher getCipher(int mode) {
        try {
            if (mode == Cipher.DECRYPT_MODE) {
                return RsaEncrypt.getInstance(mContext).getDecryptCipher(key);
            } else {
                return RsaEncrypt.getInstance(mContext).getEncryptCipher(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String encrypt(String str) {
        try {
            return RsaEncrypt.getInstance(mContext).encrypt(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String str) {
        try {
            return RsaEncrypt.getInstance(mContext).decrypt(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
