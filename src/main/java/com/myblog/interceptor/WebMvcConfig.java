package com.myblog.interceptor;

import com.myblog.utils.TaleUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Description:向mvc中添加自定义组件
 * @author li192
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BaseInterceptor baseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
        registry.addInterceptor(annotationInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public AnnotationInterceptor annotationInterceptor() {
        return new AnnotationInterceptor();
    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + TaleUtils.getUplodFilePath() + "upload/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
