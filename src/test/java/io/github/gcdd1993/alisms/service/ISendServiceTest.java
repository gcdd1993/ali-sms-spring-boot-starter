package io.github.gcdd1993.alisms.service;

import io.github.gcdd1993.alisms.domain.SmsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author gaochen
 * @date 2019/6/6
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ISendServiceTest {

    @Autowired
    private ISendService sendService;

    @Test
    public void sendSync() {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setPhoneNumbers("17602526128");
        smsRequest.setTemplateId(167365532);
        Map<String, String> map = new HashMap<>(1);
        map.put("name", "张三");
        map.put("project", "xx项目");
        map.put("machine", "xx设备");
        map.put("rule", "xx报警规则");
        smsRequest.setParams(map);
        sendService.sendSync(smsRequest);
    }

    @Test
    public void sendAsync() {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setPhoneNumbers("17602526128");
        smsRequest.setTemplateId(164376078);
        Map<String, String> map = new HashMap<>(1);
        map.put("code", "123456");
        smsRequest.setParams(map);
        sendService.sendAsync(smsRequest);
    }
}