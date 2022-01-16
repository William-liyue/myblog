package com.myblog.vo;

import com.myblog.entity.Type;
import lombok.Data;

import java.util.Date;

/**
 * 显示数据实体类
 * @author li192
 */
@Data
public class BlogQuery {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String description;
    private String recommend;
    private boolean published;
    private Date updateTime;
    private Type type;
    private Long typeId;
}
