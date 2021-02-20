package top.xuxing.common.data.redis.pc.msg;

import java.util.concurrent.ExecutorService;

/**
 * 消费者接口
 * @author xhb
 * @date 2021/2/18
 */
public interface MsgConsumer {

    /**
     * topic
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
     * 消费成功
     * @param message
     */
    void onMessage(Object message);

    /**
     * 消费失败
     * @param message
     * @param e
     */
    void onError(Object message, Exception e);
}
