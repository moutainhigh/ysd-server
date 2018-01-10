package com.ysd.push;

import cn.jpush.api.push.model.PushPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xishengchun on 15/10/16.
 */
public class JPushManager {

    private static Logger LOGGER = LoggerFactory.getLogger(JPushManager.class);

    private JPushProperty jPushProperty;

    private Map<String, JPush> jPushClientMap = new HashMap<>();

    /*true生成环境;false开发环境*/
    private boolean iosENV = false;

    protected void init() {
        initJSON();
        initJPush();
    }

    private void initJSON() {
        try {
            jPushProperty = PushConfig.getJPushProperty();
            if (!jPushProperty.valid()) {
                throw new IllegalArgumentException("未配置推送参数");
            }
            LOGGER.debug("jpush jPushProperty:{} ", jPushProperty);
        } catch(Exception e) {
            LOGGER.error("jpush解析失败", e);
        }
    }

    private void initJPush() {
        String appKey = jPushProperty.getAppKey();
        if (!jPushClientMap.containsKey(appKey)) {// 不存在推送连接
            if (jPushProperty.valid()) {
                jPushClientMap.put(appKey, JPush.create(jPushProperty));
            } else {
                LOGGER.debug("jpush 参数无效 {}", jPushProperty);
            }
        } else {
            JPush jPush = jPushClientMap.get(appKey);
            if (jPush == null) {
                jPushClientMap.put(appKey, JPush.create(jPushProperty));
            }
        }
        this.iosENV = jPushProperty.isProduction();
        LOGGER.debug("jPushClientMap {} ", jPushClientMap != null ? jPushClientMap.size() : 0);
    }

    private static JPushManager jPushManager;

    public static JPushManager getInstance() {
        if (jPushManager == null) {
            jPushManager = new JPushManager();
        }
        return jPushManager;
    }

    private JPushManager() {
        init();
    }

    /**
     * 根据标签推送消息.
     * @param alert
     * 通知内容
     * @param androidTitle
     * android通知栏里的标题
     * @param badge
     * @param msgContent
     * 消息内容
     * @param extras
     * 附件数据
     * @param tag
     * 标签
     * @return
     */
    public void sendPushByTag(String alert, String androidTitle, int badge, String msgContent,
                              Map<String, String> extras, String tag) {
        if (extras == null) {
            extras = new HashMap<>(0);
        }
        PushPayload pushPayload = PushBuild.buildPushObject_tags_messageWithExtras(alert, androidTitle, badge, msgContent, extras, tag);
        sendPush(pushPayload);
    }


    /**
     * 根据别名推送消息。
     * 一个别名对应一台机器。
     * 支持多个别名推送
     * @param alert
     * 推送弹框内容
     * @param androidTitle
     * android标题
     * @param badge
     * ios badge
     * @param msgContent
     * 消息内容
     * @param extras
     * 额外的键值对参数
     * @param alias
     */
    public void sendPushByAlias(String alert, String androidTitle, int badge, String msgContent,
                                Map<String, String> extras, String... alias) {
        if (extras == null) {
            extras = new HashMap<>(0);
        }
        PushPayload pushPayload = PushBuild.buildPushObject_alias_messageWithExtras(alert, androidTitle, badge, msgContent, extras, alias);
        sendPush(pushPayload);
    }

    public void sendPushByAlias(String iosAlert, String androidAlert, String androidTitle, int badge, String msgContent,
                                Map<String, String> extras, String... alias) {
        if (extras == null) {
            extras = new HashMap<>(0);
        }
        PushPayload pushPayload = PushBuild.buildPushObject_alias_messageWithExtras(iosAlert, androidAlert, androidTitle, badge, msgContent, extras, alias);
        sendPush(pushPayload);
    }

    /**
     * 根据别名推送消息
     * @param alert
     * @param alias
     */
    public void sendPushByAlias(String alert, String... alias) {
        PushPayload pushPayload = PushBuild.buildPushObject_all_alias_alert(alert, alias);
        sendPush(pushPayload);
    }

    /**
     * 根据标签推送消息
     * @param alert
     * @param tag
     */
    public void sendPushByTag(String alert, String... tag) {
        PushPayload pushPayload = PushBuild.buildPushObject_all_tag_alert(alert, tag);
        sendPush(pushPayload);
    }

    public void sendPush(final PushPayload pushPayload) {
        try {
            Collection<JPush> values = jPushClientMap.values();

            pushPayload.resetOptionsApnsProduction(iosENV);// 设置ios的推送环境
            LOGGER.debug("sendPush value {}, iosEnv:{}", values != null ? values.size() : 0, iosENV);
            for (JPush value : values) {
                if (value != null) {
                    value.sendPush(pushPayload);
                }
            }
        } catch(Exception e) {
            LOGGER.error("极光推送失败", e);
        }
    }

}
