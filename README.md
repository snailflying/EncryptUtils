
# [EncryptUtils](https://github.com/snailflying/EncryptUtils) 

> android加密解密工具类

# 支持加密解密方案
+ AES+RSA混合加密
+ AES加密
+ DES3加密



# 使用方法
#### 暂时只支持源码依赖
```java
Encrypt.with(Context).encrypt(String);
Encrypt.with(Context).decrypt(String);

//获取默认 AES+RSA混合加密 策略
Encrypt.with(Context)
//获取 AES加密 策略
Encrypt.with(AesStrategy(Context))
//获取 DES3加密加密 策略
Encrypt.with(Des3Strategy(context))

```

# 版本更新说明
+ v1.0


# 关于

+ 个人博客：[简书](https://www.jianshu.com/u/50bb4070ebb0)
+ 如果你也喜欢这个库，Star一下吧，欢迎Fork


# License

    Copyright 2016 ZhiQiang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
