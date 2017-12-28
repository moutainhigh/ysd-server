package com.qmd.action.payment;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.ApiBaseAction;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.payment.PaymentService;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.redis.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yujian on 2017/9/16.
 */
//@InterceptorRefs({ @InterceptorRef(value = "apiUserInterceptor"), @InterceptorRef(value = "qmdDefaultStack") })
@Service("newPaymentAction")
public class NewPaymentAction extends ApiBaseAction {

    private static Logger log = Logger.getLogger(ApiPaymentAction.class);
    private static Logger logPayment = Logger.getLogger("userPaymentLog");

    @Autowired
    private PaymentService paymentService;
    /**
     * 充值支付提交
     * @return
     * qxw
     */


    @Action(value="/bank/toRecharge",results={
            @Result(name="success", location="/content/payment/payment_info.ftl", type="freemarker"),
            @Result(name="success2", location="/userCenter/payResult.do", type="redirect")})
    public String toRecharge(){

        logPayment.debug("支付开始");

        //yujian add
        if(orderNo == null) {
            orderNo = CacheUtil.getOrderId(token);
            log.debug("__-_orderNo:---------------"+orderNo);
        }

        if(StringUtils.isBlank(orderNo)||StringUtils.isBlank(checkCode)){
            logPayment.debug("订单号或验证码不存在");
            msg="订单号或验证码不存在";
            return ajaxJson("S00010", msg);
        }

        //验证码验证 yujian
        if (!ConfigUtil.isTestSmsCode(checkCode)) {
            String redis_sms_code = CacheUtil.getObjValue("sms:code:"+token).toString();
            if(!checkCode.equals(redis_sms_code)){
                logPayment.debug("验证码验证错误");
                msg="验证码验证错误";
                return ajaxJson("S00010", msg);
            }
        } else {
            log.debug("使用万能密码:---------------"+checkCode);
        }


        User getuser = getLoginUser();
        UserAccountRecharge userAccountRecharge = paymentService.getUserAccountRechargeByTradeNo(orderNo);//根据订单号 查询有无充值记录
        if(userAccountRecharge == null){
            logPayment.debug("充值记录不存在!");
            msg="充值记录不存在!";
            return ajaxJson("S00010", msg);
        }

        if(!userAccountRecharge.getUserId().equals(getuser.getId())){
            logPayment.debug("充值记录异常，用户不一致");
            msg="充值记录异常";
            return ajaxJson("S00010", msg);
        }

        if("1".equals(userAccountRecharge.getStatus().toString())){
            logPayment.debug("充值记录异常，["+orderNo+"]订单状态不对");
            msg="充值记录异常";
            return ajaxJson("S00010", msg);
        }

        //yujian 支付发起
        Map<String,String> pmap = new HashMap<String,String>();
        pmap.put("orderNo",orderNo);
        pmap.put("userId",userService.get(getuser.getId()).getBankCustNo());
        pmap.put("amount",  userAccountRecharge.getMoney().toString() ) ;
        msg = com.ysd.util.HttpUtil.post(ConfigUtil.getConfigUtil().get("pay.url")+"/rechargeApp",pmap);

        if(msg == null || "".equals(msg)){
            msg="存管服务器无法访问,请重新尝试！";
            return ajaxJson("S00010", msg);
        }

        userAccountRecharge.setIpOperator(getRequestRemoteAddr());

        logPayment.debug("充值完成，["+orderNo+"]订单");

        return SUCCESS;
    }



    public String getRequestRemoteAddr() {
        String myip = getRequest().getHeader("x-forwarded-for");
        myip = CommonUtil.ipSplit(myip);
        if (myip == null) {
            myip = getRequest().getHeader("X-Real-IP");
        }
        if (myip == null) {
            myip = getRequest().getRemoteAddr();
        }
        return myip;
    }

    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }


    private String orderNo;
    private String msg;
    private String msgUrl;
    private String checkCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String getMsgUrl() {
        return msgUrl;
    }

    @Override
    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
