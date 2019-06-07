<p align="center">
<a href="https://travis-ci.org/gcdd1993/ali-sms-spring-boot-starter"><img src="https://travis-ci.org/gcdd1993/ali-sms-spring-boot-starter.svg?branch=master"></a>
<img src="https://img.shields.io/badge/language-java-green.svg">
</p>

# 简介

> 使用`SpringBoot`自动装配简化对接阿里云短信过程。

**小工具一枚，欢迎使用和Star支持，如使用过程中碰到问题，可以提出Issue，我会尽力完善该Starter**

# 版本基础

`aliyun-java-sdk-core:4.1.0`

# 如何使用

## Maven

```xml
<dependency>
  <groupId>io.github.gcdd1993</groupId>
  <artifactId>ali-sms-spring-boot-starter</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>
```

## Gradle

```groovy
compile 'io.github.gcdd1993:ali-sms-spring-boot-starter:1.0.0.RELEASE'
```

## 参数配置

以`application.yml`举例

```yaml
ali:
  sms:
  	domain: "dysmsapi.aliyuncs.com" ## 默认dysmsapi.aliyuncs.com
  	version: "2017-05-25" ## 默认2017-05-25
  	action: "SendSms" ## 默认SendSms
    access-key:
      id: "${阿里云短信AccessKeyId}"
      secret: "${阿里云短信AccessKeySecret}"
    region-id: "${阿里云短信地域}"
    sign-name: "${阿里云短信签名}" ## 如果不填，必须在发送方法中指定
```

## 基本使用

### 同步发送短信

为了方便使用，接口上进行了方法的重载，提供5种不同的参数列表供选择，你可以自行选择使用

```java
/**
 * 同步发送短信
 * <p>
 *     参数1：使用的短信模板ID
 *     参数2：接收者的手机号，如"17602526129,17602923211"
 *     参数3：Map，key对应模板中的参数名，value对应值（这里是使用Jackson来序列化）
 * </p>
 */
@Test
public void sendSync() {
    SmsResponse smsResponse = sendService.sendSync(TEMPLATE_ID, PHONE_NUMBER, MAP);
    Assert.assertTrue(smsResponse.isSuccess());
}

/**
 * 同步发送短信
 * <p>
 *     参数1：使用的短信模板ID
 *     参数2：接收者的手机号，如"17602526129,17602923211"
 *     参数3：要发送的短信写入值，你可以自己进行json的拼装。注意要进行json的转义，例如："{\"code\":\"112233\"}"
 * </p>
 */
@Test
public void sendSync1() {
    SmsResponse smsResponse = sendService.sendSync(TEMPLATE_ID, PHONE_NUMBER, "{\"code\":\"112233\"}");
    Assert.assertTrue(smsResponse.isSuccess());
}

/**
 * 同步发送短信
 * <p>
 *     参数1：短信签名，适用于同一模板需要有不同短信签名的
 *     参数2：使用的短信模板ID
 *     参数3：接收者的手机号，如"17602526129,17602923211"
 *     参数4：Map，key对应模板中的参数名，value对应值（这里是使用Jackson来序列化）
 * </p>
 */
@Test
public void sendSync2() {
    SmsResponse smsResponse = sendService.sendSync(SIGN_NAME, TEMPLATE_ID, PHONE_NUMBER, MAP);
    Assert.assertTrue(smsResponse.isSuccess());
}

/**
 * 同步发送短信
 * <p>
 *     参数1：短信签名，适用于同一模板需要有不同短信签名的
 *     参数2：使用的短信模板ID
 *     参数3：接收者的手机号，如"17602526129,17602923211"
 *     参数4：要发送的短信写入值，你可以自己进行json的拼装。注意要进行json的转义，例如："{\"code\":\"112233\"}"
 * </p>
 */
@Test
public void sendSync3() {
    SmsResponse smsResponse = sendService.sendSync(SIGN_NAME, TEMPLATE_ID, PHONE_NUMBER, "{\"code\":\"112233\"}");
    Assert.assertTrue(smsResponse.isSuccess());
}
```

最后一个提供了一个参数对象来定义短信发送请求，如果不嫌麻烦，可以使用这个。

```java
/**
 * 阿里云短信请求体
 *
 * @author gaochen
 * @date 2019/6/6
 */
@Data
public class SmsRequest {

    /**
     * 接收短信的手机号码。以英文逗号（,）分隔。
     */
    private String phoneNumbers;

    /**
     * 短信签名名称。请在控制台签名管理页面签名名称一列查看。
     */
    private String signName;

    /**
     * 短信模板ID，前缀为SMS_
     */
    private Integer templateId;

    /**
     * 阿里云短信内容,key:短信模板中的字段名，value：短信模板字段对应值
     * 使用此字段需要{@link com.fasterxml.jackson.databind.ObjectMapper}
     */
    private Map<String, String> params;

    /**
     * json str of  {@link #getParams()}
     * 使用此字段请设置params为Null
     */
    private String paramStr;

}
```

使用：

```java
@Test
public void sendSync4() {
    SmsRequest smsRequest = new SmsRequest();
    smsRequest.setPhoneNumbers(PHONE_NUMBER);
    smsRequest.setTemplateId(TEMPLATE_ID);
    smsRequest.setParams(MAP);
    SmsResponse smsResponse = sendService.sendSync(smsRequest);
    Assert.assertTrue(smsResponse.isSuccess());
}
```

### 异步发送短信

> 考虑到发短信的需求，一般来说都需要异步加持，对以上5中方法分别提供了`sendAsync`，使用方法一致。

```java
CompletableFuture<SmsResponse> smsResponse = sendService.sendAsync(TEMPLATE_ID, PHONE_NUMBER, MAP);
smsResponse.thenAcceptAsync(sr -> {
    if (sr.isSuccess()) {
        System.out.println("发短信成功");
    } else {
        System.out.println("发送到消息队列，准备重试此次短信");
    }
});
```

## 高级使用

除了使用以上方法发送短信外，你还可以使用官方的`IAcsClient`来发送短信，如

```java
package io.github.gcdd1993.demo;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.request;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gcdd1993.alisms.domain.SmsRequest;
import io.github.gcdd1993.alisms.domain.SmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author gaochen
 * @date 2019/6/8
 */
@Service
public class SendService {

    @Autowired
    private IAcsClient acsClient;

    public SmsResponse sendSync() {
        try {
            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "region");
            request.putQueryParameter("PhoneNumbers", "1771636783");
            request.putQueryParameter("SignName", "SignName");
            request.putQueryParameter("TemplateCode", "SMS_12345678");
            request.putQueryParameter("TemplateParam", "{\"code\":\"112233\"}");
            CommonResponse commonResponse = acsClient.getCommonResponse(request);

            return SmsResponse.SmsResponseBuilder.build(commonResponse);

        } catch (ClientException e) {
            log.error("send msg error.", e);
            return SmsResponse.SmsResponseBuilder.buildFail(e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("write json failed.", e);
            return SmsResponse.SmsResponseBuilder.buildFail("短信参数在json序列化时出错");
        }
    }
}
```

# Licenses

[The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)

# Issues

[Issues Welcome](<https://github.com/gcdd1993/ali-sms-spring-boot-starter/issues>)

# 更多参考

[阿里云短信服务API参考](<https://help.aliyun.com/document_detail/101300.html>)