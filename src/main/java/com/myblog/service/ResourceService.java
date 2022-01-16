package com.myblog.service;

import com.myblog.entity.Resource;

import java.util.List;

/**
 * 业务层资源接口
 * @author li192
 */
public interface ResourceService {

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
