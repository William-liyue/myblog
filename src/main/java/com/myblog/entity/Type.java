package com.myblog.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类实体类
 * @author li192
 */
@Data
public class Type {

    private Integer id;
    private String name;
    private String typename;

    private List<Blog> blogs = new ArrayList<>();
}
