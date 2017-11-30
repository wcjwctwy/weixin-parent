package cn.sf80.weixin.manager.config;

import cn.sf80.weixin.common.utils.MD5Util;
import cn.sf80.weixin.manager.pojo.WxUrlCard;
import cn.sf80.weixin.manager.pojo.WxUrlMaterial;
import cn.sf80.weixin.spring.WxUserInfoHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties({WxUrlMaterial.class, WxUrlCard.class})
@PropertySource("classpath:/config/wxurl.properties")
public class ManagerConfig {

    @Bean
    public WxUserInfoHandler getWxUserInfoHandler(){
        return wxUserInfo -> System.out.println("用户名称："+wxUserInfo.getNickname());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {

                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(encode(rawPassword));
            }};
    }

}
