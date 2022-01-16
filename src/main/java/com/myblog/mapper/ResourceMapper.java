package com.myblog.mapper;

import com.myblog.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源持久层接口
 * @author li192
 */
@Mapper
@Repository
public interface ResourceMapper {

    /**查询资源*/
    List<Resource> listResource();
    /**保存资源*/
    int saveResource(Resource resource);

    /**根据id查询资源*/
    Resource getResource(Long id);

    /**根据名称查询资源*/
    Resource getResource(String resourceName);

    /**编辑资源*/
    int updateResource(Resource resource);

    /**删除资源*/
    void deleteResource(Long id);
}
