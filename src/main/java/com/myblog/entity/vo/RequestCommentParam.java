package com.myblog.entity.vo;

import lombok.Data;

/**
 * @author li192
 */
@Data
public class RequestCommentParam {

    private Integer articleId;          // 文章id
    private String content;             // 评论该文章内容
    private String nickname;            // 评论者昵称
    private String email;               // 评论者邮箱
    private String param;               // 额外参数 包括pid replyId replyNickName

}
