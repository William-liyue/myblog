package com.myblog.entity;

import com.myblog.vo.DetailedBlog;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论实体类
 * @author li192
 */
@Data
public class Comment implements Serializable {

    private Integer id;
    private Integer userid;
    private String nickname;
    private String email;
    private String content;
    private Date createTime;

    private Integer pid;                     // 父id
    private Integer replyCommentId;          // 留言 评论文章
    private Integer articleId;               // 评论文章的id
    private Integer status;                  // 状态

    /**头像*/
    private String avatar;                   // 评论者头像
    private Integer replyId;                 // 评论者id
    private String replyNickname;            // 回复者昵称
    private String os;                       // 评论者操作系统名称

    private String createAt;                // date类型转换成string

    /**回复评论*/
    private List<Comment> children;          // 用于保存子评论

    private DetailedBlog blog;
}
