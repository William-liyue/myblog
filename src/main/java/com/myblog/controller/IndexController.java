package com.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.entity.Comment;
import com.myblog.mapper.BlogMapper;
import com.myblog.service.BlogService;
import com.myblog.service.CommentService;
import com.myblog.service.TypeService;
import com.myblog.vo.DetailedBlog;
import com.myblog.vo.FirstPageBlog;
import com.myblog.vo.RecommendBlog;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 首页控制器
 * @author li192
 */
@Controller
public class IndexController {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CommentService commentService;

    /**
     * 分页查询博客列表
     */
    @GetMapping("/index")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum")
                        Integer pageNum, RedirectAttributes attributes) {
        PageHelper.startPage(pageNum,10);
        List<FirstPageBlog> firstPageBlog = blogService.getAllFirstPageBlog();
        List<RecommendBlog> recommendedBlog = blogService.getRecommendedBlog();
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(firstPageBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendedBlog",recommendedBlog);
        return "/index";
    }

    /**
     * 搜索博客
     * @param model
     * @param pageNum：分页列表
     * @param query
     * @return
     */
    @PostMapping("/search")
    public String search(Model model,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query) {
        PageHelper.startPage(pageNum,1000);
        List<FirstPageBlog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "/search";
    }

    /**
     * 跳转博客详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) throws NotFoundException {
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
//        List<Comment> comments = commentService.listCommentByBlogId(id);
//        model.addAttribute("comments",comments);
        model.addAttribute("blog",detailedBlog);
        return "redirect:/blog";
    }

    /**
     * 最新博客列表
     * @param model
     * @return
     */
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        List<FirstPageBlog> newBlog = blogService.getNewBlog();
        model.addAttribute("newblogs",newBlog);
        return "/index :: newblogList";
    }

    /**
     * 博客信息统计
     * @param model
     * @return
     */
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model) {
        int blogTotal = blogService.getBlogTotal();
        int blogViewTotal = blogService.getBlogViewTotal();
        int blogCommentTotal = blogService.getBlogCommentTotal();
        int blogMessageTotal = blogService.getBlogMessageTotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);
        return "/index :: blogMessage";
    }
}
