package com.myblog.model.dto;

import lombok.Data;

import java.util.Map;

/**
 * description
 * @author li192
 */
@Data
public class IpBean {

    /**响应码**/
    private String code;

    /**数据封装**/
    private Map<String, String> data;

}
