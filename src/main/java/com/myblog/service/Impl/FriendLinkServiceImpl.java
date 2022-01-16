package com.myblog.service.Impl;

import com.myblog.entity.FriendLink;
import com.myblog.mapper.FriendLinkMapper;
import com.myblog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li192
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Override
    public List<FriendLink> listFriendLink() {
        return friendLinkMapper.listFriendLink();
    }

    @Override
    public int saveFriendLink(FriendLink friendLink) {
        return friendLinkMapper.saveFriendLink(friendLink);
    }

    @Override
    public FriendLink getFriendLink(Long id) {
        return friendLinkMapper.getFriendLink(id);
    }

    @Override
    public FriendLink getFriendLinkByBlogAddress(String blogAddres) {
        return friendLinkMapper.getFriendLinkByBlogAddress(blogAddres);
    }

    @Override
    public int updateFriendLink(FriendLink friendLink) {
        return friendLinkMapper.updateFriendLink(friendLink);
    }

    @Override
    public void deleteFriendLink(Long id) {
        friendLinkMapper.deleteFriendLink(id);
    }
}
