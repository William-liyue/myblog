package com.myblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.entity.Resource;
import com.myblog.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * 资源管理控制器
 * @author li192
 */
@Controller
@RequestMapping("/admin")
public class ResourcesController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查询资源
     * @param model
     * @param pageNum
     * @return
     */
    @GetMapping("/resources")
    public String resource(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum,10);
        List<Resource> list = resourceService.listResource();
        PageInfo<Resource> pageInfo = new PageInfo<Resource>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/resources";
    }

    /**
     * 新增页面
     * @param model
     * @return
     */
    @GetMapping("/resources/input")
    public String input(Model model) {
        model.addAttribute("resource", new Resource());
        return "/admin/resources-input";
    }

    /**
     * 新增资源
     * @param resource
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/resources")
    public String post(@Valid Resource resource, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/admin/resources-input";
        }
        int p = resourceService.saveResource(resource);
        if (p == 0) {
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/resources";
    }

    /**
     * 跳转编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/resources/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("resource",resourceService.getResource(id));
        return "/admin/resources-input";
    }

    /**
     * 编辑资源
     * @param resource
     * @param attributes
     * @return
     */
    @PostMapping("/resources/{id}")
    public String editPost(@Valid Resource resource, RedirectAttributes attributes) {
        int p = resourceService.updateResource(resource);
        if (p == 0) {
            attributes.addFlashAttribute("message","编辑失败");
        } else {
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/admin/resources";
    }

    /**
     * 删除资源
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/resources/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        resourceService.deleteResource(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/resources";
    }
}
