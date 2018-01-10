package com.ysd.ipyy;

import com.ysd.biz.YueSmsUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xishengchun on 2016-11-25.
 */
public class IPYYSmsSDKTest {

//    @Test
    public void testSend() {
//        IPYYSmsSDK ipyySmsSDK = new IPYYSmsSDK();
//        ipyySmsSDK.init("jksw020", "ab156234");
//        String url = "http://sh2.ipyy.com/smsJson.aspx?action=send";
//        String mobile = "18662376333";
//        String content = "【驿点商学院】您的手机动态验证码为:1232。该码10分钟内有效且只能输入1次，若10分钟内未输入，需重新获取。";
//        IPYYSMSResult send = ipyySmsSDK.sendSMS(url, mobile, content);
//        Assert.assertTrue(send.ok());
    }

    @Test
    public void testSend2() {
//        String mobile = "18662376333";
//        String format = "【驿点商学院】您的手机动态验证码为:%1s。该码10分钟内有效且只能输入1次，若10分钟内未输入，需重新获取。";
//        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, "453115");
    }

    @Test
    public void testForget() {
        // YueSmsUtils.sendForForgetPwd("15072312117", "8888");
    }
}
