package top.xuxing.common.data.redis.pc.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import top.xuxing.common.data.redis.pc.container.RedisMqConsumerContainer;

/**
 * @author xhb
 * @date 2021/2/18
 */
public class RedisQueueConfig {

    @Bean
    public RedisMqConsumerContainer redisMqConsumerContainer(){
        return new RedisMqConsumerContainer();
    }
}
