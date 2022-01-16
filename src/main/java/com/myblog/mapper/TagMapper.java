package com.myblog.mapper;

import com.myblog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author li192
 */
@Mapper
public interface TagMapper {

    /**
     * 新增标签
     * @param tag
     * @return
     */
    boolean saveTag(Tag tag);

    /**
     * 获取所有标签
     * @return
     */
    List<Tag> findAll();

    /**
     *根据标签名称查找标签
     * @param name
     * @return
     */
    String findTagByName(String name);

    /**
     *根据标签名称跟新标签的文章数
     * @param name
     */
    void updateTagCount(String name);

    /**
     * 根据文章的标签id查出该文章的所有标签
     * @param tags
     * @return
     */
    List<Tag> findByIds(String tags);
}
