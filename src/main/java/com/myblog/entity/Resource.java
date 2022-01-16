package com.myblog.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源实体类
 * @author li192
 */
@Data
public class Resource {

    private Long id;
    private String resourceName;
    private String resourceAddress;
    private String firstResource;
    private String secondResource;

    // 博客列表
    private List<Blog> blogs = new ArrayList<>();
}
