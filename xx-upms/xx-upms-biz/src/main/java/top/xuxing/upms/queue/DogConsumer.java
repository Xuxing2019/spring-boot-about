package top.xuxing.upms.queue;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xhb
 * @date 2021/2/18
 */
@Slf4j
@Getter
@Setter
@Component("dogConsumer")
public class DogConsumer implements MsgConsumer {

    @Override
    public void onMessage(Object message) {
        log.info("msg={}", message);
    }

    @Override
    public void onError(Object message, Exception e) {
        log.info("msg={} exceptionMsg={}", message, e.getMessage());
    }
}
