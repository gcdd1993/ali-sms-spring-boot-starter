package io.github.gcdd1993.alisms.domain;

import com.aliyuncs.CommonResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

/**
 * 封装了阿里短信返回值
 *
 * @author gaochen
 * @date 2019/6/6
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
     * 阿里云短信返回值
     */
    private final CommonResponse commonResponse;

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
            return new SmsResponse(checkSuccess(commonResponse),
                    commonResponse.getData(),
                    commonResponse.getData(),
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
