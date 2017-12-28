package com.ysd.biz;

import com.alibaba.fastjson.JSONObject;
import com.ysd.ipyy.IPYYSMS;
import com.ysd.ipyy.IPYYSMSResult;

/**
 * @author xishengchun on 2017-08-30.
 */
public class YueSmsUtils {

    /**
     * 新用户注册短信发送
     * @param mobile
     * @return
     */
    public static IPYYSMSResult sendForMemberRegister(String mobile, String code) {
        String format = TemplateConfig.get("qmd.sms.member.register");
        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, code);
        return ipyysmsResult;
    }

    /**
     * 修改交易密码
     * @param mobile
     * @return
     */
    public static IPYYSMSResult sendForUpdateTradePwd(String mobile, String code) {
        String format = TemplateConfig.get("qmd.sms.update.trade.pwd");
        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, code);
        return ipyysmsResult;
    }

    /**
     * 提现
     * @param mobile
     * @return
     */
    public static IPYYSMSResult sendForGetCash(String mobile, String code) {
        String format = TemplateConfig.get("qmd.sms.get.cash");
        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, code);
        return ipyysmsResult;
    }

    /**
     * 找回密码
     * @param mobile
     * @return
     */
    public static IPYYSMSResult sendForForgetPwd(String mobile, String code) {
        String format = TemplateConfig.get("qmd.sms.forget.pwd");
        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, code);
        return ipyysmsResult;
    }

    /**
     * 通用验证码。但是短信内容里只有验证码
     * @param mobile
     * @param code
     * @return
     */
    public static IPYYSMSResult sendForCommon(String mobile, String code) {
        String format = TemplateConfig.get("qmd.sms.common");
        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, code);
        return ipyysmsResult;
    }

    /**
     * 发送随机的新密码
     * @param mobile
     * @param code
     * @return
     */
    public static IPYYSMSResult sendForNewPwd(String mobile, String code) {
        String format = TemplateConfig.get("qmd.sms.new.pwd");
        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, code);
        return ipyysmsResult;
    }


    /**
     * 充值获取短信验证码
     * @param mobile
     * @param code
     * @return
     */
    public static IPYYSMSResult sendForRecharge(String mobile, String code) {
        String format = TemplateConfig.get("qmd.sms.recharge");
        IPYYSMSResult ipyysmsResult = IPYYSMS.sendSMS(mobile, format, code);
        return ipyysmsResult;
    }

    public static String success() {
        return result("0", "短信发送成功");
    }

    public static String fail(String content) {
        return result("1", content);
    }

    public static String result(String code, String content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", code);
        jsonObject.put("reason", content);
        return jsonObject.toJSONString();
    }

}
