package com.myblog.controller;

import com.myblog.service.BlogService;
import com.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 时间轴控制器
 * @author li192
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archive(Model model) {
        List<BlogQuery> blogs = blogService.getAllBlog();
        model.addAttribute("blogs",blogs);
        return "archives";
    }
}
