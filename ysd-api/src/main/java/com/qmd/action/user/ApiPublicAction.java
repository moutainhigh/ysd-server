package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.BaseBean;
import com.qmd.util.ApiConstantUtil;
import com.ysd.biz.PushForBiz;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xishengchun on 2017-08-29.
 * 测试类
 */
@Service("apiPublicAction")
public class ApiPublicAction extends ApiBaseAction {

    private String deviceToken;//消息推送

    private String token; // 登录token

    @Action(value = "/api/pushlogin", results = { @Result(type = "json") })
    public String pushWhenLogin() {
        if (StringUtils.isBlank(deviceToken)) {
            return ajaxJson("M0008_99", "推送的deviceToken不能为空");
        }

        PushForBiz.pushWhenLogin(deviceToken);
        BaseBean baseBean = new BaseBean();
        baseBean.setRcd("success");
        baseBean.setRmg("在其它设备登录,推送成功");
        return ajax(baseBean);
    }

    @Action(value = "/api/pushborrow", results = { @Result(type = "json") })
    public String pushWhenBorrow() {
        if (StringUtils.isBlank(deviceToken)) {
            return ajaxJson("M0008_99", "推送的deviceToken不能为空");
        }

        PushForBiz.pushWhenBorrow(deviceToken, "七夕标", "2017-08-29", new BigDecimal("50000"), new BigDecimal("2000"));
        BaseBean baseBean = new BaseBean();
        baseBean.setRcd("success");
        baseBean.setRmg("还款通知,推送成功");
        return ajax(baseBean);
    }


    @Action(value = "/api/pushapplycash", results = { @Result(type = "json") })
    public String pushWhenApplyCash() {
        if (StringUtils.isBlank(deviceToken)) {
            return ajaxJson("M0008_99", "推送的deviceToken不能为空");
        }

        PushForBiz.pushWhenApplyCash(deviceToken, "测试用户", new BigDecimal("10000"), "6233786849372910383");
        BaseBean baseBean = new BaseBean();
        baseBean.setRcd("success");
        baseBean.setRmg("申请提现,推送成功");
        return ajax(baseBean);
    }



    @Action(value = "/api/pushrecharge", results = { @Result(type = "json") })
    public String pushWhenRecharge() {
        if (StringUtils.isBlank(deviceToken)) {
            return ajaxJson("M0008_99", "推送的deviceToken不能为空");
        }

        PushForBiz.pushWhenRecharge(deviceToken, "测试用户", new BigDecimal("12000"));
        BaseBean baseBean = new BaseBean();
        baseBean.setRcd("success");
        baseBean.setRmg("充值提醒,推送成功");
        return ajax(baseBean);
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
