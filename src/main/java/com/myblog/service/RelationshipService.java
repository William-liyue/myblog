package com.myblog.service;


import com.myblog.model.entity.Relationships;

import java.util.List;

/**
 * @author li192
 */
public interface RelationshipService {

    /**
     * 根据id搜索
     * @param cid
     * @param mid
     * @return
     */
    List<Relationships> getRelationshipById(Integer cid, Integer mid);

    /**
     * 根据id删除关系
     * @param cid
     * @param mid
     */
    void delRelationshipById(Integer cid, Integer mid);

    /**
     * 保存关系
     * @param relationships
     */
    void saveRelationship(Relationships relationships);

    /**
     * 根据id来查询是否存在
     * @param cid
     * @param mid
     * @return
     */
    Long countById(Integer cid, int mid);

    void delRelationshipByCId(Integer cid);
}
