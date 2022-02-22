package com.myblog.controller.admin;

import com.myblog.constant.WebConstant;
import com.myblog.controller.BaseController;
import com.myblog.exception.TipException;
import com.myblog.model.bo.BackResponseBo;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.entity.Options;
import com.myblog.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li192
 */
@Controller
@RequestMapping("/admin/setting")
@Slf4j(topic = "SettingController")
public class SettingController extends BaseController {

    @Autowired
    private OptionService optionService;

    /**
     * 系统设置
     */
    @GetMapping(value = "")
    public String setting(HttpServletRequest request) {
        List<Options> voList = optionService.getOptions();
        Map<String, String> options = new HashMap<>();
        voList.forEach((option) -> {
            options.put(option.getName(), option.getValue());
        });
        if (options.get("site_record") == null) {
            options.put("site_record", "");
        }
        request.setAttribute("options", options);
        return "admin/setting";
    }

    /**
     * 保存系统设置
     * @param site_theme
     * @param request
     * @return
     */
    @PostMapping(value = "")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo saveSetting(@RequestParam(required = false) String site_theme,
                                      HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> querys = new HashMap();
            parameterMap.forEach((key, value) -> {
                querys.put(key, join(value));
            });
            optionService.saveOrUpdateOptions(querys);
            WebConstant.initConfig = querys;
            if (StringUtils.isNotBlank(site_theme)) {
                BaseController.THEME = "themes/" + site_theme;
            }
            return RestResponseBo.ok();
        } catch (Exception e) {
            String msg = "保存设置失败";
            return RestResponseBo.fail(msg);
        }
    }

    /**
     * 数组转字符串
     * @param arr
     * @return
     */
    private String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        String[] var3 = arr;
        int var4 = arr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }


    /**
     * 备份数据库文件
     * @param bk_type
     * @param bk_path
     * @param request
     * @return
     */
    @PostMapping(value = "backup")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo backup(@RequestParam String bk_type,
                                 @RequestParam String bk_path,
                                 HttpServletRequest request) {
        if (StringUtils.isBlank(bk_path)) {
            return RestResponseBo.fail("请输入sql文件存放路径");
        }
        try {
            BackResponseBo backResponse = optionService.backup(bk_type, bk_path, "yyyy-MM-dd");
            return RestResponseBo.ok(backResponse);
        } catch (Exception e) {
            String msg = "导出失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            return RestResponseBo.fail(msg);
        }
    }

    /**
     * 备份文件
     * @param ep_type
     * @param ep_path
     * @param request
     * @return
     */
    @RequiresRoles("admin")
    @PostMapping(value = "exports")
    @ResponseBody
    public RestResponseBo exports(@RequestParam String ep_type, @RequestParam String ep_path,
                                  HttpServletRequest request) {
        if (StringUtils.isBlank(ep_path)) {
            return RestResponseBo.fail("请输入文件备份路径");
        }
        try {
            BackResponseBo backResponseBo = optionService.exports(ep_type, ep_path,"yyyy-MM-dd");
            return RestResponseBo.ok(backResponseBo);
        } catch (Exception e) {
            String msg = "备份失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            return RestResponseBo.fail(msg);
        }
    }
}
