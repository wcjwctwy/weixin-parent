package cn.sf80.weixin.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//配置安全策略
                .antMatchers("/").permitAll()//定义/请求不需要验证
                .anyRequest().authenticated()//其余的所有请求都需要验证
                .and()
                .logout()
                .permitAll()//定义logout不需要验证
                .and()
                .formLogin();//使用form表单登录
    }
}
