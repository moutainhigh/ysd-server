package net.qmdboss.action.admin;

import net.qmdboss.entity.UserAward;
import net.qmdboss.service.BorrowAwardCodeService;
import net.qmdboss.util.JsonUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
sjc 20180122
接口单词之间加下划线
* */

@ParentPackage("admin")
public class BorrowAwardCodeAction extends BaseAdminAction{

    @Resource(name = "borrowAwardCodeServiceImpl")
    private BorrowAwardCodeService borrowAwardCodeService;

    private UserAward userAward;

    public  String list(){
        userAward = borrowAwardCodeService.load(id);
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer awardCode = new StringBuffer();
        List<UserAward> awardCodeList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0;i < 30;i++ )
        {
            awardCode.delete(0,awardCode.length());
            for(int j = 0;j < 8;j++)
                awardCode.append(base.charAt(rand.nextInt(base.length())));
            userAward = new UserAward();
            userAward.setStatus(0);
            userAward.setBorrowId(id);
            userAward.setAwardCode(awardCode.toString());
            borrowAwardCodeService.save(userAward);
            awardCodeList.add(userAward);
        }
        return ajax(JsonUtil.toJson(awardCodeList));
    }
}
