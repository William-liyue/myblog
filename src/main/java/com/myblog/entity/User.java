package com.myblog.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * 用户实体类
 * @author li192
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String avatar;
    private Integer status;         // 状态
    private String remark;          // 备注
    private Date createTime;
    private Date updateTime;

    public int getStatus() {
        return 0;
    }
}
