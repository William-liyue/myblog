package com.myblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.controller.BaseController;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.entity.Logs;
import com.myblog.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 日志管理控制层
 * @author li192
 */
@Controller
@RequestMapping("admin/logs")
@Slf4j(topic = "LogController")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @GetMapping(value = "")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int limit,
                        HttpServletRequest request) {
        PageHelper.startPage(page, limit);
        List<Logs> list = logService.getLogsByAction(null);
        PageInfo<Logs> pageInfo = new PageInfo<>(list);
        request.setAttribute("logs", pageInfo);
        return "admin/log_list";
    }

    @RequestMapping(value = "deleteLog")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo deleteLogById(@RequestParam("log_id") Integer log_id, HttpServletRequest request) {
        try {
            logService.deleteLogById(log_id);
            log.info("==========删除日志成功==========");
            return RestResponseBo.ok();
        } catch (Exception e) {
            log.error("==========删除日志失败==========" + e.getMessage());
            return RestResponseBo.fail();
        }
    }

}
