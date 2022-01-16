package com.myblog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章实体内
 * @author li192
 */
@Data
public class Article implements Serializable {

    private Integer id;
    private String title;           // 文章标题
    private String content;         // 文章内容
    private String remark;          // 文章简介
    private Integer isTop;           // 文章置顶
    private Integer status;          // 是否草稿 0 草稿 1 发布
    private Integer isReprint;       // 是否原创 1 原创 2 转载
    private Long browsNum;        // 文章浏览数
    private Integer likeNum;         // 文章点赞数
    private Integer comments;         // 文章评论数
    private String tags;              // 文章标签列表
    private Integer typeId;             // 文章类型id
    private String author;              // 文章作者
    private Integer userId;             // 文章作者id
    private String coverImage;          // 文章缩略图
    private Date createTime;            // 文章创建时间
    private Date updateTime;            // 文章更新时间
    private Integer isRecommend;        // 文章是否推荐 0 不推荐 1 推荐 默认不推荐

    private String category;            // 分类名称
    private String isNew;               // 文章是否最新的
    private String createAt;            // date类型转换成string

    private String cover_image;         // 文章缩略图 用于分享
    private Date create_time;           // 文章创建时间 后期将删除
    private Integer brows_num;          // 文章浏览数 后期将删除
}
