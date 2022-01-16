package com.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 音乐控制器
 * @author li192
 */
@Controller
public class MusicShowController {

    @GetMapping("/music")
    public String about() {
        return "/music";
    }
}
