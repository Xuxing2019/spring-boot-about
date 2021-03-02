package top.xuxing.xxgateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 自定义过滤器
 * @author xhb
 * @since 2021/2/28
 */
@Component
public class AuthFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("token");
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        if (StrUtil.isEmpty(token)){
            HashMap<String, Object> resultBody = new HashMap<>(4);
            resultBody.put("code", 1);
            resultBody.put("msg", "invalid token");
            ResponseEntity<HashMap<String, Object>> resultJson = ResponseEntity.ok(resultBody);
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(JSON.toJSONBytes(resultJson));
            return exchange.getResponse().writeWith(Flux.just(dataBuffer));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
