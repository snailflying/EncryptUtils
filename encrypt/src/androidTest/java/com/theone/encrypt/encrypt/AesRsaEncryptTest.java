package com.theone.encrypt.encrypt;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @Author zhiqiang
 * @Date 2019-05-15
 * @Email liuzhiqiang@moretickets.com
 * @Description
 */
@RunWith(AndroidJUnit4.class)
public class AesRsaEncryptTest {

    private AesRsaEncrypt aesRsaEncrypt;
    private String value = "志强+zhiqiang";

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        aesRsaEncrypt = AesRsaEncrypt.getInstance(appContext);
    }

    @Test
    public void encryptAndDecrypt() {
        try {
            String encrypt = aesRsaEncrypt.encrypt(value);
            System.out.print("encrypt:" + encrypt);
            Assert.assertNotEquals(encrypt, value);
            Assert.assertEquals(aesRsaEncrypt.decrypt(encrypt), value);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}