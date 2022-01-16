package com.myblog.mapper;

import com.myblog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章分类1持久层接口
 * @author li192
 */
@Mapper
@Repository
public interface TypeMapper {

    /**新增保存分类*/
    int saveType(Type type);

    /**根据名称查询分类*/
    Type getTypeByName(String name);

    /**根据分类名称分类*/
    int getTypeName(Type typename);

    /**根据id查询分类*/
    Type getType(Integer id);

    /**查询所有分类*/
    List<Type> getAllType();

    /**查询所有分类的博客*/
    List<Type> getAllTypeAndBlog();

    /**编辑修改分类*/
    int updateType(Type type);

    /**删除分类*/
    void deleteType(Long id);
}
