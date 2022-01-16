package com.myblog.entity;

import lombok.Data;

import java.util.Date;

/**
 * 友链控制台
 * @author li192
 */
@Data
public class FriendLink {

    private Long id;
    private String blogName;
    private String pictureAddress;
    private String blogAddress;
    private Date createTime;
    private String description;

    /**分类名称*/
    private String typename;

    /**用户名*/
    private String nickname;

    /**用户头像*/
    private String avatar;
}
