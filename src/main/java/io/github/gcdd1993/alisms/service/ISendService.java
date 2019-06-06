package io.github.gcdd1993.alisms.service;

import io.github.gcdd1993.alisms.domain.SmsRequest;
import io.github.gcdd1993.alisms.domain.SmsResponse;

import java.util.concurrent.CompletableFuture;

/**
 * 阿里云短信发送接口
 *
 * @author gaochen
 * @date 2019/6/6
 */
public interface ISendService {

    /**
     * 发送短信（同步）
     *
     * @param request 阿里云默认请求体
     * @return you can easy use {@link SmsResponse#isSuccess()} to get send response
     */
    SmsResponse sendSync(SmsRequest request);

    /**
     * 发送短信（异步）
     *
     * @param request 阿里云默认请求体
     * @return you can easy use {@link SmsResponse#isSuccess()} to get send response
     */
    CompletableFuture<SmsResponse> sendAsync(SmsRequest request);

}
