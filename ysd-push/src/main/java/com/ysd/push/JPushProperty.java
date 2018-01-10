package com.ysd.push;

import org.apache.commons.lang.StringUtils;

/**
 * Created by xishengchun on 15/10/16.
 * 推送参数
 */
public class JPushProperty {

    private String appKey;

    private String masterSecret;

    private int maxRetryTimes = 0;

    private String iosEnv;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public int getMaxRetryTimes() {
        if (maxRetryTimes <= 0) {
            maxRetryTimes = 1;
        }
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public String getIosEnv() {
        return iosEnv;
    }

    public void setIosEnv(String iosEnv) {
        this.iosEnv = iosEnv;
    }

    public boolean valid() {
        return StringUtils.isNotEmpty(appKey) && StringUtils.isNotEmpty(masterSecret);
    }

    public boolean isProduction() {
        return "production".equalsIgnoreCase(iosEnv);
    }

    @Override
    public String toString() {
        return "JPushProperty{" +
                "appKey='" + appKey + '\'' +
                ", masterSecret='" + masterSecret + '\'' +
                ", maxRetryTimes=" + maxRetryTimes +
                ", iosEnv='" + iosEnv + '\'' +
                '}';
    }
}
