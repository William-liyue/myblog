package com.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.entity.Type;
import com.myblog.service.BlogService;
import com.myblog.service.TypeService;
import com.myblog.vo.FirstPageBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 分类控制器
 * @author li192
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, @PathVariable Integer id, Model model) {
        List<Type> types = typeService.getAllType();
        // id为1时表示从首页导航点击进入分类页面
        if (id == 1) {
            id = types.get(0).getId();
        }
        model.addAttribute("types",types);
        List<FirstPageBlog> blogs = blogService.getByTypeId(id);

        PageHelper.startPage(pageNum,10000);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "/types";
    }
}
