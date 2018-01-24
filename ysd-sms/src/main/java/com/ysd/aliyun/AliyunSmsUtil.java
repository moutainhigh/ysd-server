package com.ysd.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ysd.aliyun.conf.AliyunSmsConfig;
import com.ysd.aliyun.enums.AliyunSmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 阿里云短信工具类
 *
 * @author: wuxj
 * @date: 2018/1/24 16:49
 */
public class AliyunSmsUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(AliyunSmsUtil.class);

    //阿里云发送短信对象
    private static IAcsClient acsClient;

    /**
     * 初始化阿里云发送短信对象
     */
    static {
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliyunSmsConfig.getAccessKeyId(), AliyunSmsConfig.getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", AliyunSmsConfig.getProduct(), AliyunSmsConfig.getDomain());
            acsClient = new DefaultAcsClient(profile);
        } catch (Exception e) {
            LOGGER.error("初始化阿里云配置异常");
        }
    }

    /**
     * 发送短信
     *
     * @param phone         手机号码
     * @param signName      签名
     * @param templateCode  模板编号
     * @param templateParam 模板参数
     * @return
     * @throws ClientException
     */
    public static SendSmsResponse sendSms(String phone, String signName, String templateCode, String templateParam) throws ClientException {
        if (acsClient == null) {
            throw new ClientException("未初始化阿里云短信发送对象");
        }
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);
        //此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            LOGGER.error("发送短信异常");
        }
        return sendSmsResponse;
    }

    /**
     * 短信查询
     *
     * @param phone 手机号码
     * @param bizId 发送流水号
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySendDetails(String phone, String bizId) throws ClientException {
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phone);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
            querySendDetailsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            LOGGER.error("发送短信查询异常");
        }

        return querySendDetailsResponse;
    }


    public static void main(String[] args) throws ClientException, InterruptedException {
        System.out.println(String.format(AliyunSmsEnum.sms2.getStr(), "1234", "12345"));
        //发短信
        SendSmsResponse response = sendSms("15858281612", AliyunSmsConfig.getSignName(), AliyunSmsEnum.sms1.getCode(), String.format(AliyunSmsEnum.sms1.getStr(), "1234"));
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        Thread.sleep(3000L);

        //查明细
        if (response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails("15858281612", response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
                System.out.println("SmsSendDetailDTO[" + i + "]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }
    }
}
