package com.ysd.ipyy;

import org.apache.commons.lang.StringUtils;

/**
 * @author xishengchun on 2016-11-25.
 *         华信创世短信发送
 */
public class IPYYSMS {

    private IPYYSmsSDK ipyySmsSDK = new IPYYSmsSDK();

    private static IPYYSMS ipyysms;

    private IPYYSMS() {
        String account = IPYYSMSConfig.getInstance().get("ACCOUNT");
        if (StringUtils.isBlank(account)) {
            throw new IllegalArgumentException("创世华信短信发送配置失败:账号未配置");
        }

        String password = IPYYSMSConfig.getInstance().get("PASSWORD");
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("创世华信短信发送配置失败:密码未配置");
        }

        ipyySmsSDK.init(account, password);
    }

    public static IPYYSMS getInstance() {
        if (ipyysms == null) {
            ipyysms = new IPYYSMS();
        }
        return ipyysms;
    }

    public IPYYSMSResult doSend(String url, String mobile, String content) {
        return ipyySmsSDK.sendSMS(url, mobile, content);
    }

    public static IPYYSMSResult sendSMS(String mobile, String content) {
        String sendURL = IPYYSMSConfig.getInstance().get("SMS_SEND_URL");
        return IPYYSMS.getInstance().doSend(sendURL, mobile, content);
    }

    /**
     * 通过String.format将format与data组合
     *
     * @param mobile
     * @param format
     * @param data
     * @return
     */
    public static IPYYSMSResult sendSMS(String mobile, String format, String... data) {
        String content = String.format(format, data);
        return sendSMS(mobile, content);
    }


}
