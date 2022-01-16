package com.myblog.entity;

import lombok.Data;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客实体类
 * @author li192
 */
@Data
public class Blog {
    private Integer id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;

    //    评论计数
    private Integer commentCount;

    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;

    private Integer typeId;
    private Integer userId;
    private Integer tagId;
    private String description;
    private Type type;
    private User user;
    private List<Comment> comments = new ArrayList<>();

}