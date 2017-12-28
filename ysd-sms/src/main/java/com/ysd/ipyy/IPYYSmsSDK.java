package com.ysd.ipyy;

import com.alibaba.fastjson.JSON;
import com.ysd.common.DateUtils;
import com.ysd.common.SMSCodeUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * @author xishengchun on 2016-11-25.
 *         创世华信短信发送
 */
public class IPYYSmsSDK {

    private static Logger LOGGER = LoggerFactory.getLogger(IPYYSmsSDK.class);

    private String account;

    private String password;

    /**
     * 初始化发送配置
     */
    public void init(String account, String password) {
        this.account = account;
        this.password = password;
    }

    /**
     * 发送短信
     * @param url
     * 短信的接口地址
     * @param mobile
     * 目的手机号码。多个使用半角逗号隔开
     * @param content
     * 发送内容
     * @return
     */
    public IPYYSMSResult sendSMS(String url, String mobile, String content) {
        if (StringUtils.isBlank(account)) {
            throw new IllegalArgumentException("短信发送失败:账号不能为空");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("短信发送失败:密码不能为空");
        }

        if (StringUtils.isBlank(url)) {
            return IPYYSMSResult.fail("短信发送失败:发送地址不能为空");
        }

        if (StringUtils.isBlank(mobile)) {
            return IPYYSMSResult.fail("短信发送失败:手机号不能为空");
        }

        if (!SMSCodeUtils.validMobile(mobile)) {
            return IPYYSMSResult.fail("短信发送失败:手机号格式不正确");
        }

        if (StringUtils.isBlank(content)) {
            return IPYYSMSResult.fail("短信发送失败:发送内容不能为空");
        }

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        post.addParameter("userid", "");
        post.addParameter("account", account);
        post.addParameter("password", Md5Utils.GetMD5Code(password));
        post.addParameter("mobile", mobile);
        post.addParameter("content", content);
        post.addParameter("sendTime", "");
        post.addParameter("extno", "");

        try {
            int status = client.executeMethod(post);
            if (status != 200) {
                return IPYYSMSResult.fail("短信发送失败:服务连接失败!");
            }
            String str = post.getResponseBodyAsString();
            IPYYSMSResult ipyysmsResult = JSON.parseObject(str, IPYYSMSResult.class);
            if (ipyysmsResult == null) {
                return IPYYSMSResult.fail("短信发送失败:发送结果解析失败");
            }
            if (ipyysmsResult.ok()) {
                // 保存发送成功的时间
                ipyysmsResult.setSendDate(DateUtils.getDateTime(new Date(), DateUtils.DATE_FORMAT_NUM_SECOND));
            }
            return ipyysmsResult;
        } catch (IOException e) {
            LOGGER.error("短信发送异常", e);
            return IPYYSMSResult.fail("短信发送异常");
        } catch (Exception e) {
            LOGGER.error("短信发送异常", e);
            return IPYYSMSResult.fail("短信发送异常");
        }
    }

}
