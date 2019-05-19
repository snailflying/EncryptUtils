package com.theone.encrypt.encrypt;

import android.content.Context;
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
public class AesEncrypt implements IEncrypt {

    private static AesEncrypt instance;

    private Context context;

    private AesEncrypt(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 单例模式
     *
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

    //keyLenth:密钥长度 需要*8  取值只能为 16 24 32 对应密钥长度为128、192、256
    private byte[] getKey(String key) {
        String serialNo = GenKey.getAndroidId(context);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + key + "#$Zhi$D%F^Qiang").substring(0, 32).getBytes();
    }

    //IV长度12
    private byte[] getIV(String key) {
        String serialNo = GenKey.getAndroidId(context);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + key + "#$Zhi$D%FQiang").substring(0, 12).getBytes();
    }

    @Override
    public Cipher getDecryptCipher(String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(getKey(key), EncryptConstants.TYPE_AES);
        Cipher cipher = Cipher.getInstance(EncryptConstants.AES_GCM_NO_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(getIV(key)));
        return cipher;
    }

    @Override
    public Cipher getEncryptCipher(String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(getKey(key), EncryptConstants.TYPE_AES);
        Cipher cipher = Cipher.getInstance(EncryptConstants.AES_GCM_NO_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(getIV(key)));
        return cipher;
    }

    /**
     * AES128加密
     *
     * @param plainText 明文
     * @return 加密后的值
     */
    @Override
    public String encrypt(String key, String plainText) throws Exception {
        Cipher cipher = getEncryptCipher(key);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64Util.encode(encrypted);
    }

    /**
     * AES128解密
     *
     * @param cipherText 密文
     * @return 解密后端值
     */
    @Override
    public String decrypt(String key, String cipherText) throws Exception {
        Cipher cipher = getDecryptCipher(key);
        byte[] encrypted1 = Base64Util.decode(cipherText);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

}
