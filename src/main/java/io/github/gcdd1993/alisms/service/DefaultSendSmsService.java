package io.github.gcdd1993.alisms.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gcdd1993.alisms.autoconfigure.AliSmsProperties;
import io.github.gcdd1993.alisms.domain.SmsRequest;
import io.github.gcdd1993.alisms.domain.SmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 默认的短信发送实现
 *
 * @author gaochen
 * @date 2019/6/6
 */
@Slf4j
@Service
public class DefaultSendSmsService implements ISendSmsService {

    private final IAcsClient acsClient;

    private final AliSmsProperties properties;

    @Autowired
    public DefaultSendSmsService(IAcsClient acsClient, AliSmsProperties properties) {
        this.acsClient = acsClient;
        this.properties = properties;
    }

    @Override
    public SmsResponse sendSync(Integer templateId, String phoneNumbers, Map<String, String> params) {
        return sendSync(properties.getSignName(), templateId, phoneNumbers, params);
    }

    @Override
    public SmsResponse sendSync(Integer templateId, String phoneNumbers, String paramStr) {
        return sendSync(properties.getSignName(), templateId, phoneNumbers, paramStr);
    }

    @Override
    public SmsResponse sendSync(String signName, Integer templateId, String phoneNumbers, Map<String, String> params) {
        SmsRequest request = new SmsRequest();
        request.setTemplateId(templateId);
        request.setPhoneNumbers(phoneNumbers);
        request.setParams(params);
        request.setSignName(signName);
        return sendSync(request);
    }

    @Override
    public SmsResponse sendSync(String signName, Integer templateId, String phoneNumbers, String paramStr) {
        SmsRequest request = new SmsRequest();
        request.setTemplateId(templateId);
        request.setPhoneNumbers(phoneNumbers);
        request.setParamStr(paramStr);
        request.setSignName(signName);
        return sendSync(request);
    }

    @Override
    public SmsResponse sendSync(SmsRequest request) {
        try {
            CommonRequest commonRequest = defaultCommonRequest();
            commonRequest.putQueryParameter("PhoneNumbers", request.getPhoneNumbers());
            commonRequest.putQueryParameter("SignName", request.getSignName() == null ?
                    properties.getSignName() :
                    request.getSignName());
            commonRequest.putQueryParameter("TemplateCode", "SMS_" + request.getTemplateId());
            if (request.getParams() == null) {
                commonRequest.putQueryParameter("TemplateParam", request.getParamStr());
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                commonRequest.putQueryParameter("TemplateParam", objectMapper.writeValueAsString(request.getParams()));
            }
            CommonResponse commonResponse = acsClient.getCommonResponse(commonRequest);

            return SmsResponse.SmsResponseBuilder.build(commonResponse);

        } catch (ClientException e) {
            log.error("send msg error.", e);
            return SmsResponse.SmsResponseBuilder.buildFail("发送短信时请求出错");
        } catch (JsonProcessingException e) {
            log.error("write json failed.", e);
            return SmsResponse.SmsResponseBuilder.buildFail("短信参数在json序列化时出错");
        }
    }

    @Override
    public CompletableFuture<SmsResponse> sendAsync(Integer templateId, String phoneNumbers, Map<String, String> params) {
        return sendAsync(properties.getSignName(), templateId, phoneNumbers, params);
    }

    @Override
    public CompletableFuture<SmsResponse> sendAsync(Integer templateId, String phoneNumbers, String paramStr) {
        return sendAsync(properties.getSignName(), templateId, phoneNumbers, paramStr);
    }

    @Override
    public CompletableFuture<SmsResponse> sendAsync(String signName, Integer templateId, String phoneNumbers, Map<String, String> params) {
        SmsRequest request = new SmsRequest();
        request.setTemplateId(templateId);
        request.setPhoneNumbers(phoneNumbers);
        request.setParams(params);
        request.setSignName(signName);
        return sendAsync(request);
    }

    @Override
    public CompletableFuture<SmsResponse> sendAsync(String signName, Integer templateId, String phoneNumbers, String paramStr) {
        SmsRequest request = new SmsRequest();
        request.setTemplateId(templateId);
        request.setPhoneNumbers(phoneNumbers);
        request.setParamStr(paramStr);
        request.setSignName(signName);
        return sendAsync(request);
    }

    @Override
    public CompletableFuture<SmsResponse> sendAsync(SmsRequest request) {
        return CompletableFuture.supplyAsync(() -> sendSync(request));
    }

    /**
     * default build common request {@link CommonRequest}
     *
     * @return CommonRequest
     */
    public CommonRequest defaultCommonRequest() {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(properties.getDomain());
        request.setVersion(properties.getVersion());
        request.setAction(properties.getAction());
        request.putQueryParameter("RegionId", properties.getRegionId());

        return request;
    }

}
