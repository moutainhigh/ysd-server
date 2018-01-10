package com.qmd.action.download;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.activity.Activity;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

/**
 * @author xishengchun on 2017-09-25.
 * app下载界面
 */
@Service("downloadAction")
public class DownloadAction extends BaseAction {

    @Action(value = "/app/download", results = { @Result(name = "success", location = "/content/download/download.ftl", type = "freemarker") })
    public String list() {
        return SUCCESS;
    }
}
