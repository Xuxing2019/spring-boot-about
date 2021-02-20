package top.xuxing.common.data.redis.pc.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.cache.RedisTemplateConfig;
import top.xuxing.common.data.redis.pc.container.RedisMqConsumerContainer;

/**
 * @author xhb
 * @date 2021/2/18
 */
@AutoConfigureBefore(RedisTemplateConfig.class)
public class RedisQueueConfig {

    @Bean
    public RedisMqConsumerContainer redisMqConsumerContainer(){
        return new RedisMqConsumerContainer();
    }
}
