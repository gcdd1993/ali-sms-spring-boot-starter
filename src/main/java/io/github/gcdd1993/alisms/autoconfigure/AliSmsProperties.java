package io.github.gcdd1993.alisms.autoconfigure;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ali sms 属性装配定义
 *
 * @author gaochen
 * Created on 2019/6/6.
 */
@Data
@ConfigurationProperties(prefix = "ali.sms")
public class AliSmsProperties {

    private AccessKey accessKey;

    /**
     * @param domain ali sms 服务地址
     * @return ali sms 服务地址 <a href="https://help.aliyun.com/document_detail/101511.html?spm=a2c4g.11186623.6.605.6bd17ce8eBGc3Y">帮助文档-服务地址</a>
     */
    private String domain = "dysmsapi.aliyuncs.com";

    /**
     * @param version ali sms 版本号
     * @return ali sms 版本号
     */
    private String version = "2017-05-25";

    /**
     * @param action ali sms 发送短信
     * @return ali sms 发送短信
     */
    private String action = "SendSms";

    /**
     * @param regionId ali sms 地域
     * @return ali sms 地域
     */
    private String regionId = "cn-shanghai";

    /**
     * @param signName 短信签名名称。一般来说，系统只使用一个签名，所以可以放在配置文件中
     * @return 短信签名名称。请在控制台签名管理页面签名名称一列查看。
     */
    private String signName;

    /**
     * AccessKey
     * <a href="https://help.aliyun.com/document_detail/101339.html?spm=a2c4g.11186623.6.604.cc523e2cIaDw2h">帮助文档-AccessKey</a>
     */
    @Data
    @Getter
    @NoArgsConstructor
    public static class AccessKey {

        /**
         * @param id AccessKeyId用于标识用户。
         * @return AccessKeyId用于标识用户。
         */
        private String id;

        /**
         * @param secret AccessKeySecret是用来验证用户的密钥。AccessKeySecret必须保密。
         * @return AccessKeySecret是用来验证用户的密钥。AccessKeySecret必须保密。
         */
        private String secret;
    }

}
