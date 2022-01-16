package com.myblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.entity.FriendLink;
import com.myblog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 友链后台管理控制台
 * @author li192
 */
@Controller
@RequestMapping("/admin")
public class FriendController {

    @Autowired
    private FriendLinkService friendLinkService;

    /**
     * 跳转友链新增页面
     * @param model
     * @return
     */
    @GetMapping("/friendlinks/input")
    public String input(Model model) {
        model.addAttribute("friendlink", new FriendLink());
        return "/admin/friendlinks-input";
    }

    /**
     * 友链新增
     * @param friendLink
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/friendlinks")
    public String post(@Valid FriendLink friendLink, BindingResult result, RedirectAttributes attributes) {

        FriendLink type1 = friendLinkService.getFriendLinkByBlogAddress(friendLink.getBlogAddress());
        if (type1 != null) {
            attributes.addFlashAttribute("message","不能添加相同的网址");
            return "redirect:/admin/friendlinks/input";
        }
        if (result.hasErrors()) {
            return "/admin/firendlinks-input";
        }
        friendLink.setCreateTime(new Date());
        int f = friendLinkService.saveFriendLink(friendLink);
        if (f == 0) {
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/friendlinks";
    }

    /**
     * 查询所有友链
     * @param model
     * @param pageNum
     * @return
     */
    @GetMapping("/friendlinks")
    public String firend(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum,10);
        List<FriendLink> list = friendLinkService.listFriendLink();
        PageInfo<FriendLink> pageInfo = new PageInfo<FriendLink>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/friendlinks";
    }

    /**
     * 修改并编辑友链页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/friendlinks/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("friendlink",friendLinkService.getFriendLink(id));
        return "/admin/friendlinks-input";
    }

    /**
     * 修改并编辑友链
     * @param friendLink
     * @param attributes
     * @return
     */
    @PostMapping("/friendlinks/{id}")
    public String editPost(@Valid FriendLink friendLink, RedirectAttributes attributes) {
        int t = friendLinkService.updateFriendLink(friendLink);
        if (t == 0) {
            attributes.addFlashAttribute("message","编辑失败");
        } else {
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/admin/friendlinks";
    }

    /**
     * 删除友链
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/friendlinks/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        friendLinkService.deleteFriendLink(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/friendlinks";
    }
}
