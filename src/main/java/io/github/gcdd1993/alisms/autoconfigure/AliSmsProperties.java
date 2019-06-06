package io.github.gcdd1993.alisms.autoconfigure;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author gaochen
 * @date 2019/6/6
 */
@Data
@ConfigurationProperties(prefix = "ali.sms")
public class AliSmsProperties {

    private AccessKey accessKey;

    /**
     * ali sms 服务地址
     * <a href="https://help.aliyun.com/document_detail/101511.html?spm=a2c4g.11186623.6.605.6bd17ce8eBGc3Y">帮助文档-服务地址</a>
     */
    private String domain = "dysmsapi.aliyuncs.com";

    /**
     * ali sms 版本号
     */
    private String version = "2017-05-25";

    /**
     * ali sms 发送短信
     */
    private String action = "SendSms";

    /**
     * ali sms 地域
     */
    private String regionId = "cn-shanghai";

    /**
     * AccessKey
     * <a href="https://help.aliyun.com/document_detail/101339.html?spm=a2c4g.11186623.6.604.cc523e2cIaDw2h">帮助文档-AccessKey</a>
     */
    @Data
    @Getter
    @NoArgsConstructor
    public static class AccessKey {

        /**
         * access key id
         * <p>
         * AccessKeyId用于标识用户。
         * </p>
         */
        private String id;

        /**
         * access key secret
         * <p>
         * AccessKeySecret是用来验证用户的密钥。AccessKeySecret必须保密。
         * </p>
         */
        private String secret;
    }

}
