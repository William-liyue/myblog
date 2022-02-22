package com.myblog.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统设置的返回参数
 * @author li192
 */
@Data
public class BackResponseBo implements Serializable {

    private String attachPath;

    private String themePath;

    private String sqlPath;

    private String typePath;

}
