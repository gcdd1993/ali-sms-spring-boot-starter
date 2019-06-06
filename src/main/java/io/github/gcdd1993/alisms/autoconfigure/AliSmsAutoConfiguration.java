package io.github.gcdd1993.alisms.autoconfigure;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * ali sms 自动配置
 *
 * @author gaochen
 * @date 2019/6/6
 */
@Configuration
@ConditionalOnClass({
        IAcsClient.class,
        CommonRequest.class,
        CommonResponse.class
})
@EnableConfigurationProperties({AliSmsProperties.class})
public class AliSmsAutoConfiguration {

    private final AliSmsProperties properties;

    public AliSmsAutoConfiguration(AliSmsProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public IAcsClient buildAcsClient() {
        Assert.notNull(properties.getRegionId(), "regionId 不能为空");
        Assert.notNull(properties.getAccessKey(), "AccessKey 不能为空");
        Assert.notNull(properties.getAccessKey().getId(), "AccessKey Id 不能为空");
        Assert.notNull(properties.getAccessKey().getSecret(), "AccessKey Secret 不能为空");

        DefaultProfile profile = DefaultProfile.getProfile(properties.getRegionId(),
                properties.getAccessKey().getId(),
                properties.getAccessKey().getSecret());

        return new DefaultAcsClient(profile);

    }

}
