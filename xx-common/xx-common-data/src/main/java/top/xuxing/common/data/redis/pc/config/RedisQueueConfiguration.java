package top.xuxing.common.data.redis.pc.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

/**
 * @author xhb
 * @date 2021/2/4
 */
@Getter
@Setter
@Builder
public class RedisQueueConfiguration {
    /**
     * 队列名称
     */
    private String queue;
    /**
     * 消费者
     */
    private MsgConsumer consumer;
}
