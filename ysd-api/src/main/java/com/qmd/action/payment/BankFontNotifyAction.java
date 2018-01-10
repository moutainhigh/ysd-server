package com.qmd.action.payment;

import com.qmd.action.base.ApiBaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

/**
 * Created by yujian on 2017/9/19.
 */
@Service("bankFontNotiftAction")
public class BankFontNotifyAction extends ApiBaseAction {


    private String url;

    @Action(value="/bank/info",results={
            @Result(name="success", location="/content/new/message_page.ftl", type="freemarker"),
            @Result(name="error", location="/content/new/message_page.ftl", type="freemarker")
    })
    public String nofityForRecharge(){
        url = "/bank/dm_success.do";
        return SUCCESS;
    }

    @Action(value="/bank/error",results={
            @Result(name="success", location="/content/new/message_page.ftl", type="freemarker"),
            @Result(name="error", location="/content/new/message_page.ftl", type="freemarker")
    })
    public String bankError(){
        return ERROR;
    }



    @Action(value="/bank/dm_success",results={
            @Result(name="success", location="/content/new/message_page.ftl", type="freemarker"),
            @Result(name="error", location="/content/new/message_page.ftl", type="freemarker")
    })
    public String dm_success(){
        return SUCCESS;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
