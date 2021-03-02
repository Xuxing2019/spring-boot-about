package top.xuxing.upms.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xuxing.common.data.redis.pc.config.RedisQueueConfiguration;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author xhb
 * @date 2021/2/20
 */
@RestController
@AllArgsConstructor
@RequestMapping("test")
public class RedisQueueTestApi {

    private final RedisTemplate<String, Object> redisTemplate;

    @Qualifier("dogRedisQueueConfiguration")
    private final RedisQueueConfiguration redisQueueConfiguration;

    @GetMapping("send/msg")
    public ResponseEntity<String> sendRedisQueueMsg(){
        String consumerTopic = redisQueueConfiguration.getConsumerTopic();
        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForList().leftPush(consumerTopic, "red shit " + i);
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("gateway")
    public ResponseEntity<String> gatewayTest(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            System.out.print(headerNames.nextElement() + " ");
        }
        System.out.println("");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            System.out.print(parameterNames.nextElement() + " ");
        }
        System.out.println("");
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public ResponseEntity<String> defaultPath(){
        return ResponseEntity.ok("ok");
    }
}
