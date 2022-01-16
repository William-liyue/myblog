package com.myblog.service;

import com.myblog.entity.Type;

import java.util.List;

/**
 * 文章分类业务层接口
 * @author li192
 */
public interface TypeService {

    int saveType(Type name);

    int getTypeName(Type typename);

    Type getType(Integer id);

    List<Type> getAllType();

    List<Type> getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);
}
