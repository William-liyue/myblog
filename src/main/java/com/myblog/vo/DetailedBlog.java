package com.myblog.vo;

import lombok.Data;

import java.util.Date;

/**
 * 删除数据实体
 * @author li192
 */
@Data
public class DetailedBlog {

    private Long id;
    private String firstPicture;
    private String flag;
    private String title;
    private String content;

    private Integer views;
    private Integer commendCount;
    private Date updateTime;
    private boolean recommentabled;
    private boolean shareStatement;
    private boolean appreciation;
    private String nickname;
    private String avatar;

    /**Type*/
    private String typename;
}
