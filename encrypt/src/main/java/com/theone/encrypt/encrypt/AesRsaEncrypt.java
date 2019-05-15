package com.theone.encrypt.encrypt;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import com.theone.encrypt.constant.EncryptConstants;
import com.theone.encrypt.util.Base64Util;
import com.theone.encrypt.util.GenKey;
import com.theone.encrypt.util.SpUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @Author zhiqiang
 * @Date 2019-05-15
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
public class AesRsaEncrypt {
    public static final String TAG = "AesRsaUtil";

    private Context mContext;

    private static final String KEYSTORE_ALIAS = "KEYSTORE_DEMO";

    private static AesRsaEncrypt instance;
    private KeyStore keyStore;


    /**
     * 顺序不能乱
     * 1.生成keyStore
     * 2.生成RsaKey
     * 3.生成AesKey
     *
     * @param context 上下文
     */
    private AesRsaEncrypt(Context context) {
        mContext = context.getApplicationContext();
        try {
            keyStore = KeyStore.getInstance(EncryptConstants.ANDROID_KEY_STORE);
            keyStore.load(null);
            createRsaKeys(context, KEYSTORE_ALIAS);
            createAESKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 单例模式
     *
     * @param context context
     * @return 获取EncryptUtil实例
     */
    public static AesRsaEncrypt getInstance(Context context) {
        if (instance == null) {
            synchronized (AesRsaEncrypt.class) {
                if (instance == null) {
                    instance = new AesRsaEncrypt(context);
                }
            }
        }
        return instance;
    }

    /**
     * AES 加密
     *
     * @param plainText: 需要被加密数据.
     * @return Base64格式的加密字符串.
     */
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(EncryptConstants.AES_GCM_NO_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, getAESKeySpec(), getIvParameterSpec());

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        //将byte转为Base64编码格式
        return Base64Util.encode(encryptedBytes);
    }
    /**
     * AES 解密
     *
     * @param encryptedText: 需要被解密数据.
     * @return 解密后字符串.
     */
    public String decrypt(String encryptedText) throws Exception {
        //将Base64编码格式字符串解码为byte
        byte[] decodedBytes = Base64Util.decode(encryptedText);

        Cipher cipher = Cipher.getInstance(EncryptConstants.AES_GCM_NO_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, getAESKeySpec(), getIvParameterSpec());

        return new String(cipher.doFinal(decodedBytes));
    }

    private void createRsaKeys(Context context, String alias)
            throws NoSuchProviderException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException {
        if (!containsAlias(alias)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                createKeysM(alias, false);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                createKeysJBMR2(context, alias);
            }
        }
    }

    private void createAESKey() throws Exception {
        // Generate AES-Key
        if (TextUtils.isEmpty(SpUtils.getIV(mContext))) {
            SpUtils.setIV(mContext, getIV());
        }

        if (TextUtils.isEmpty(SpUtils.getAESKey(mContext))) {
            // Encrypt AES-Key with RSA Public Key then save to SharedPrefs
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                String encryptAESKey = encryptRSA(getKey());
                SpUtils.setAESKey(mContext, encryptAESKey);
            }
        }
    }

    private IvParameterSpec getIvParameterSpec() throws Exception {

        return new IvParameterSpec(getIV().getBytes());
    }

    private SecretKeySpec getAESKeySpec() throws Exception {

        byte[] aesKey;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            String encryptedKey = SpUtils.getAESKey(mContext);
            aesKey = decryptRSA(encryptedKey);
        } else {
            aesKey = getKey();
        }

        return new SecretKeySpec(aesKey, EncryptConstants.TYPE_AES);
    }

    private byte[] getKey() {
        String serialNo = GenKey.getAndroidId(mContext);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + "#$Zhi$D%F^Qiang").substring(0, 16).getBytes();
    }

    private String getIV() {
        String serialNo = GenKey.getAndroidId(mContext);
        //加密随机字符串生成AES key
        return GenKey.SHA(serialNo + "#$Zhi$D%FQiang").substring(0, 12);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private KeyPair createKeysJBMR2(Context context, String alias)
            throws NoSuchProviderException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException {

        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        end.add(Calendar.YEAR, 30);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                // You'll use the alias later to retrieve the key. It's a key
                // for the key!
                .setAlias(alias)
                .setSubject(new X500Principal("CN=" + alias))
                .setSerialNumber(BigInteger.valueOf(Math.abs(alias.hashCode())))
                // Date range of validity for the generated pair.
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();

        KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(EncryptConstants.TYPE_RSA,
                EncryptConstants.ANDROID_KEY_STORE);
        kpGenerator.initialize(spec);
        return kpGenerator.generateKeyPair();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private KeyPair createKeysM(String alias, boolean requireAuth) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA,
                        EncryptConstants.ANDROID_KEY_STORE);
        keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT
                        | KeyProperties.PURPOSE_DECRYPT).setAlgorithmParameterSpec(
                new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4))
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA384,
                        KeyProperties.DIGEST_SHA512)
                // Only permit the private key to be used if the user authenticated
                // within the last five minutes.
                .setUserAuthenticationRequired(requireAuth)
                .build());
        return keyPairGenerator.generateKeyPair();

    }

    /**
     * JBMR2+ If Key with the default alias exists, returns true, else false.
     * on pre-JBMR2 returns true always.
     */
    private boolean containsAlias(String alias) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                KeyStore keyStore =
                        KeyStore.getInstance(EncryptConstants.ANDROID_KEY_STORE);
                keyStore.load(null);
                return keyStore.containsAlias(alias);
            } catch (Exception e) {
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private String encryptRSA(byte[] plainText) throws Exception {
        PublicKey publicKey = keyStore.getCertificate(KEYSTORE_ALIAS).getPublicKey();

        Cipher cipher = Cipher.getInstance(EncryptConstants.RSA_ECB_PKCS1PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedByte = cipher.doFinal(plainText);
        return Base64Util.encode(encryptedByte);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private byte[] decryptRSA(String encryptedText) throws Exception {
//        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEYSTORE_ALIAS, null);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEYSTORE_ALIAS, null);

        Cipher cipher = Cipher.getInstance(EncryptConstants.RSA_ECB_PKCS1PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedBytes = Base64Util.decode(encryptedText);

        return cipher.doFinal(encryptedBytes);
    }

}
