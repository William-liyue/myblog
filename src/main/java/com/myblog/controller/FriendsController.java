package com.myblog.controller;

import com.myblog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 友链控制器
 * @author li192
 */
@Controller
public class FriendsController {

    @Autowired
    private FriendLinkService friendLinkService;

    @GetMapping("/friends")
    public String frineds(Model model) {
        model.addAttribute("friendLinks",friendLinkService.listFriendLink());
        return "/friends";
    }
}
