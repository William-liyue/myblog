package com.myblog.service;


import com.myblog.model.entity.Visit;

/**
 * @author li192
 */
public interface VisitService {

    Visit getCountById(Integer id);

    void updateCountById(Integer id);

}
