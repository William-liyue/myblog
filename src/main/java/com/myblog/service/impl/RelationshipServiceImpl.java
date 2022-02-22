package com.myblog.service.impl;

import com.myblog.mapper.RelationshipMapper;
import com.myblog.model.entity.Relationships;
import com.myblog.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li192
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Override
    public List<Relationships> getRelationshipById(Integer cid, Integer mid) {
//        if (cid != null) {
//            criteria.andCidEqualTo(cid);
//        }
//        if (mid != null) {
//            criteria.andMidEqualTo(mid);
//        }
        return relationshipMapper.getRelationshipByMid(mid);
    }

    @Override
    public void delRelationshipById(Integer cid, Integer mid) {
        relationshipMapper.delRelationshipById(cid, mid);
    }

    @Override
    public void saveRelationship(Relationships relationships) {
        relationshipMapper.saveRelationship(relationships);
    }

    @Override
    public Long countById(Integer cid, int mid) {
        return relationshipMapper.countById(cid, mid);
    }

    @Override
    public void delRelationshipByCId(Integer cid) {
        relationshipMapper.delRelationshipByCId(cid);
    }
}
