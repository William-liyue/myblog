package com.myblog.service.Impl;

import com.myblog.util.Page;
import com.myblog.entity.Comment;
import com.myblog.mapper.CommentMapper;
import com.myblog.service.CommentService;
import com.myblog.util.RelativeDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客评论业务层接口实现
 * @author li192
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public int findCommentCount(Integer id) {
        return commentMapper.findCommentCount(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "commentList", condition = "#comment.articleId == null",key = "1"),
            @CacheEvict(value = "articleCommentsList",condition = "#comment.articleId != null",key = "1")
    })

    @Override
    @Deprecated
    public boolean addComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Caching(cacheable = {
            @Cacheable(value = "commentList", condition = "#articleId == null", key = "1"),
            @Cacheable(value = "articleCommentList", condition = "#articleId != null", key = "1")
    })
    @Deprecated
    @Override
    public Page<Comment> findAll(Integer articleId, Integer pageNow, Integer pageSize) {
        int startLimit = (pageNow-1)*pageSize;
        List<Comment> c = commentMapper.findAllComment(articleId,startLimit,pageSize);
        List<Comment> children = null;
        if (null != c) {
            for (Comment t : c) {
                t.setCreateAt(RelativeDateFormat.showTime(t.getCreateTime()));
                children = commentMapper.findChildrenComment(t.getId());
                if (null != children) {
                    for (Comment child : children) {
                        child.setCreateAt(RelativeDateFormat.showTime(child.getCreateTime()));
                    }
                }
                t.setChildren(children);
            }
        }
        int totalCount = commentMapper.findCommentCount(articleId);
        int totalPage = (totalCount % pageSize == 0) ? (totalCount/pageSize) : (totalCount/pageSize) + 1;

        Page<Comment> page = new Page<Comment>();
        page.setData(c);
        page.setTotalPage(totalPage);
        page.setCurrentPage(pageNow);
        return page;
    }

    @Override
    @CacheEvict(value = "articleCommentList",allEntries = true,beforeInvocation = true)
    public boolean addArticledComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    @CacheEvict(value = "commentList",allEntries = true,beforeInvocation = true)
    public boolean addLevelComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    @Cacheable(cacheNames = "articleCommentList",key = "T(String).valueOf(#articleId).concat('-').concat(T(String).valueOf(#pageNow)).concat('-').concat(T(String).valueOf(#pageSize))")
    public Page<Comment> findArticledCommentAll(Integer articleId, Integer pageNow, Integer pageSize) {
        int startLimit = (pageNow - 1)*pageSize;
        List<Comment> c = commentMapper.findAllComment(articleId,startLimit,pageSize);
        List<Comment> children = null;
        if (null != c) {
            for (Comment t : c) {
                t.setCreateAt(RelativeDateFormat.showTime(t.getCreateTime()));
                children = commentMapper.findChildrenComment(t.getId());
                if(null != children){
                    for(Comment child : children){
                        child.setCreateAt(RelativeDateFormat.showTime(child.getCreateTime()));
                    }
                }
                t.setChildren(children);
            }
        }
        int totalCount = c.size();
        int totalPage = (totalCount % pageSize == 0) ? (totalCount/pageSize) : (totalCount/pageSize) + 1;
        Page<Comment> page = new Page<Comment>();
        page.setData(c);
        page.setTotalPage(totalPage);
        page.setCurrentPage(pageNow);
        return page;
    }

    @Override
    @Cacheable(cacheNames = "commentList",
            key="T(String).valueOf(#pageNow).concat('-').concat(T(String).valueOf(#pageSize))")
    public Page<Comment> findLevelCommentAll(Integer pageNow, Integer pageSize) {
        int startLimit=(pageNow-1)*pageSize;
        List<Comment> c=commentMapper.findAllComment(null,startLimit,pageSize);
        List<Comment> children=null;
        if(null!=c){
            for(Comment t : c){
                t.setCreateAt(RelativeDateFormat.showTime(t.getCreateTime()));
                children=commentMapper.findChildrenComment(t.getId());
                if(null!=children){
                    for(Comment child : children){
                        child.setCreateAt(RelativeDateFormat.showTime(child.getCreateTime()));
                    }
                }
                t.setChildren(children);
            }
        }
        int totalCount=commentMapper.findCommentCount(null);
        int totalPage=(totalCount % pageSize == 0) ? (totalCount/pageSize) : (totalCount/pageSize)+1;

        Page<Comment> page=new Page<Comment>();
        page.setData(c);
        page.setTotalPage(totalPage);
        page.setTotalPage(pageNow);
        return page;
    }

    @Cacheable(cacheNames = "commentList",
            key="T(String).valueOf(#pageNow).concat('-').concat(T(String).valueOf(#pageSize))")
    public Page<Comment> findLevelMessageAll(Integer pageNow, Integer pageSize) {
        int startLimit=(pageNow-1)*pageSize;
        List<Comment> c=commentMapper.findAllComment(null,startLimit,pageSize);
        List<Comment> children=null;
        if(null != c){
            for(Comment t : c){
                t.setCreateAt(RelativeDateFormat.showTime(t.getCreateTime()));
                children=commentMapper.findChildrenComment(t.getId());
                if(null!=children){
                    for(Comment child : children){
                        child.setCreateAt(RelativeDateFormat.showTime(child.getCreateTime()));
                    }
                }
                t.setChildren(children);
            }
        }
        int totalCount=commentMapper.findCommentCount(null);
        int totalPage=(totalCount % pageSize == 0) ? (totalCount/pageSize) : (totalCount/pageSize)+1;

        Page<Comment> page=new Page<Comment>();
        page.setData(c);
        page.setTotalPage(totalPage);
        page.setCurrentPage(pageNow);
        return page;
    }
}
