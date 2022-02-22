package com.myblog.controller.admin;

import com.myblog.controller.BaseController;
import com.myblog.mapper.MetaMapper;
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
 * Description:链接控制层
 * @author li192
 */
@Controller
@RequestMapping("admin/links")
@Slf4j(topic = "LinksController")
public class LinksController extends BaseController {

    @Autowired
    private MetaService metasService;

    @Autowired
    private MetaMapper metaMapper;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        // 根据类型来获取链接
        List<Metas> metas = metasService.getMetasByType(Types.LINK);
        request.setAttribute("links", metas);
        return "admin/links";
    }


    /**
     * 保存友链
     * @param mid
     * @param name
     * @param url
     * @param logo
     * @param sort
     * @param request
     * @return
     */
    @PostMapping(value = "save")
    @ResponseBody
    public RestResponseBo saveLink(@Param(value = "mid") Integer mid,
                                   @RequestParam String name,
                                   @RequestParam String url,
                                   @RequestParam String logo,
                                   @RequestParam(value = "sort", defaultValue = "0") int sort,
                                   HttpServletRequest request) {
        try {
            Metas metas = new Metas();
            metas.setName(name);
            metas.setSlug(url);
            metas.setDescription(logo);
            metas.setSort(sort);
            metas.setType(Types.LINK);
            if (null != mid) {
                metas.setMid(mid);
                metaMapper.updateMeta(metas);
            } else {
                metaMapper.saveMeta(metas);
            }
        } catch (Exception e) {
            String msg = "友链保存失败";
            log.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }


    /**
     * 删除友链
     * @param mid
     * @param request
     * @return
     */
    @RequiresPermissions("delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public RestResponseBo delete(@RequestParam int mid,HttpServletRequest request) {
        try {
            metaMapper.delMetaById(mid);
        } catch (Exception e) {
            String msg = "友链删除失败";
            log.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

}
