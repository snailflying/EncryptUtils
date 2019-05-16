package com.theone.encrypt.strategy;

import javax.crypto.Cipher;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description 加解密策略
 */
public interface IEncryptStrategy {

    Cipher getCipher(int mode);

    String encrypt(String str);

    String decrypt(String str);
}
