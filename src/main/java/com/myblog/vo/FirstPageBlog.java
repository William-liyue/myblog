package com.myblog.vo;

import com.myblog.entity.Blog;
import lombok.Data;

import java.util.Date;

/**
 * 博客首页数据实体
 * @author li192
 */
@Data
public class FirstPageBlog {

    /**Blog*/
    private Integer id;
    private String title;
    private String content;
    private String typeId;
    private String firstPicture;
    private String recommend;
    private String description;
    private Integer views;
    private Integer commentCount;
    private Date updateTime;

     /**Type*/
    private String typename;

     /**User*/
    private String nickname;

     /**Nickname*/
    private String avatar;
}
