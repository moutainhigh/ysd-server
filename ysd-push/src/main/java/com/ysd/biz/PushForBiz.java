package com.ysd.biz;

import com.ysd.push.JPushManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xishengchun on 2017-08-27.
 * 各种业务的推送提醒
 */
public class PushForBiz {

    private static Logger log = Logger.getLogger(PushForBiz.class);

    /**
     * 登录成功的时候推送
     * @param deviceToken
     */
    public static void pushWhenLogin(String deviceToken) {
        try {
            if (StringUtils.isBlank(deviceToken)) {
                log.error("登录推送失败:deviceToken不能为空!");
                return;
            }
            String alert = "您的账号在其它设备登录";
            Map<String, String> extras = new HashMap<>();
            extras.put("custom", "tc");// 参照旧的写法
            JPushManager.getInstance().sendPushByAlias(alert, alert, "登录提醒", 1, alert, extras, deviceToken);
        } catch(Exception e) {
            log.error("登录推送失败", e);
        }
    }

    /**
     * 充值成功推送
     * @param deviceToken
     * @param userName
     * @param tranAmt
     */
    public static void pushWhenRecharge(String deviceToken, String userName, BigDecimal tranAmt) {
        try {

            if (StringUtils.isBlank(deviceToken)) {
                log.error("充值成功推送失败:deviceToken不能为空!");
                return;
            }

            if (tranAmt == null) {
                log.error("充值成功推送失败:充值金额不能为空!");
                return;
            }

            String message = String.format("尊敬的%1$s,您已成功充值:%2$s元。立即投资，轻松获得高收益。", userName, tranAmt);
            Map<String, String> extras = new HashMap<>();
            int deviceTokenLength = deviceToken.length();
            if(deviceTokenLength == 44){
                extras.put("custom", "cn.ysgroup.ysdai.Activities.RechargeRecordActivity");
            }else if(deviceTokenLength==64){
                extras.put("custom", "yhcz");
            }
            JPushManager.getInstance().sendPushByAlias(message, message, "充值提示", 1, message, extras, deviceToken);
        } catch(Exception e) {
            log.error("充值成功推送失败", e);
        }


    }


    /**
     * 提现申请审核通过推送
     * @param deviceToken
     * @param userName
     * @param total
     * @param account
     */
    public static void pushWhenApplyCash(String deviceToken, String userName, BigDecimal total, String account) {

        try {
            if (StringUtils.isBlank(deviceToken)) {
                log.error("提现申请推送失败:deviceToken不能为空!");
                return;
            }

            if (StringUtils.isBlank(userName)) {
                log.error("提现申请推送失败:用户不能为空!");
                return;
            }

            if (total == null) {
                log.error("提现申请推送失败:提现金额不能为空!");
                return;
            }

            if (StringUtils.isBlank(account)) {
                log.error("提现申请推送失败:账号不能为空!");
                return;
            }
            String sectionAccount = account.substring(account.length()-4, account.length());
            String message = String.format("尊敬的%1$s，您申请的%2$s元的提现已经审核通过，款项将于1个工作日后汇入尾号为%3$s的卡内，请到时查收。", userName, total, sectionAccount);
            Map<String, String> extras = new HashMap<>();
            int deviceTokenLength = deviceToken.length();
            if(deviceTokenLength == 44){
                extras.put("custom", "cn.ysgroup.ysdai.Activities.TxRecordActivity");
            }else if(deviceTokenLength==64){
                extras.put("custom", "yhtx");
            }
            JPushManager.getInstance().sendPushByAlias(message, message, "提现通知", 1, message, extras, deviceToken);
        } catch(Exception e) {
            log.error("提现申请推送", e);
        }
    }


    /**
     * 还款通知
     * @param deviceToken
     * @param borrowName
     * @param repayTime
     * @param waitMoney
     * @param waitInterest
     */
    public static void pushWhenBorrow(String deviceToken, String borrowName, String repayTime, BigDecimal waitMoney, BigDecimal waitInterest) {

        try {

            if (StringUtils.isBlank(deviceToken)) {
                log.error("还款通知推送失败:deviceToken不能为空!");
                return;
            }

            if (StringUtils.isBlank(borrowName)) {
                log.error("还款通知推送失败:项目名称不能为空!");
                return;
            }

            if (waitMoney == null) {
                log.error("还款通知推送失败:还款金额不能为空!");
                return;
            }

            if (waitInterest == null) {
                log.error("还款通知推送失败:利息不能为空!");
                return;
            }

            String message = String.format("您投资的[%1$s]项目已于%2$s还款，还款本金为%3$s元，利息为%4$s元。",
                    borrowName, repayTime, waitMoney, waitInterest
            );
            Map<String, String> extras = new HashMap<>();
            int deviceTokenLength = deviceToken.length();
            if(deviceTokenLength == 44){
                extras.put("custom", "cn.ysgroup.ysdai.Activities.RepayMentActivity");
            }else if(deviceTokenLength==64){
                extras.put("custom", "hk");
            }

            JPushManager.getInstance().sendPushByAlias(message, message, "还款通知", 1, message, extras, deviceToken);

        } catch(Exception e) {
            log.error("还款通知推送失败", e);
        }
    }
}
