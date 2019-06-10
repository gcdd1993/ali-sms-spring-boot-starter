package io.github.gcdd1993.alisms.domain;

import lombok.Data;

import java.util.Map;

/**
 * 阿里云短信请求体
 *
 * @author gaochen
 * Created on 2019/6/6.
 */
@Data
public class SmsRequest {

    /**
     * @param phoneNumbers 接收短信的手机号码。以英文逗号（,）分隔。
     * @return 接收短信的手机号码。以英文逗号（,）分隔。
     */
    private String phoneNumbers;

    /**
     * @param signName 短信签名名称。请在控制台签名管理页面签名名称一列查看。
     * @return 短信签名名称。请在控制台签名管理页面签名名称一列查看。
     */
    private String signName;

    /**
     * @param templateId 短信模板ID，前缀为SMS_
     * @return 短信模板ID，前缀为SMS_
     */
    private Integer templateId;

    /**
     * @param params 阿里云短信内容, key:短信模板中的字段名，value：短信模板字段对应值
     * @return 阿里云短信内容, key:短信模板中的字段名，value：短信模板字段对应值
     * 使用此字段需要{@link com.fasterxml.jackson.databind.ObjectMapper}
     */
    private Map<String, String> params;

    /**
     * @param paramStr json str of  {@link #getParams()}
     * @return json str of  {@link #getParams()}
     * 使用此字段请设置params为Null
     */
    private String paramStr;

}
