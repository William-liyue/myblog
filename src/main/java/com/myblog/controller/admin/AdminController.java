package com.myblog.controller.admin;

import com.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * @author li192
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/Admin")
    public String input(Model model) {
        model.addAttribute("types",typeService.getAllType());
        return "/admin/Admin";
    }
}
