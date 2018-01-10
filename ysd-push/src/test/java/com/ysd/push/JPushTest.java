package com.ysd.push;

import cn.jpush.api.push.model.PushPayload;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xishengchun on 2017-08-26.
 */
public class JPushTest extends TestCase {

    @Test
    public void testPush() {
        /*PushOrderInfo pushOrderInfo = new PushOrderInfo();
        pushOrderInfo.toBusiness =  true;
        pushOrderInfo.tag = "xxxxxxxx";
        JPushOrder.pushWhenPay(pushOrderInfo);*/
        String[] tag = {"1123123"};
        JPushManager.getInstance().sendPushByTag("测试", tag);

    }

    @Test
    public void testPush2() {
        String[] alias = {"3"};
        JPushManager.getInstance().sendPushByAlias("测试", alias);
    }
}
