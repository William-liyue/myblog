package com.myblog.mapper;

import com.myblog.model.entity.Metas;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author li192
 */
@Mapper
public interface MetaMapper {

    /**
     * 根据类型来查询友链
     * @param type
     * @return
     */
    List<Metas> getMetasByType(@Param("type") String type);

    /**
     * 更新meta
     * @param metas
     */
    void updateMeta(Metas metas);

    /**
     * 新增meta
     * @param metas
     */
    void saveMeta(Metas metas);

    /**
     * 根据id删除meta
     * @param mid
     */
    void delMetaById(@Param("mid") Integer mid);

    /**
     * 根据id来查询meta
     * @param mid
     * @return
     */
    Metas getMetaById(@Param("mid") Integer mid);

    /**
     * 根据条件来查询meta
     * @param type
     * @param name
     * @return
     */
    List<Metas> selectMetaListByConditions(@Param("type") String type, @Param("name") String name);

    /**
     * @param type
     * @return
     */
    List<Metas> getMetasBySql(@Param("type") String type);

    /**
     * 前台标签页使用
     * @param type
     * @param name
     * @return
     */
    Metas getMeta(@Param("type") String type, @Param("name") String name);

    /**
     * 查询标签下的文章数量
     * @param mid
     * @return
     */
    int countWithSql(Integer mid);
}
