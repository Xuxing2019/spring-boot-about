import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.xuxing.common.data.cache.Util;
import top.xuxing.upms.AdminApplication;

/**
 * @author xhb
 * @since 2021/2/12
 */
@SpringBootTest(classes = AdminApplication.class)
public class BaseTest {

    @Autowired
    @Qualifier("xxRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    Util util;

    @Test
    public void redisComponent() {
        System.out.println("xxx");
        redisTemplate.opsForValue().set("key", "vvvv");
    }

    @Test
    public void utilTest(){
        util.util();
    }
}
