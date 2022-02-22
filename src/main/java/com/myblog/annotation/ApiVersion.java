package com.myblog.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;


/**
 * created by liyuehui
 * @author li192
 * @version 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

    /**
     * 版本号
     */
    int[]value();

}