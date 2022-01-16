package com.myblog.mapper;

import com.myblog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论区持久层
 * @author li192
 */
@Mapper
@Repository
public interface CommentMapper {

    /**
     * 新增一条评论或留言
     * @param comment
     * @return
     */
    public boolean insertComment(Comment comment);

    /**
     * 查询该文章下的所有评论 或 所有留言的总数
     * @param articleId
     * @return
     */
    public int findCommentCount(@Param("articleId") Integer articleId);

    public List<Comment> findAllComment(@Param("articleId") Integer articleId,@Param("pageNow") int pageNow,@Param("pageSize") int pageSize);

    /**
     * 查找该评论下的所有子评论
     * @param pId
     * @return
     */
    public List<Comment> findChildrenComment(@Param("pId") Integer pId);
}