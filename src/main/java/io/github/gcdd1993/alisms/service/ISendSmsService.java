package io.github.gcdd1993.alisms.service;

import io.github.gcdd1993.alisms.domain.SmsRequest;
import io.github.gcdd1993.alisms.domain.SmsResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 阿里云短信发送接口
 *
 * @author gaochen
 * Created on 2019/6/6.
 */
public interface ISendSmsService {

    /**
     * 发送短信（同步）
     *
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param params       {@link SmsRequest#getParams()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    SmsResponse sendSync(Integer templateId, String phoneNumbers, Map<String, String> params);

    /**
     * 发送短信（同步）
     *
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param paramStr     {@link SmsRequest#getParamStr()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    SmsResponse sendSync(Integer templateId, String phoneNumbers, String paramStr);

    /**
     * 发送短信（同步）
     *
     * @param signName     {@link SmsRequest#getSignName()}
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param params       {@link SmsRequest#getParams()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    SmsResponse sendSync(String signName, Integer templateId, String phoneNumbers, Map<String, String> params);

    /**
     * 发送短信（同步）
     *
     * @param signName     {@link SmsRequest#getSignName()}
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param paramStr     {@link SmsRequest#getParamStr()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    SmsResponse sendSync(String signName, Integer templateId, String phoneNumbers, String paramStr);

    /**
     * 发送短信（同步）
     *
     * @param request 阿里云短信请求体
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    SmsResponse sendSync(SmsRequest request);

    /**
     * 发送短信（异步）
     *
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param params       {@link SmsRequest#getParams()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    CompletableFuture<SmsResponse> sendAsync(Integer templateId, String phoneNumbers, Map<String, String> params);

    /**
     * 发送短信（异步）
     *
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param paramStr     {@link SmsRequest#getParamStr()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    CompletableFuture<SmsResponse> sendAsync(Integer templateId, String phoneNumbers, String paramStr);

    /**
     * 发送短信（异步）
     *
     * @param signName     {@link SmsRequest#getSignName()}
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param params       {@link SmsRequest#getParams()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    CompletableFuture<SmsResponse> sendAsync(String signName, Integer templateId, String phoneNumbers, Map<String, String> params);

    /**
     * 发送短信（异步）
     *
     * @param signName     {@link SmsRequest#getSignName()}
     * @param templateId   {@link SmsRequest#getTemplateId()}
     * @param phoneNumbers {@link SmsRequest#getPhoneNumbers()}
     * @param paramStr     {@link SmsRequest#getParamStr()}
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    CompletableFuture<SmsResponse> sendAsync(String signName, Integer templateId, String phoneNumbers, String paramStr);

    /**
     * 发送短信（异步）
     *
     * @param request 阿里云短信请求体
     * @return 阿里云短信返回值
     * @see SmsResponse
     */
    CompletableFuture<SmsResponse> sendAsync(SmsRequest request);

}
