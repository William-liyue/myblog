package com.myblog.service.Impl;

import com.myblog.entity.Tag;
import com.myblog.mapper.TagMapper;
import com.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li192
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void saveTags(String tags) {
        String[] tagNameArray = tags.split(",");
        for (String name : tagNameArray) {
            String mTag = findTagByName(name);
            if (null == mTag) {
                Tag tag =new Tag();
                tag.setName(name);
                tag.setArticleNum(1);
                saveTags(tag);
            } else {
                updateTagCount(name);
            }
        }
    }

    @Override
    public boolean saveTags(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagMapper.findAll();
    }

    @Override
    public String findTagByName(String name) {
        return tagMapper.findTagByName(name);
    }

    @Override
    public void updateTagCount(String name) {
        tagMapper.updateTagCount(name);
    }

    @Override
    public List<Tag> findByIds(String tags) {
        return tagMapper.findByIds(tags);
    }
}
