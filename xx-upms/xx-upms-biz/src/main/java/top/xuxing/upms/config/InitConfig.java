package top.xuxing.upms.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.redis.pc.config.RedisQueueConfiguration;
import top.xuxing.common.data.redis.pc.container.RedisMqConsumerContainer;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

import java.util.List;

/**
 * @author xhb
 * @date 2021/2/19
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class InitConfig {

    private final List<RedisQueueConfiguration> redisQueueConfigurations;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMqConsumerContainer redisMqConsumerContainer;

    @Bean(initMethod = "init")
    public InitRedisQueueConfig initRedisQueueConfig() {
        log.info("init initRedisQueueConfig...");
        return new InitRedisQueueConfig(redisQueueConfigurations, redisTemplate, redisMqConsumerContainer);
    }
}
