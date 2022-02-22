package com.myblog.mapper;

import com.myblog.model.entity.Visit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author li192
 */
@Mapper
public interface VisitMapper {

    Visit getCountById(@Param("id") Integer id);

    void updateCountById(@Param("times") Integer times);

}
