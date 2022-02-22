package com.myblog.model.entity;

import lombok.Data;

/**
 * description 用户权限类
 * @author li192
 */

@Data
public class Permission extends BaseEntity {

    private Integer id;

    /** url地址 **/
    private String url;

    /** url描述 **/
    private String description;

}
