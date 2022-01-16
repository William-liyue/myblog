package com.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 指定拦截内容的配置类
 * 实现WebMvcConfigurer接口，然后根据需要实现对应的方法即可，
 * 建议使用第一种，因为这种方式的自定义配置不会导致SpringBoot的默认配置失效，
 * 可以与我们的自定义配置共存
 * @author li192
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
