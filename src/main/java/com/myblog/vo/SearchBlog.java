package com.myblog.vo;

import lombok.Data;

/**
 * 搜索博客管理列表
 * @author li192
 */
@Data
public class SearchBlog {

    private Long id;
    private String name;
    private String title;
    private Long typeId;
}
