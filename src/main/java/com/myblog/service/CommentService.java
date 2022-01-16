package com.myblog.service;

import com.myblog.util.Page;
import com.myblog.entity.Comment;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 博客评论业务层接口
 * @author li192
 */
public interface CommentService {

    /**
     * 查找某文章下的评论数
     * @param id
     * @return
     */
    int findCommentCount(Integer id);

    @Deprecated
    boolean addComment(Comment comment);

    /**
     * 以分页形式显示文章的评论列表或留言列表
     * @param articleId
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Deprecated
    Page<Comment> findAll(Integer articleId,Integer pageNow, Integer pageSize);

    /**
     * 用于添加文章评论
     * @param comment
     * @return
     */
    boolean addArticledComment(Comment comment);

    /**
     * 用于添加留言
     * @param comment
     * @return
     */
    boolean addLevelComment(Comment comment);

    /**
     * 用于查找某篇文章下的所有评论  并分页
     * @param articleId
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Deprecated
    Page<Comment> findArticledCommentAll(Integer articleId, Integer pageNow, Integer pageSize);

    /**
     * 用于查找所有留言  并分页
     * @param pageNow
     * @param pageSize
     * @return
     */
    Page<Comment> findLevelCommentAll(Integer pageNow,Integer pageSize);

}
