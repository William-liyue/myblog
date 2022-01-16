package com.myblog.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author li192
 */
@Data
public class Page<T> implements Serializable {

    // 总的页数
    private int totalPage;
    // 当前页
    private int currentPage;
    // 查询存储结果集
    private List<T> data;

    public Page() {

    }

    public Page(List<T> data, int totalPage) {
        this.data = data;
        this.totalPage = totalPage;
    }
}
