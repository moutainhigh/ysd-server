package com.ysd.push;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 封装推送对象
 */
public class PushBuild {
    protected static final Logger LOGGER = LoggerFactory.getLogger(PushBuild.class);


    public static PushPayload buildPushObject_all_all_alert(String alert) {
        return PushPayload.newBuilder().setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder().setTitle("消息标题").setMsgContent("消息内容").build())
                .build();
    }


    public static PushPayload buildPushObject_all_alias_alert(String alert, String... alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(alert))
                .build();
    }

    public static PushPayload buildPushObject_all_tag_alert(String alert, String... tag) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.alert(alert))
                .build();
    }


    public static PushPayload buildPushObject_android_tag_alertWithTitle(String alert, String title, String... tags) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tags))
                .setNotification(Notification.android(alert, title, null))
                .build();
    }

    public static PushPayload buildPushObject_android_and_ios(String alert, String androidTitle, int iosBadge, Map<String, String> extras, String... tags) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tags))
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(androidTitle)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(iosBadge)
                                .addExtras(extras).build())
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(List<String> tags, String alert, int badge, String sound, Map<String, String> extras, String msgContent, boolean production) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and(tags))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(badge)
                                .setSound(sound)
                                .addExtras(extras)
                                .build())
                        .build())
                .setMessage(Message.content(msgContent))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(production)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(List<String> tags, Map<String, String> extras, String msgContent, String... alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag(tags))
                        .addAudienceTarget(AudienceTarget.alias(alias))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtras(extras)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_all_messageWithExtras(String alert, String androidTitle, int iosBadge, String msgContent, Map<String, String> extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(androidTitle)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(iosBadge)
                                .addExtras(extras)
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtras(extras)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_tags_messageWithExtras(String alert, String androidTitle, int iosBadge, String msgContent, Map<String, String> extras,String... tags) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag(tags))
                        .build())
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(androidTitle)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(iosBadge)
                                .addExtras(extras)
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtras(extras)
                        .build())
                .build();
    }


    public static PushPayload buildPushObject_alias_messageWithExtras(String alert, String androidTitle, int iosBadge, String msgContent, Map<String, String> extras,String... alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(alias))
                        .build())
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(androidTitle)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(iosBadge)
                                .addExtras(extras)
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtras(extras)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_alias_messageWithExtras(String iosAlert, String androidAlert, String androidTitle, int iosBadge, String msgContent, Map<String, String> extras,String... alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(alias))
                        .build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(androidTitle)
                                .addExtras(extras)
                                .setAlert(androidAlert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(iosBadge)
                                .addExtras(extras)
                                .setAlert(iosAlert)
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtras(extras)
                        .build())
                .build();
    }


}

