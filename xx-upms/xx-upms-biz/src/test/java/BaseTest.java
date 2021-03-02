import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.cache.Util;
import top.xuxing.upms.AdminApplication;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
     *
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
        threadPoolExecutor.submit(() -> {
            while (BaseTest.run) {
                System.out.println("run..." + System.currentTimeMillis());
            }
        });
        TimeUnit.SECONDS.sleep(3);
        BaseTest.run = false;
        threadPoolExecutor.shutdown();
        boolean flag = threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("flag " + flag);
        if (threadPoolExecutor.isTerminated()) {
            System.out.println("pool is down");
        } else {
            System.out.println("pool not down");
        }
    }

    @Test
    public void timeUtil() {
        LocalDate now = LocalDate.now();
        LocalDateTime localDateTime = now.atStartOfDay();
        System.out.println("localDateTime = " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        ArrayList<String> times = new ArrayList<>();
        for (int i = 0; i <= 24; i++) {
            LocalDateTime currentHour = localDateTime.plusHours(i);
            times.add(currentHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
        }
        System.out.println("times = " + times);

        for (int i = 1; i <= 24; i++) {
            String currentHour = times.get(i);
            String previousHour = times.get(i - 1);
            System.out.println( previousHour+ " ~ " + currentHour);
        }

        BigDecimal a = new BigDecimal(3);
        BigDecimal b = new BigDecimal(11);
        BigDecimal divide = a.divide(b, 4, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide.doubleValue());
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(4);
        System.out.println(numberFormat.format(divide.doubleValue()));

        String timeStr = "2021022423";
        LocalDateTime parse = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));
        System.out.println("parse = " + parse.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String previousHour = "2021022816";
        LocalDateTime betweenStart = LocalDateTime.parse(previousHour, DateTimeFormatter.ofPattern("yyyyMMddHH"));
        String betweenStartStr = betweenStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime betweenEnd = betweenStart.plusHours(1);
        String betweenEndStr = betweenEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("betweenStartStr = " + betweenStartStr);
        System.out.println("betweenEndStr = " + betweenEndStr);
    }

}
