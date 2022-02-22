package com.myblog.service;


import com.myblog.model.entity.Metas;

import java.util.List;

/**
 * Description: 友情链接接口
 * @author li192
 */
public interface MetaService {

    /**
     * 根据类型来获取友链
     * @param type
     * @return
     */
    List<Metas> getMetasByType(String type);

    /**
     * 根据id删除meta
     * @param mid
     */
    void delMetaById(Integer mid);

    /**
     * 根据id来获取meta
     * @param id
     * @return
     */
    Metas getMetaById(Integer id);

    /**
     * 保存项目
     * @param type
     * @param cname
     * @param mid
     */
    void saveMeta(String type, String cname, Integer mid);

    /**
     * 根据条件查metas
     * @param type
     * @param orderby
     * @param limit
     * @return
     */
    List<Metas> getMetaList(String type, String orderby, Integer limit);

    /**
     * 前台标签页使用
     * @param type
     * @param name
     * @return
     */
    Metas getMeta(String type, String name);

    /**
     * 保存分类或者标签
     * @param cid
     * @param tags
     * @param type
     */
    void saveMetas(Integer cid, String tags, String type);
}
