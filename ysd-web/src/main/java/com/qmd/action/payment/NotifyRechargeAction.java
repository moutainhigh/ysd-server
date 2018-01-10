package com.qmd.action.payment;

import com.opensymphony.xwork2.ActionContext;
import com.qmd.action.base.BaseAction;
import com.qmd.util.ConfigUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付回调相关
 * @author yujian
 */
@Service("notifyRechargeAction")
public class NotifyRechargeAction extends NotifyAction {

    private static Logger log = Logger.getLogger(NotifyRechargeAction.class);

    /**
     * 通联购买申请的异步通知
     * 充值回调处理
     * @return
     */
    @Action(value="/payment/mtp/buy/notify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String notifyForMTPBuyProduct(){


        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        msg = com.ysd.util.HttpUtil.post(pay_url+"/app/buy/notify",pmap);

        if(msg == null || "".equals(msg)){
            msg="存管服务器无法访问,请重新尝试！";
            msgUrl="/userCenter/toBankInput.do";
            return "error_ftl";
        }

        return "success";
    }

    /**
     * 充值回调处理
     * @return
     */
    @Action(value="/payment/rechargeNotify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String recharge(){


        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        msg = com.ysd.util.HttpUtil.post(pay_url+"/rechargeNotify",pmap);

        if(msg == null || "".equals(msg)){
            msg="服务器无法访问,请重新尝试！";
            return "success";
        }else{
            msg="Server接收处理完成";
        }
        return "success";
    }


    /**
     * 充值回调处理
     * @return
     */
    @Action(value="/payment/rechargeAppNotify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String rechargeApp(){


        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        msg = com.ysd.util.HttpUtil.post(pay_url+"/rechargeAppNotify",pmap);

        if(msg == null || "".equals(msg)){
            msg="存管服务器无法访问,请重新尝试！";
            msgUrl="/userCenter/toBankInput.do";
            return "error_ftl";
        }

        return "success";
    }

    private String msg;
    private String msgUrl;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }
}
