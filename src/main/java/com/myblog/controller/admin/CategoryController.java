package com.myblog.controller.admin;

import com.myblog.constant.WebConstant;
import com.myblog.controller.BaseController;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.dto.Types;
import com.myblog.model.entity.Metas;
import com.myblog.service.MetaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description:分类管理
 * @author li192
 */
@Controller
@RequestMapping("admin/category")
@Slf4j(topic = "CategoryController")
public class CategoryController extends BaseController {

    @Autowired
    private MetaService metasService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<Metas> categories = metasService.getMetaList(Types.CATEGORY, null, WebConstant.MAX_POSTS);
        List<Metas> tags = metasService.getMetaList(Types.TAG, null, WebConstant.MAX_POSTS);
        request.setAttribute("categories", categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }

    @RequiresPermissions("create")
    @PostMapping(value = "save")
    @ResponseBody
    public RestResponseBo saveCategory(@RequestParam String cname, @Param("mid") Integer mid, @RequestParam String metaType,
                                       HttpServletRequest request) {
        try {
            metasService.saveMeta(metaType, cname, mid);
        } catch (Exception e) {
            String msg = "分类保存失败";
            log.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

    @RequiresPermissions("delete")
    @PostMapping(value = "delete")
    @ResponseBody
    public RestResponseBo delete(@RequestParam Integer mid, HttpServletRequest request) {
        try {
            metasService.delMetaById(mid);
        } catch (Exception e) {
            String msg = "删除失败";
            log.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

}
