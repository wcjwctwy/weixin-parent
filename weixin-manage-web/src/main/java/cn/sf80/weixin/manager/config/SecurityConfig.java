package cn.sf80.weixin.manager.config;

import cn.sf80.weixin.common.utils.MD5Util;
import cn.sf80.weixin.spring.pojo.WxPlatformConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConfigurationProperties(prefix = "security.user")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String name;

    private String password;

    @Autowired
    private WxPlatformConfig wxPlatformConfig;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(name).password(password).roles("USER").and().passwordEncoder(passwordEncoder); //user Details Service验证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//配置安全策略
                .antMatchers("/**"+wxPlatformConfig.getPath()+"/**").permitAll()//定义/请求不需要验证
                .anyRequest().authenticated()//其余的所有请求都需要验证
                .and()
                .logout()
                .permitAll()//定义logout不需要验证
                .and()
                .formLogin()
                ;//使用form表单登录
        http.csrf().disable();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
