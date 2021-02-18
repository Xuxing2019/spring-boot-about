import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.cache.Util;
import top.xuxing.upms.AdminApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xhb
 * @since 2021/2/12
 */
@SpringBootTest(classes = AdminApplication.class)
public class BaseTest {

    private static boolean run = true;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    Util util;

    @Test
    public void redisComponent() {
        redisTemplate.opsForValue().set("key", "value");
    }

    @Test
    public void utilTest() {
        util.util();
    }

    /**
     * 线程池测试
     * shutdown 拒接接收新任务，等待在线任务结束再关闭线程池(任务不结束无法关闭线程池)
     * shutdownNow 拒接接收新任务，将线程池设置stop状态，在特定时机结束任务
     * isTerminated 所有任务结束 返回true
     * @throws InterruptedException
     */
    @Test
    public void threadPool() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                30,
                3000,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.submit(()->{
           while (BaseTest.run){
               System.out.println("run..." + System.currentTimeMillis());
           }
        });
        TimeUnit.SECONDS.sleep(3);
        BaseTest.run = false;
        threadPoolExecutor.shutdown();
        boolean flag = threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("flag " + flag);
        if (threadPoolExecutor.isTerminated()){
            System.out.println("pool is down");
        }else{
            System.out.println("pool not down");
        }
    }
}
