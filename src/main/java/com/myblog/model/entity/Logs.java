package com.myblog.model.entity;

import lombok.Data;

/**
 * 日志记录
 * @author li192
 */
@Data
public class Logs {

    // 项目主键
    private Integer id;

    // 产生的动作
    private String action;

    // 产生的数据
    private String data;

    // 发生人id
    private Integer authorId;

    // 日志产生的ip
    private String ip;

    // 日志创建时间
    private Integer created;

}