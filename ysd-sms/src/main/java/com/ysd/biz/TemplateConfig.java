package com.ysd.biz;

import com.ysd.ipyy.IPYYSMSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xishengchun on 2017-08-30.
 */
public class TemplateConfig {

    private Logger LOGGER = LoggerFactory.getLogger(IPYYSMSConfig.class);

    /* 默认将配置文件放在classpath根目录下。 */
    private static final String FILE_NAME = "/sms.template.properties";

    private static TemplateConfig templateConfig;

    private java.util.Properties properties = new java.util.Properties();

    public static TemplateConfig getInstance() {
        if (templateConfig == null) {
            templateConfig = new TemplateConfig();
        }
        return templateConfig;
    }

    private TemplateConfig() {
        init();
    }

    private void init() {
        try {
            InputStream resourceAsStream = TemplateConfig.class.getResourceAsStream(FILE_NAME);
            this.properties.load(resourceAsStream);
        } catch (FileNotFoundException e) {
            LOGGER.error("加载sms.template.properties配置文件异常,请将配置文件放置com.ysd.ipyy目录", e);
        } catch (IOException e) {
            LOGGER.error("加载sms.template.properties配置文件异常,请将配置文件放置com.ysd.ipyy目录", e);
        } catch (Exception e) {
            LOGGER.error("加载sms.template.properties配置文件异常,请将配置文件放置com.ysd.ipyy目录", e);
        }

    }


    public String getString(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key) {
        return TemplateConfig.getInstance().getString(key);
    }

}
