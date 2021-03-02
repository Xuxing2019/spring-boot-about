package top.xuxing.xxgateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author xhb
 * @since 2021/2/27
 */
@Configuration
public class RateLimitConfig {

    /**
     * 根据路由限流
     * @return
     */
    @Bean
    @Primary
    public KeyResolver pathKyResolver(){
        return exchange -> {
            System.out.println("path = " + exchange.getRequest().getPath().toString());
            return Mono.just(exchange.getRequest().getPath().toString());
        };
    }
    /**
     * 根据路由限流
     * @return
     */
    @Bean
    public KeyResolver ipKyResolver(){
        return exchange -> {
            System.out.println("ip = " + Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
           return Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
        };
    }

    /**
     * 根据路由限流
     * @return
     */
    @Bean
    public KeyResolver userKyResolver(){
        return exchange -> {
            System.out.println("userId = " + Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst("userId")));
            return  Mono.just(Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst("userId")));
        };
    }

}
