package com.qmd.action.payment;

import com.opensymphony.xwork2.ActionContext;
import com.qmd.util.ConfigUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yujian on 2017/9/18.
 */
@Service("notifyCashAction")
public class NotifyCashAction extends NotifyAction{

    private static Logger log = Logger.getLogger(NotifyCashAction.class);

    /**
     * 提现回调处理
     * @return
     */
    @Action(value="/payment/cashNotify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String bind(){

        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        msg = com.ysd.util.HttpUtil.post(pay_url+"/cashNotify",pmap);

        if(msg == null || "".equals(msg)){
            msg="存管服务器无法访问,请重新尝试！";
            msgUrl="/userCenter/toBankInput.do";
            return "error_ftl";
        }


        return "success";
    }


    /**
     * 提现回调处理
     * @return
     */
    @Action(value="/payment/cashAppNotify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String bindApp(){

        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        msg = com.ysd.util.HttpUtil.post(pay_url+"/cashAppNotify",pmap);

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
