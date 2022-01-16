package com.myblog.controller;

import com.myblog.entity.Comment;
import com.myblog.entity.User;
import com.myblog.service.BlogService;
import com.myblog.service.CommentService;
import com.myblog.vo.DetailedBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 留言信息控制器
 * @author li192
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("{comment.avatar}")
    private String avatar;


}
