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

import static org.junit.Assert.*;

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

    @Test
    public void sendSync() {
        SmsResponse smsResponse = sendService.sendSync(TEMPLATE_ID, PHONE_NUMBER, MAP);
        Assert.assertTrue(smsResponse.isSuccess());
    }

    @Test
    public void sendSync1() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SmsResponse smsResponse = sendService.sendSync(TEMPLATE_ID, PHONE_NUMBER, mapper.writeValueAsString(MAP));
        Assert.assertTrue(smsResponse.isSuccess());
    }

    @Test
    public void sendSync2() {
        SmsResponse smsResponse = sendService.sendSync(SIGN_NAME, TEMPLATE_ID, PHONE_NUMBER, MAP);
        Assert.assertTrue(smsResponse.isSuccess());
    }

    @Test
    public void sendSync3() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SmsResponse smsResponse = sendService.sendSync(SIGN_NAME, TEMPLATE_ID, PHONE_NUMBER, mapper.writeValueAsString(MAP));
        Assert.assertTrue(smsResponse.isSuccess());
    }

    @Test
    public void sendSync4() {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setPhoneNumbers(PHONE_NUMBER);
        smsRequest.setTemplateId(TEMPLATE_ID);
        Map<String, String> map = new HashMap<>(1);
        map.put("name", "张三");
        map.put("project", "xx项目");
        map.put("machine", "xx设备");
        map.put("rule", "xx报警规则");
        smsRequest.setParams(map);
        SmsResponse smsResponse = sendService.sendSync(smsRequest);
        Assert.assertTrue(smsResponse.isSuccess());
    }

    @Test
    public void sendAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<SmsResponse> smsResponse = sendService.sendAsync(TEMPLATE_ID, PHONE_NUMBER, MAP);
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