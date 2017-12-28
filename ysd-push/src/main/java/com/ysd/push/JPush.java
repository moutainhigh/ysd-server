package com.ysd.push;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xishengchun on 15/10/7.
 * 极光推送
 */
public class JPush {

    private static Logger LOGGER = LoggerFactory.getLogger(JPush.class);

    private String appKey;

    private String masterSecret;

    private int maxRetryTimes;

    private JPushClient jpushClient = null;

    public static JPush create(JPushProperty jPushProperty) {
        return new JPush(jPushProperty);
    }

    public JPush(JPushProperty jPushProperty) {
        this.appKey = jPushProperty.getAppKey();
        this.masterSecret = jPushProperty.getMasterSecret();
        this.maxRetryTimes = jPushProperty.getMaxRetryTimes();
        init();
    }

    private void init() {
        jpushClient = new JPushClient(masterSecret, appKey, maxRetryTimes);
    }


    /**
     * 推送
     * @param payload
     */
    public void sendPush(final PushPayload payload) {
        RdThreadPoolExecutor.pushExector.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.info("极光推送开始**** payload {} appKey {} masterSecret {} ", payload, appKey, masterSecret);
                    PushResult result = jpushClient.sendPush(payload);
                    LOGGER.debug("极光推送正常 result.isResultOK {}", result.isResultOK());
                } catch (APIConnectionException e) {
                    LOGGER.error("极光推送连接异常.", e);
                } catch (APIRequestException e) {
                    LOGGER.error("极光推送异常", e);
                    LOGGER.info("极光推送异常-HTTP Status{} ErrorCode {} ErrorMessage {} MsgID {}",
                            e.getStatus(), e.getErrorCode(), e.getErrorMessage(), e.getMsgId());
                } catch (Exception e) {
                    LOGGER.error("极光推送异常", e);
                }
            }
        });
    }

}
