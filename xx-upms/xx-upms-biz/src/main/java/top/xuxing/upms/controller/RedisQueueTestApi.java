package top.xuxing.upms.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xuxing.common.data.redis.pc.msg.MsgConsumer;

/**
 * @author xhb
 * @date 2021/2/20
 */
@RestController
@AllArgsConstructor
@RequestMapping("test")
public class RedisQueueTestApi {

    private final RedisTemplate<String, Object> redisTemplate;

    private final MsgConsumer msgConsumer;

    @GetMapping("send/msg")
    public ResponseEntity sendRedisQueueMsg(){
        String consumerTopic = msgConsumer.getConsumerTopic();
        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForList().leftPush(consumerTopic, "red shit");
        }
        return ResponseEntity.ok("success");
    }
}
