package com.myblog.model.dto;
import com.myblog.model.entity.Contents;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 文章归档
 * @author li192
 */
@Data
public class Archive implements Serializable {

    private static final long serialVersionUID = 1L;

    private String date_str;

    // 文章日期
    private String date;

    // 文章数量
    private String count;

    // 文章集合
    private List<Contents> articles;

}
