package com.theone.encrypt.strategy;

/**
 * @Author zhiqiang
 * @Date 2019-05-14
 * @Email liuzhiqiang@moretickets.com
 * @Description 加解密策略
 */
public interface IEncrypt {

    String encrypt(String str);

    String decode(String str);
}
