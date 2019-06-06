package io.github.gcdd1993.alisms.domain;

import com.aliyuncs.CommonResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

/**
 * TODO
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

    @UtilityClass
    public static class SmsResponseBuilder {

        public static SmsResponse buildSuccess(String message) {
            return new SmsResponse(true,
                    SEND_SUCCESS_CODE,
                    message);
        }

        public static SmsResponse buildFail(String message) {
            return new SmsResponse(false,
                    SEND_FAIL_CODE,
                    message);
        }

        public static SmsResponse buildSuccess() {
            return new SmsResponse(true,
                    SEND_SUCCESS_CODE,
                    "短信发送成功");
        }

        public static SmsResponse buildFail() {
            return new SmsResponse(false,
                    SEND_FAIL_CODE,
                    "短信发送失败");
        }

        public static SmsResponse build(CommonResponse commonResponse) {
            return new SmsResponse(commonResponse.getHttpResponse().isSuccess(),
                    commonResponse.getData(),
                    commonResponse.getData());
        }

    }
}
