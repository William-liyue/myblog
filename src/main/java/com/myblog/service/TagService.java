package com.myblog.service;

import com.myblog.entity.Tag;

import java.util.List;

/**
 * @author li192
 */
public interface TagService {

    void saveTags(String tags);

    boolean saveTags(Tag tag);

    List<Tag> findAll();

    String findTagByName(String name);

    void updateTagCount(String name);

    List<Tag> findByIds(String tags);
}
