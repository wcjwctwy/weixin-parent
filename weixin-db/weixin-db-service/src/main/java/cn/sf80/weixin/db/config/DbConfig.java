package cn.sf80.weixin.db.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/db.properties")
public class DbConfig {
}
