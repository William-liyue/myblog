package com.myblog.service.Impl;

import com.myblog.entity.Type;
import com.myblog.mapper.TypeMapper;
import com.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章分类业务层接口实现类
 * @author li192
 */
@Controller
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Override
    public int getTypeName(Type typename) {
        return typeMapper.getTypeName(typename);
    }

    @Override
    public Type getType(Integer id) {
        return typeMapper.getType(id);
    }

    @Transactional
    @Override
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    @Override
    public List<Type> getAllTypeAndBlog() {
        return typeMapper.getAllTypeAndBlog();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }
}
