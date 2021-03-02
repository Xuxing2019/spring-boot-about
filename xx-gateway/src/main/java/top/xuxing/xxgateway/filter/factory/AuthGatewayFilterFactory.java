package top.xuxing.xxgateway.filter.factory;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.stereotype.Component;
import top.xuxing.xxgateway.filter.AuthFilter;

/**
 * @author xhb
 * @since 2021/2/28
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    public AuthGatewayFilterFactory() {
        super(AuthGatewayFilterFactory.Config.class);
    }
    @Override
    public GatewayFilter apply(AuthGatewayFilterFactory.Config config) {
        return new AuthFilter();
    }

    @Getter
    @Setter
    public static class Config {
        private String template;
    }
}
