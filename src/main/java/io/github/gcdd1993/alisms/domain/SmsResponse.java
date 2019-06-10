package io.github.gcdd1993.alisms.domain;

import com.aliyuncs.CommonResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 封装了阿里短信返回值
 *
 * @author gaochen
 * Created on 2019/6/6.
 */
@Data
@RequiredArgsConstructor
public class SmsResponse {

    private static final String SEND_SUCCESS_CODE = "OK";
    private static final String SEND_FAIL_CODE = "FAIL";

    private final boolean success;

    private final String code;

    private final String message;

    /**
     * @return 阿里云短信返回值
     */
    private final CommonResponse commonResponse;

    @Slf4j
    @UtilityClass
    public static class SmsResponseBuilder {

        public static SmsResponse buildSuccess(String message) {
            return new SmsResponse(true,
                    SEND_SUCCESS_CODE,
                    message,
                    null);
        }

        public static SmsResponse buildFail(String message) {
            return new SmsResponse(false,
                    SEND_FAIL_CODE,
                    message,
                    null);
        }

        public static SmsResponse buildSuccess() {
            return new SmsResponse(true,
                    SEND_SUCCESS_CODE,
                    "短信发送成功",
                    null);
        }

        public static SmsResponse buildFail() {
            return new SmsResponse(false,
                    SEND_FAIL_CODE,
                    "短信发送失败",
                    null);
        }

        public static SmsResponse build(CommonResponse commonResponse) {
            // 使用jackson解析message和code
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(commonResponse.getData());
                String code = jsonNode.get("Code").asText();
                String message = jsonNode.get("Message").asText();
                return new SmsResponse(SEND_SUCCESS_CODE.equals(code),
                        code,
                        message,
                        commonResponse);
            } catch (IOException e) {
                log.error("短信调用成功，解析短信结果json解析出错.", e);
            }
            return new SmsResponse(false,
                    "短信调用成功，解析短信结果json解析出错",
                    "短信调用成功，解析短信结果json解析出错",
                    commonResponse);
        }

        /**
         * 校验短信是否发送成功
         * <a href="https://help.aliyun.com/document_detail/101346.html?spm=a2c4g.11186623.6.613.28d92246q2Q77t">API 错误码</a>
         *
         * @param commonResponse {@link CommonResponse}
         * @return if send success, return true
         */
        public static boolean checkSuccess(CommonResponse commonResponse) {
            return SEND_SUCCESS_CODE.equals(commonResponse.getData());
        }

    }
}
