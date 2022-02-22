package com.myblog.model.entity;

import lombok.Data;

/**
 * 附件
 * <p>
 * @author li192
 */
@Data
public class Attach {

    private Integer id;
    private String fname;
    private String ftype;
    private String fkey;
    private Integer authorId;
    private Integer created;

}
