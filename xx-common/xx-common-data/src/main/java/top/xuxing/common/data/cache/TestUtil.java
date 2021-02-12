package top.xuxing.common.data.cache;

import org.springframework.context.annotation.Bean;

/**
 * @author xhb
 * @since 2021/2/12
 */
public class TestUtil {

    @Bean
    public Util util(){
        return new Util();
    }
}
