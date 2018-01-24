package com.ysd.aliyun.enums;

/**
 * 模板枚举类
 *
 * @author: wuxj
 * @date: 2018/1/24 16:49
 */
public enum AliyunSmsEnum {
    sms1("SMS_122299523", "{\"code\":\"%s\"}"),
    sms2("SMS_122299600", "{\"code\":\"%s\",\"code1\":\"%s\"}"),
    sms3("SMS_122284546", "{\"code\":\"%s\"}"),
    sms4("SMS_122284544", "{\"code\":\"%s\"}"),
    sms5("SMS_122284541", "{\"code\":\"%s\"}");

    private String code;
    private String str;

    AliyunSmsEnum(String code, String str) {
        this.code = code;
        this.str = str;
    }

    public String getCode() {
        return code;
    }

    public String getStr() {
        return str;
    }
}
