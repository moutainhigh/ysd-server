package com.ysd.ipyy;

import com.ysd.common.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xishengchun on 2016-11-25.
 * 创世华信短信结果
 */
public class IPYYSMSResult {

    private Logger LOGGER = LoggerFactory.getLogger(IPYYSMSResult.class);

    /* 返回状态值：成功返回Success 失败返回：Faild */
    private String returnstatus;

    /* 相关的错误描述 */
    private String message;

    /* 返回余额 */
    private String remainpoint;

    /* 返回本次任务的序列ID */
    private String taskID;

    /* 成功短信数：当成功后返回提交成功短信数 */
    private String successCounts;

    /* 发送时间 */
    private String sendDate;

    /* 发送的手机号码 */
    private String mobile;

    /* 短信验证码的值。只有发送短信验证码才有用 */
    private String smsCode;

    public String getReturnstatus() {
        return returnstatus;
    }

    public void setReturnstatus(String returnstatus) {
        this.returnstatus = returnstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemainpoint() {
        return remainpoint;
    }

    public void setRemainpoint(String remainpoint) {
        this.remainpoint = remainpoint;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getSuccessCounts() {
        return successCounts;
    }

    public void setSuccessCounts(String successCounts) {
        this.successCounts = successCounts;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public static IPYYSMSResult create(String returnstatus, String message) {
        IPYYSMSResult smsResult = new IPYYSMSResult();
        smsResult.setReturnstatus("failed");
        smsResult.setMessage(message);
        return smsResult;
    }

    public static IPYYSMSResult fail(String message) {
        return create("Faild", message);
    }

    public static IPYYSMSResult success(String message) {
        return create("Success", message);
    }

    /**
     * 发送成功的标记
     * @return
     */
    public boolean ok() {
        return "Success".equalsIgnoreCase(returnstatus);
    }

    /**
     * 判断手机号是否同一个个
     * @param mobile
     * @return
     */
    public boolean isValidMobile(String mobile) {
        LOGGER.debug("sms result isValidMobile mobile {} this.mobile {}", mobile, this.mobile);
        return StringUtils.isNotEmpty(mobile) && mobile.equals(this.mobile);
    }

    /**
     * 判断验证码是否正确
     * @param code
     * @return
     */
    public boolean isValidCode(String code) {
        LOGGER.debug("sms result isValidCode smscode {} this.smscode {}", code, this.smsCode);
        return StringUtils.isNotEmpty(code) && code.equals(this.smsCode);
    }

    /**
     * 验证码是否已经超时
     * 注意：短信服务器时间与应用服务器时间计算有可能会负值
     * @return
     */
    public boolean isNotTimeout() {
        if (sendDate == null) {
            return true;
        }
        long diffMinute = DateUtils.diffMinute(sendDate);
        long validMinute = Long.valueOf(IPYYSMSConfig.getInstance().get("SMS_VALID_MINUTE"));
        LOGGER.debug("sendDate {} diffMinute {} validMinute {}", sendDate, diffMinute, validMinute);
        return diffMinute <= validMinute;
    }
}
