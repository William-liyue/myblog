package com.myblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.entity.Type;
import com.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * 分类管理控制器
 * @author li192
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**-
     * 分页查询分类列表
     * @param model
     * @param pageNum
     * @return
     */
    @GetMapping("/types")
    public String list(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        // 按照排序字段 倒序 排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<Type> list = typeService.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<Type>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/types";
    }

    /**
     * 返回新增分类页面
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "/admin/types-input";
    }

    /**
     * 新增分类
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int t = typeService.saveType(type);
        if (t == 0) {
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改分类页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Integer id,Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "/admin/types-input";
    }

    /**
     * 修改并编辑分类
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editInput(@Valid Type type, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message","不能修改添加重复的分类");
        }
        int t = typeService.updateType(type);
        if (t == 0) {
            attributes.addFlashAttribute("message","编辑失败");
        } else {
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除分类
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }
}
