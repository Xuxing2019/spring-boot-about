package top.xuxing.upms.queue.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xhb
 * @since 2021/2/21
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "xx.redis.queue")
public class DogRedisQueueProperties {

    private String topic;

    private int threadNum;

    private int corePoolSize;

    private int maximumPoolSize;

    private int keepAliveTime;

    private int queueSize;
}
