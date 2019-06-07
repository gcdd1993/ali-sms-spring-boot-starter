package io.github.gcdd1993.alisms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gcdd1993.alisms.domain.SmsRequest;
import io.github.gcdd1993.alisms.domain.SmsResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * TODO
 *
 * @author gaochen
 * @date 2019/6/7
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ISendSmsServiceTest {

    @Autowired
    private ISendSmsService sendService;

    private static final Map<String, String> MAP = new HashMap<String, String>(4) {{
        put("example", "xxx");
    }};

    private static final String PHONE_NUMBER = "your phone.";
    private static final Integer TEMPLATE_ID = 111;
    private static final String SIGN_NAME = "your sign name";

    /**
     * 同步发送短信
     * <p>
     * 参数1：使用的短信模板ID
     * 参数2：接收者的手机号，如"17602526129,17602923211"
     * 参数3：Map，key对应模板中的参数名，value对应值（这里是使用Jackson来序列化）
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
     * 参数1：使用的短信模板ID
     * 参数2：接收者的手机号，如"17602526129,17602923211"
     * 参数3：要发送的短信写入值，你可以自己进行json的拼装。注意要进行json的转义，例如："{\"code\":\"112233\"}"
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
     * 参数1：短信签名，适用于同一模板需要有不同短信签名的
     * 参数2：使用的短信模板ID
     * 参数3：接收者的手机号，如"17602526129,17602923211"
     * 参数4：Map，key对应模板中的参数名，value对应值（这里是使用Jackson来序列化）
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
     * 参数1：短信签名，适用于同一模板需要有不同短信签名的
     * 参数2：使用的短信模板ID
     * 参数3：接收者的手机号，如"17602526129,17602923211"
     * 参数4：要发送的短信写入值，你可以自己进行json的拼装。注意要进行json的转义，例如："{\"code\":\"112233\"}"
     * </p>
     */
    @Test
    public void sendSync3() {
        SmsResponse smsResponse = sendService.sendSync(SIGN_NAME, TEMPLATE_ID, PHONE_NUMBER, "{\"code\":\"112233\"}");
        Assert.assertTrue(smsResponse.isSuccess());
    }

    /**
     * 同步发送短信
     */
    @Test
    public void sendSync4() {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setPhoneNumbers(PHONE_NUMBER);
        smsRequest.setTemplateId(TEMPLATE_ID);
        smsRequest.setParams(MAP);
        SmsResponse smsResponse = sendService.sendSync(smsRequest);
        Assert.assertTrue(smsResponse.isSuccess());
    }

    @Test
    public void sendAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<SmsResponse> smsResponse = sendService.sendAsync(TEMPLATE_ID, PHONE_NUMBER, MAP);
        smsResponse.thenAcceptAsync(sr -> {
            if (sr.isSuccess()) {
                System.out.println("发短信成功");
            } else {
                System.out.println("发送到消息队列，准备重试此次短信");
            }
        });
        Assert.assertTrue(smsResponse.get().isSuccess());
    }

    @Test
    public void sendAsync1() throws JsonProcessingException, ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<SmsResponse> smsResponse = sendService.sendAsync(TEMPLATE_ID, PHONE_NUMBER, mapper.writeValueAsString(MAP));
        Assert.assertTrue(smsResponse.get().isSuccess());
    }

    @Test
    public void sendAsync2() throws ExecutionException, InterruptedException {
        CompletableFuture<SmsResponse> smsResponse = sendService.sendAsync(SIGN_NAME, TEMPLATE_ID, PHONE_NUMBER, MAP);
        Assert.assertTrue(smsResponse.get().isSuccess());
    }

    @Test
    public void sendAsync3() throws JsonProcessingException, ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<SmsResponse> smsResponse = sendService.sendAsync(SIGN_NAME, TEMPLATE_ID, PHONE_NUMBER, mapper.writeValueAsString(MAP));
        Assert.assertTrue(smsResponse.get().isSuccess());
    }

    @Test
    public void sendAsync4() throws ExecutionException, InterruptedException {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setPhoneNumbers(PHONE_NUMBER);
        smsRequest.setTemplateId(TEMPLATE_ID);
        smsRequest.setParams(MAP);
        CompletableFuture<SmsResponse> smsResponse = sendService.sendAsync(smsRequest);
        Assert.assertTrue(smsResponse.get().isSuccess());
    }
}