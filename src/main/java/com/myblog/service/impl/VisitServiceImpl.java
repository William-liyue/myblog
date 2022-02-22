package com.myblog.service.impl;

import com.myblog.mapper.VisitMapper;
import com.myblog.model.entity.Visit;
import com.myblog.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author li192
 */
@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitMapper visitMapper;

    @Override
    public Visit getCountById(Integer id) {
        return visitMapper.getCountById(id);
    }

    @Override
    public void updateCountById(Integer id) {
        visitMapper.updateCountById(id);
    }
}
