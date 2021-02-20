package top.xuxing.common.data.redis.pc.msg;

import java.util.concurrent.ExecutorService;

/**
 * 消费者接口
 * @author xhb
 * @date 2021/2/18
 */
public interface MsgConsumer {

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
