package com.buqi.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantYmlUtils implements InitializingBean {

    //从配置文件中获取内容
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    //定义公开的静态变量
    public static String END_POINT;
    public static String BUCKET_NAME;
    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        BUCKET_NAME = bucketName;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
    }
}
