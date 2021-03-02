package top.xuxing.xxtest.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.xxtest.XxTestApplication;
import javax.annotation.Resource;

/**
 * @author xhb
 * @since 2021/2/28
 */
@SpringBootTest(classes = XxTestApplication.class)
class TestControllerTest {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Test
    void test() {
        System.out.println("true = " + true);
        redisTemplate.opsForValue().set("testKey","testValue");
    }
}