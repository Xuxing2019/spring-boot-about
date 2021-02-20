package top.xuxing.common.data.redis.pc.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

import java.util.concurrent.ExecutorService;

/**
 * @author xhb
 * @date 2021/2/4
 */
public interface RedisQueueConfiguration {
    /**
     * topic
     * @return
     */
    String getConsumerTopic();
    /**
     * 设置消费设数线程数
     * @return
     */
    int getConsumerThreadNum();

    /**
     * 设置消费设数线程池
     * @return
     */
    ExecutorService getConsumerThreadPool();

    /**
     * 消费者
     * @return
     */
    MsgConsumer getConsumer();
}
