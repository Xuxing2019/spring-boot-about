package top.xuxing.upms.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.redis.pc.config.RedisQueueConfiguration;
import top.xuxing.common.data.redis.pc.container.RedisMqConsumerContainer;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

import java.util.List;

/**
 * @author xhb
 * @date 2021/2/18
 */
@Slf4j
@AllArgsConstructor
public class InitRedisQueueConfig {

    private final List<MsgConsumer> msgConsumers;

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisMqConsumerContainer redisMqConsumerContainer;

    private void init() {
        log.info("init...");
        msgConsumers.forEach(msgConsumer -> {
            redisMqConsumerContainer.addConsumer(RedisQueueConfiguration.builder().queue(msgConsumer.getConsumerTopic()).consumer(msgConsumer).build(), msgConsumer.getConsumerThreadPool(), redisTemplate);
        });
    }
}
