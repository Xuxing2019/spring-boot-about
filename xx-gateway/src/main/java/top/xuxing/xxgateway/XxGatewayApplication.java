package top.xuxing.xxgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Administrator
 */
@EnableDiscoveryClient
@SpringBootApplication
public class XxGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxGatewayApplication.class, args);
    }

}
