package com.ysd.ipyy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xishengchun on 2016-11-25.
 */
public class IPYYSMSConfig {

    private Logger LOGGER = LoggerFactory.getLogger(IPYYSMSConfig.class);

    /*为也防止和其他工程文件名重名，所以将文件放在包中*/
    /* 默认将配置文件放在classpath根目录下。 */
    private static final String FILE_NAME = "ipyy.properties";

    private static IPYYSMSConfig smsConfig;

    private java.util.Properties properties = new java.util.Properties();

    public static IPYYSMSConfig getInstance() {
        if (smsConfig == null) {
            smsConfig = new IPYYSMSConfig();
        }
        return smsConfig;
    }

    private IPYYSMSConfig() {
        init();
    }

    private void init() {
        try {
            InputStream resourceAsStream = IPYYSMSConfig.class.getResourceAsStream(FILE_NAME);
            this.properties.load(resourceAsStream);
        } catch (FileNotFoundException e) {
            LOGGER.error("加载ipyy配置文件异常,请将配置文件放置com.ysd.ipyy目录", e);
        } catch (IOException e) {
            LOGGER.error("加载ipyy配置文件异常,请将配置文件放置com.ysd.ipyy目录", e);
        } catch (Exception e) {
            LOGGER.error("加载ipyy配置文件异常,请将配置文件放置com.ysd.ipyy目录", e);
        }

    }


    public String getString(String key) {
        return properties.getProperty(key);
    }

    public String get(String key) {
        return getString(key);
    }



    public static void main(String[] args) {
        String account = IPYYSMSConfig.getInstance().get("ACCOUNT");
        System.out.println("account:" + account);
        System.out.println(IPYYSMSConfig.class.getResource(""));
    }

}
