package com.myblog.mapper;

import com.myblog.entity.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 友链持久层接口
 * @author li192
 */
@Mapper
@Repository
public interface FriendLinkMapper {

    /**查询友链管理列表*/
    List<FriendLink> listFriendLink();

    /**新增友链*/
    int saveFriendLink(FriendLink friendLink);

    /**根据id查询友链*/
    FriendLink getFriendLink(Long id);

    /**根据网址查询友链*/
    FriendLink getFriendLinkByBlogAddress(String blogAddress);

    /**编辑修改友链*/
    int updateFriendLink(FriendLink friendLink);

    /**删除友链*/
    void deleteFriendLink(Long id);
}
