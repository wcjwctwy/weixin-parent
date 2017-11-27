package cn.sf80.weixin.manager.config;

import cn.sf80.weixin.manager.pojo.WxUrlCard;
import cn.sf80.weixin.manager.pojo.WxUrlMaterial;
import cn.sf80.weixin.spring.WxUserInfoHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({WxUrlMaterial.class, WxUrlCard.class})
@PropertySource("file:/usr/weixin/config/wxurl.properties")
public class ManagerConfig {

    @Bean
    public WxUserInfoHandler getWxUserInfoHandler(){
        return wxUserInfo -> System.out.println("用户名称："+wxUserInfo.getNickname());
    }

}
