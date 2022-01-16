package com.myblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.entity.Blog;
import com.myblog.entity.Type;
import com.myblog.entity.User;
import com.myblog.service.BlogService;
import com.myblog.service.TypeService;
import com.myblog.vo.BlogQuery;
import com.myblog.vo.SearchBlog;
import com.myblog.vo.ShowBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 博客管理控制器
 * @author li192
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    /**
     * 跳转博客新增页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("blog", new Blog());
        return "/admin/blogs-input";
    }

    /**
     * 博客列表
     * @param model
     * @param pageNum
     * @param attributes
     * @return
     */
    @RequestMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        RedirectAttributes attributes) {
        // 按照排序字段 倒序 排序
        String orderBy = "updateTime desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<BlogQuery> list = blogService.getAllBlog();
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(list);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/blogs";
    }

    /**
     * 新增博客
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        // 设置blog的type
        blog.setType(typeService.getType(blog.getTypeId()));
        // 设置blog中typeId属性
        blog.setTagId(blog.getTypeId());
        // 设置用户Id
        blog.setUserId(blog.getId());
        int b = blogService.saveBlog(blog);

        if (b == 0) {
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 删除博客文章
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * 编辑并修改博客文章
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Integer id, Model model) {
        ShowBlog blogById = blogService.getBlogById(id);
        List<Type> allType = typeService.getAllType();
        model.addAttribute("blog",blogById);
        model.addAttribute("types",allType);
        return "/admin/blogs-input";
    }

    /**
     * 修改并编辑博客文章
     * @param showBlog
     * @param attributes
     * @return
     */
    @PostMapping("/blogs/{id}")
    public String editPost(@Valid ShowBlog showBlog, RedirectAttributes attributes) {
        int b = blogService.updateBlog(showBlog);
        if (b == 0) {
            attributes.addFlashAttribute("message","修改失败");
        } else {
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 搜索博客文章
     * @param searchBlog
     * @param model
     * @param pageNum
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog, Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        List<BlogQuery> blogBySearch = blogService.getBlogBySearch(searchBlog);
        PageHelper.startPage(pageNum,10);
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(blogBySearch);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/blogs :: blogList";
    }
}
