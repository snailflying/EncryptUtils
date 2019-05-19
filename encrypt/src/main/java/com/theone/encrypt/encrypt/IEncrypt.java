package com.theone.encrypt.encrypt;

import javax.crypto.Cipher;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description 加解密策接口
 */
public interface IEncrypt {

    Cipher getEncryptCipher(String key) throws Exception;
    Cipher getDecryptCipher(String key) throws Exception;

    String encrypt(String key, String str)throws Exception;

    String decrypt(String key, String str)throws Exception;
}
