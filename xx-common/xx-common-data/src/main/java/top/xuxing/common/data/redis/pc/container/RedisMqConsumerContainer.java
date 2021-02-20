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

    public void addConsumer(RedisQueueConfiguration redisQueueConfiguration, ExecutorService executorService, RedisTemplate<String, Object> redisTemplate) {
        if (ObjectUtil.isNull(executorServices.containsKey(redisQueueConfiguration.getQueue()))) {
            log.info("thread pool is null");
            throw new RuntimeException();
        }else{
            executorServices.put(redisQueueConfiguration.getQueue(), executorService);
        }
        if (CONSUMER_MAP.containsKey(redisQueueConfiguration.getQueue())) {
            log.warn("queue={} exist", redisQueueConfiguration.getQueue());
        }
        if (redisQueueConfiguration.getConsumer() == null) {
            log.warn("queue consumer map is mull");
        }
        CONSUMER_MAP.put(redisQueueConfiguration.getQueue(), redisQueueConfiguration);
        for (int i = 0; i < redisQueueConfiguration.getConsumer().getConsumerThreadNum(); i++) {
            this.executorServices.get(redisQueueConfiguration.getQueue()).submit(new RedisQueueListener(redisTemplate, redisQueueConfiguration.getQueue(), redisQueueConfiguration.getConsumer()));
        }
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
