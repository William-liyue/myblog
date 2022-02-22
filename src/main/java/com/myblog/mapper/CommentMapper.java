package com.myblog.mapper;

import com.myblog.model.entity.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论区
 * @author li192
 */
@Mapper
public interface CommentMapper {

    /**
     * 评论总数
     * @return
     */
    int selectCommentCount();

    /**
     * 根据id来查询评论内容
     * @param id
     * @return
     */
    Comments selectCommentByid(@Param("id") Integer id);

    /**
     * 查询最新评论
     * @return
     */
    List<Comments> selectNewComments();

    /**
     * 新增评论
     * @param comment
     * @return
     */
    int saveComment(Comments comment);

    /**
     * 根据id删除评论
     * @param id
     * @return
     */
    boolean delCommentById(@Param("id") Integer id);

    /**
     * 查询非己评论
     * @param authorId
     * @return
     */
    List<Comments> selectCommentsByAuthorId(@Param("authorId") Integer authorId);

    /**
     * 根据文章的id来获取改文章下面的评论
     * @param cid
     * @param status
     * @return
     */
    List<Comments> getCommentsListByContentId(@Param("cid") Integer cid, @Param("status") String status);

    /**
     * 查询未审核评论
     * @return
     */
    List<Comments> getNotAuditComments();

    void updateCommentById(@Param("id") Integer id);

    void updateComment(Comments comObj);

}
