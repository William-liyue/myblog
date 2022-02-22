package com.myblog.model.entity;

import lombok.Data;

/**
 * Description: 友情链接元数据
 * @author li192
 */
@Data
public class Metas {

    // 项目主键
    private Integer mid;
    // 名称
    private String name;
    // 项目缩略名
    private String slug;
    // 项目类型
    private String type;
    // 选项描述
    private String description;
    // 项目排序
    private Integer sort;
    // 父级
    private Integer parent;

}