package top.xuxing.common.data.redis.pc.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.redis.pc.container.RedisMqConsumerContainer;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;
import java.util.concurrent.TimeUnit;

/**
 * @author xhb
 * @date 2021/2/4
 */
@Slf4j
@AllArgsConstructor
public class RedisQueueListener implements Runnable {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String topic;

    private final MsgConsumer msgConsumer;

    @Override
    public void run() {
        while (RedisMqConsumerContainer.CONSUMER_MAP.containsKey(topic)) {
            try {
                Object msg = redisTemplate.opsForList().rightPop(topic, 30, TimeUnit.SECONDS);
                if (msg != null) {
                    try {
                        msgConsumer.onMessage(msg);
                    } catch (Exception e) {
                        msgConsumer.onError(msg, e);
                    }
                }
            } catch (QueryTimeoutException ignored) {
            } catch (Exception e) {
                if (RedisMqConsumerContainer.CONSUMER_MAP.containsKey(topic)) {
                    log.error("异常 topic:{}", topic, e);
                } else {
                    log.info("RedisQueueListener 退出...topic:{}", topic);
                }
            }
        }
    }
}
