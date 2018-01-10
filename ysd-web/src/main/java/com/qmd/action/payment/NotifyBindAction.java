package com.qmd.action.payment;

/**
 * Created by yujian on 2017/9/12.
 */

import com.opensymphony.xwork2.ActionContext;
import com.qmd.service.user.UserService;
import com.qmd.util.ConfigUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付注册绑定回调相关
 * @author yujian
 */
@Service("notifyBindAction")
public class NotifyBindAction extends NotifyAction{

    private static Logger log = Logger.getLogger(NotifyBindAction.class);

    /**
     * 绑定回调处理
     * @return
     */
    @Action(value="/payment/bindNotify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String bind(){


        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        pmap.put("typeId","0");
        msg = com.ysd.util.HttpUtil.post(pay_url+"/bindingNotify",pmap);

        if(msg == null || "".equals(msg)){
            msg="存管服务器无法访问,请重新尝试！";
            msgUrl="/userCenter/toBankInput.do";
            return "error_ftl";
        }


        return "success";
    }

    /**
     * 绑定回调处理
     * @return
     */
    @Action(value="/payment/bindFwsNotify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String bindFws(){


        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        pmap.put("typeId","1");
        msg = com.ysd.util.HttpUtil.post(pay_url+"/bindingNotify",pmap);

        if(msg == null || "".equals(msg)){
            msg="存管服务器无法访问,请重新尝试！";
            msgUrl="/userCenter/toBankInput.do";
            return "error_ftl";
        }


        return "success";
    }


    /**
     * 绑定回调处理
     * @return
     */
    @Action(value="/payment/bindAppNotify",results={
            @Result(name="success", location="/content/payment/payment_paynotify.ftl", type="freemarker")})
    public String bindApp(){

        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);

        //打印参数
        printInfo(log,request);

        //业务逻辑处理
        String pay_url = ConfigUtil.getConfigUtil().get("pay.url");
        log.info(pay_url);
        Map<String,String> pmap = getRequestMap(request);
        pmap.put("typeId","0");
        msg = com.ysd.util.HttpUtil.post(pay_url+"/bindingAppNotify",pmap);

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
