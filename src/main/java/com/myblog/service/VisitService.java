package com.myblog.service;

import com.myblog.model.entity.Visit;

public interface IVisitService {

    Visit getCountById(Integer id);

    void updateCountById(Integer id);

}
