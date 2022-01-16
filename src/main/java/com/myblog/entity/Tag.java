package com.myblog.entity;

import lombok.Data;

/**
 * @author li192
 */
@Data
public class Tag {

    private Integer id;
    private String name;        // 标签名称
    private Integer articleNum; // 标签的文章数
}
