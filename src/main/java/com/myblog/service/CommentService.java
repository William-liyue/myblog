package com.myblog.service;


import com.github.pagehelper.PageInfo;
import com.myblog.model.entity.Comments;

import java.util.List;

/**
 * Description:评论接口
 * @author li192
 */
public interface CommentService {

    /**
     * 查询评论的数量
     * @return
     */
    int selectCommentCount();

    /**
     * 根据id来查询评论
     * @param id
     * @return
     */
    Comments selectCommentByid(Integer id);

    /**
     * 获取最新评论
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Comments> selectNewComments(Integer page, Integer limit);

    /**
     * 保存评论
     * @param comment
     * @return
     */
    int saveComments(Comments comment);

    /**
     * 根据id删除评论
     * @param id
     * @return
     */
    String delCommentById(Integer id);

    /**
     * 查询非自己的评论
     * @param authorId
     * @return
     */
    List<Comments> selectCommentsByAuthorId(Integer authorId);

    /**
     * 根据文章的id来获取改文章下面的评论
     * @param contentId
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Comments> getCommentsListByContentId(Integer contentId, Integer page, Integer limit);

    /**
     * 保存评论并返回结果
     * @param comment
     * @return
     */
    String insertComment(Comments comment);

    /**
     * 根据id来更新评论
     * @param id
     */
    void updateCommentById(Integer id);

    /**
     * 查询为审核的评论
     * @return
     */
    List<Comments> getNotAuditComments();

    /**
     * 更新评论
     * @param comObj
     */
    void updateComment(Comments comObj);
}
