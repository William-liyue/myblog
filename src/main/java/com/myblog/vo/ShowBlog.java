package com.myblog.vo;

import lombok.Data;

import java.util.Date;

/**
 * 编辑修改文章实体类
 * @author li192
 */
@Data
public class ShowBlog {

    private Long id;
    private String title;
    private String content;
    private Long typeId;
    private Long tagId;
    private String firstPicture;
    private String description;
    private String recommend;
    private boolean published;
    private boolean shareStatement;
    private boolean appreciation;
    private boolean commentabled;
    private Date createTime;
    private Date updateTime;

}
