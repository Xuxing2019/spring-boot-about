package top.xuxing.common.data.redis.pc.container;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.redis.pc.config.RedisQueueConfiguration;
import top.xuxing.common.data.redis.pc.listener.RedisQueueListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xhb
 * @date 2021/2/5
 */
@Slf4j
public class RedisMqConsumerContainer {

    public static final Map<String, RedisQueueConfiguration> CONSUMER_MAP = new HashMap<>(16);

    private final Map<String, ExecutorService> executorServices = new HashMap<>(16);

    public void addConsumer(RedisQueueConfiguration redisQueueConfiguration, RedisTemplate<String, Object> redisTemplate) {
        String topic = redisQueueConfiguration.getConsumerTopic();
        if (ObjectUtil.isNull(executorServices.containsKey(topic))) {
            log.info("thread pool is null");
            throw new RuntimeException();
        }else{
            executorServices.put(topic, redisQueueConfiguration.getConsumerThreadPool());
        }
        if (CONSUMER_MAP.containsKey(topic)) {
            log.warn("queue={} exist", topic);
        }
        if (redisQueueConfiguration.getConsumer() == null) {
            log.warn("queue consumer map is mull");
        }
        CONSUMER_MAP.put(redisQueueConfiguration.getConsumerTopic(), redisQueueConfiguration);
        for (int i = 0; i < redisQueueConfiguration.getConsumerThreadNum(); i++) {
            this.executorServices.get(redisQueueConfiguration.getConsumerTopic()).submit(new RedisQueueListener(redisTemplate, redisQueueConfiguration.getConsumerTopic(), redisQueueConfiguration.getConsumer()));
        }
        log.info("redis queue topic={} listenerNum={} loading finished", redisQueueConfiguration.getConsumerTopic(), redisQueueConfiguration.getConsumerThreadNum());
    }

    public void destroy() {
        log.info("RedisQueueListener exiting...");
        this.executorServices.forEach((k,v)->{
            v.shutdown();
            while (!v.isTerminated()) {
                log.info("{} shutdown", k);
            }
        });
        log.info("RedisQueueListener exit...");
    }
}
