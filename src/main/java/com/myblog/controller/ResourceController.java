package com.myblog.controller;

import com.myblog.entity.Resource;
import com.myblog.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 资源控制器
 * @author li192
 */
@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/resources")
    public String resources(Model model) {
        model.addAttribute("resources",resourceService.listResource());
        return "/resources";
    }

    /**
     * 添加资源
     * @param resources
     * @param model
     * @param attributes
     * @return
     */
    @PostMapping("/resources")
    public String resources(@Valid Resource resources, Model model, RedirectAttributes attributes) {
        Resource resource = resourceService.getResource(resources.getResourceName());
        if (resource != null) {
            attributes.addFlashAttribute("message","不能添加重复资源");
        } else {
            attributes.addFlashAttribute(resources);
        }
        return "redirect:/resources";
    }
}
