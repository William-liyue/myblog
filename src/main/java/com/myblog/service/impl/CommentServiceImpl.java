package com.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.constant.WebConstant;
import com.myblog.mapper.CommentMapper;
import com.myblog.model.entity.Comments;
import com.myblog.model.entity.Contents;
import com.myblog.service.CommentService;
import com.myblog.service.ContentService;
import com.myblog.utils.DateKit;
import com.myblog.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author li192
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ContentService contentService;

    @Override
    public int selectCommentCount() {
        return commentMapper.selectCommentCount();
    }

    @Override
    public Comments selectCommentByid(Integer id) {
        return commentMapper.selectCommentByid(id);
    }

    @Override
    public PageInfo<Comments> selectNewComments(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Comments> list = commentMapper.selectNewComments();
        PageInfo<Comments> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int saveComments(Comments comment) {
        return commentMapper.saveComment(comment);
    }

    @Override
    public String delCommentById(Integer coid) {
        boolean flag = commentMapper.delCommentById(coid);
        if(flag){
            return WebConstant.SUCCESS_RESULT;
        }
        return "系统异常，删除失败！";
    }

    @Override
    public List<Comments> selectCommentsByAuthorId(Integer authorId) {
        return commentMapper.selectCommentsByAuthorId(authorId);
    }

    @Override
    public PageInfo<Comments> getCommentsListByContentId(Integer contentId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Comments> list = commentMapper.getCommentsListByContentId(contentId, "approved");
        PageInfo<Comments> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    @Transactional
    public String insertComment(Comments comments) {

        if (null == comments) {
            return "评论对象为空";
        }

        if (StringUtils.isBlank(comments.getAuthor())) {
            comments.setAuthor("热心网友");
        }

        if (StringUtils.isNotBlank(comments.getMail()) && !TaleUtils.isEmail(comments.getMail())) {
            return "请输入正确的邮箱格式";
        }
        if (StringUtils.isBlank(comments.getContent())) {
            return "评论内容不能为空";
        }
        if (comments.getContent().length() < 5 || comments.getContent().length() > 2000) {
            return "评论字数在5-2000个字符";
        }
        if (null == comments.getCid()) {
            return "评论文章不能为空";
        }
        Contents contents = contentService.getContentById(comments.getCid());
        if (null == contents) {
            return "不存在的文章";
        }
        comments.setOwnerId(contents.getAuthorId());
        comments.setStatus(WebConstant.APPROVED);
        comments.setCreated(DateKit.getCurrentUnixTime());
        // 保存评论
        commentMapper.saveComment(comments);

        // 更新该文章下的评论数目
        Contents temp = new Contents();
        temp.setCid(contents.getCid());
        temp.setCommentsNum(contents.getCommentsNum() + 1);
        contentService.updateContent(temp);

        return WebConstant.SUCCESS_RESULT;
    }

    @Override
    @Transactional
    public void updateCommentById(Integer id) {
        commentMapper.updateCommentById(id);
    }

    @Override
    public List<Comments> getNotAuditComments() {
        return commentMapper.getNotAuditComments();
    }

    @Override
    @Transactional
    public void updateComment(Comments comObj) {
        commentMapper.updateComment(comObj);
    }
}
