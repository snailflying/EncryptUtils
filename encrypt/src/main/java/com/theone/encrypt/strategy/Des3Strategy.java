package com.theone.encrypt.strategy;

import android.content.Context;
import com.theone.encrypt.encrypt.Des3Encrypt;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class Des3Strategy implements IEncrypt {

    private String key;
    private Context mContext;

    public Des3Strategy(Context context) {
        this(context, null);
    }

    public Des3Strategy(Context context, String key) {
        this.mContext = context;
        if (key != null) {
            this.key = key;
        }
    }

    @Override
    public String encrypt(String str) {
        try {
            return Des3Encrypt.getInstance(mContext).encode(key,str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String decode(String str) {
        try {
            return Des3Encrypt.getInstance(mContext).decode(key,str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
