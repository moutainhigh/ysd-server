package com.ysd.push;

import com.ysd.biz.PushForBiz;
import junit.framework.TestCase;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author xishengchun on 2017-08-26.
 */
public class PushForBizTest extends TestCase {

    private String deviceToken = "12345678901234567890123456789012345678901234";

    private String userName = "席胜春";

    @Test
    public void testPushForLogin() {
        PushForBiz.pushWhenLogin(deviceToken);
    }

    @Test
    public void testPushForRecharge() {
        PushForBiz.pushWhenRecharge(deviceToken, userName, new BigDecimal("10"));
    }

    @Test
    public void testPushWhenApplyCash() {
        PushForBiz.pushWhenApplyCash(deviceToken, userName, new BigDecimal("16"), "633293291938475563929");
    }

    @Test
    public void testPushWhenBorrow() {
        PushForBiz.pushWhenBorrow(deviceToken, "新手标", "2017-08-27 15:45", new BigDecimal("15"), new BigDecimal("20"));
    }

}
