package top.xuxing.xxtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 10420
 */
@EnableDiscoveryClient
@SpringBootApplication
public class XxTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxTestApplication.class, args);
    }

}
