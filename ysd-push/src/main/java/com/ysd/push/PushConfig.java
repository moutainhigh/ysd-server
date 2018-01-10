package com.ysd.push;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xishengchun on 15/11/20.
 */
public class PushConfig {
    private Logger LOGGER = LoggerFactory.getLogger(PushConfig.class);


    private static final String FILE_NAME = "/push.properties";

    private static PushConfig pushConfig;

    private java.util.Properties properties = new java.util.Properties();

    public static PushConfig getInstance() {
        if (pushConfig == null) {
            pushConfig = new PushConfig();
        }
        return pushConfig;
    }

    private PushConfig() {
        init();
    }

    private void init() {
        try {
            InputStream resourceAsStream = PushConfig.class.getResourceAsStream(FILE_NAME);
            this.properties.load(resourceAsStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("加载push配置文件异常", e);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("加载push配置文件异常", e);
        } catch (Exception e) {
            LOGGER.error("加载push配置文件异常", e);
        }
    }

    public boolean getBoolean(String key, boolean def) {
        String value = properties.getProperty(key);

        if (StringUtils.isNotEmpty(value)) {
            return BooleanUtils.toBoolean(value);
        } else {
            return def;
        }
    }


    public String getString(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        return NumberUtils.toInt(properties.getProperty(key));
    }

    public String get(String key) {
        return getString(key);
    }

    public static JPushProperty getJPushProperty() {
        String appKey = getInstance().get("jpush.app.key");
        String masterSecret = getInstance().get("jpush.master.secret");
        String iosEnv = getInstance().get("jpush.ios.env");
        int maxRetryTimes = getInstance().getInt("jpush.maxRetryTimes");
        JPushProperty jPushProperty = new JPushProperty();
        jPushProperty.setIosEnv(iosEnv);
        jPushProperty.setAppKey(appKey);
        jPushProperty.setMasterSecret(masterSecret);
        jPushProperty.setMaxRetryTimes(maxRetryTimes);
        return jPushProperty;
    }

}
