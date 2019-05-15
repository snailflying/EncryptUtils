package com.theone.encrypt.encrypt;

import android.content.Context;
import android.util.Log;
import com.theone.encrypt.constant.EncryptConstants;
import com.theone.encrypt.util.Base64Util;
import com.theone.encrypt.util.GenKey;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class AesEncrypt {

    private static AesEncrypt instance;

    private Context context;

    private AesEncrypt(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 单例模式
     * @param context context
     * @return 获取AesEncrypt实例
     */
    public static AesEncrypt getInstance(Context context) {
        if (instance == null) {
            synchronized (AesEncrypt.class) {
                if (instance == null) {
                    instance = new AesEncrypt(context);
                }
            }
        }
        return instance;
    }

    private byte[] getKey(String key) {
        String serialNo = GenKey.getAndroidId(context);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + key + "#$Zhi$D%F^Qiang").substring(0, 16).getBytes();
    }
    private byte[] getIV(String key) {
        String serialNo = GenKey.getAndroidId(context);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + key + "#$Zhi$D%FQiang").substring(0, 12).getBytes();
    }
    /**
     * AES128加密
     * @param plainText 明文
     * @return 加密后的值
     */
    public String encrypt(String key, String plainText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(getKey(key), EncryptConstants.TYPE_AES);
            Cipher cipher = Cipher.getInstance(EncryptConstants.AES_GCM_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(getIV(key)));
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64Util.encode(encrypted);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    /**
     * AES128解密
     * @param cipherText 密文
     * @return 解密后端值
     */
    public String decrypt(String key, String cipherText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(getKey(key), EncryptConstants.TYPE_AES);
            byte[] encrypted1 = Base64Util.decode(cipherText);
            Cipher cipher = Cipher.getInstance(EncryptConstants.AES_GCM_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(getIV(key)));
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            return null;
        }
    }


}
