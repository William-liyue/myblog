package com.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 关于我界面控制器
 * @author li192
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about() {
        return "/about";
    }
}
