package com.myblog.vo;

import lombok.Data;

/**
 * 推荐博客数据实体类
 * @author li192
 */
@Data
public class RecommendBlog {

    private Long id;
    private String title;
    private String firstPicture;
    private boolean recommend;
}
