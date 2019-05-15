package com.theone.encrypt.encrypt;

import android.content.Context;
import android.text.TextUtils;
import com.theone.encrypt.util.Base64Util;
import com.theone.encrypt.util.GenKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @Author zhiqiang
 * @Email liuzhiqiang@moretickets.com
 * @Date 2019-05-15
 * @Description
 */
public class Des3Encrypt {
    public static final String DESEDE_CBC_PKCS5_PADDING = "desede/CBC/PKCS5Padding";
    private static String encoding = "utf-8";
    private static Des3Encrypt instance;
    private Context context;


    private Des3Encrypt(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 单例模式
     * @param context context
     * @return 获取AesUtil实例
     */
    public static Des3Encrypt getInstance(Context context) {
        if (instance == null) {
            synchronized (Des3Encrypt.class) {
                if (instance == null) {
                    instance = new Des3Encrypt(context);
                }
            }
        }
        return instance;
    }
    
    /**
     * 加密
     * @param plainText 要加密文字
     * @param secretKey 密钥
     *
     * @return 加密文字
     */
    public  String encode(String secretKey, String plainText) throws Exception {
        SecretKey deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(getKey(secretKey));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        deskey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(DESEDE_CBC_PKCS5_PADDING);
        IvParameterSpec ips = new IvParameterSpec(getIV(secretKey));
        cipher.init(1, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64Util.encode(encryptData);
    }

    /***
     * 解密
     * @param encryptText 要解密文字
     * @param secretKey  密钥
     * @return 解密文字
     */
    public  String decode(String secretKey, String encryptText) throws Exception {
        SecretKey deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(getKey(secretKey));
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(DESEDE_CBC_PKCS5_PADDING);
        IvParameterSpec ips = new IvParameterSpec(getIV(secretKey));
        cipher.init(2, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));
        return new String(decryptData, encoding);
    }

    private  byte[] getKey(String key) {
        String serialNo = GenKey.getAndroidId(context);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + key + "#$Zhi$D%F^Qiang").substring(0, 24).getBytes();
    }

    private byte[] getIV(String key) {
        String serialNo = GenKey.getAndroidId(context);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + key + "#$Zhi$D%FQiang").substring(0, 8).getBytes();
    }
}
