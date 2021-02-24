package top.xuxing.upms.queue.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import top.xuxing.common.data.redis.pc.config.RedisQueueConfiguration;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @author xhb
 * @since 2021/2/21
 */
@Component
@AllArgsConstructor
@EnableConfigurationProperties({DogRedisQueueProperties.class})
public class DogRedisQueueConfiguration implements RedisQueueConfiguration {

    @Qualifier("dogConsumer")
    private final MsgConsumer msgConsumer;

    private final DogRedisQueueProperties dogRedisQueueProperties;

    @Override
    public String getConsumerTopic() {
        return dogRedisQueueProperties.getTopic();
    }

    @Override
    public int getConsumerThreadNum() {
        return dogRedisQueueProperties.getThreadNum();
    }

    @Override
    public ExecutorService getConsumerThreadPool() {
        return new ThreadPoolExecutor(dogRedisQueueProperties.getCorePoolSize(),
                dogRedisQueueProperties.getMaximumPoolSize(),
                dogRedisQueueProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(dogRedisQueueProperties.getQueueSize()));
    }

    @Override
    public MsgConsumer getConsumer() {
        return this.msgConsumer;
    }
}
