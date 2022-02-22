package com.myblog.mapper;

import com.myblog.model.entity.Relationships;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author li192
 */
@Mapper
public interface RelationshipMapper {

    /**
     * 根据mid来查询relationship
     * @param mid
     * @return
     */
    List<Relationships> getRelationshipByMid(@Param("mid") Integer mid);

    /**
     * 根据主键删除关系
     * @param cid
     * @param mid
     */
    void delRelationshipById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    /**
     * 保存关系
     * @param relationships
     */
    void saveRelationship(Relationships relationships);

    /**
     * 根据id查询关系是否存在
     * @param cid
     * @param mid
     * @return
     */
    Long countById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    void delRelationshipByCId(@Param("cid") Integer cid);
}
