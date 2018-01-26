package com.ysd.aliyun.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 阿里云短信服务配置加载类
 *
 * @author: wuxj
 * @date: 2018/1/24 16:49
 */
public class AliyunSmsConfig {

    private Logger LOGGER = LoggerFactory.getLogger(AliyunSmsConfig.class);

    private static String product;
    private static String domain;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String signName;

    private static Properties props = new Properties();

    static {
        try {
            props.load((new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/ysd/aliyun/aliyun.sms.properties"), "UTF-8")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 初始化值
        product = props.getProperty("product");
        domain = props.getProperty("domain");
        accessKeyId = props.getProperty("accessKeyId");
        accessKeySecret = props.getProperty("accessKeySecret");
        signName = props.getProperty("signName");
    }

    public static String getProduct() {
        return product;
    }

    public static String getDomain() {
        return domain;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public static String getSignName() {
        return signName;
    }
}
