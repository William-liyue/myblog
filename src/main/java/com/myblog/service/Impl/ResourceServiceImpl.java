package com.myblog.service.Impl;

import com.myblog.entity.Resource;
import com.myblog.mapper.ResourceMapper;
import com.myblog.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层资源接口实现类
 * @author li192
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> listResource() {
        return resourceMapper.listResource();
    }

    @Override
    public int saveResource(Resource resource) {
        return resourceMapper.saveResource(resource);
    }

    @Override
    public Resource getResource(Long id) {
        return resourceMapper.getResource(id);
    }

    @Override
    public Resource getResource(String resourceName) {
        return resourceMapper.getResource(resourceName);
    }

    @Override
    public int updateResource(Resource resource) {
        return resourceMapper.updateResource(resource);
    }

    @Override
    public void deleteResource(Long id) {
        resourceMapper.deleteResource(id);
    }
}
