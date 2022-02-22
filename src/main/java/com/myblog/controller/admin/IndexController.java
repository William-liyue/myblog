package com.myblog.controller.admin;

import com.myblog.constant.WebConstant;
import com.myblog.controller.BaseController;
import com.myblog.exception.TipException;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.dto.Statistics;
import com.myblog.model.dto.Types;
import com.myblog.model.entity.Comments;
import com.myblog.model.entity.Contents;
import com.myblog.model.entity.User;
import com.myblog.service.CommentService;
import com.myblog.service.ContentService;
import com.myblog.service.SiteService;
import com.myblog.service.UserService;
import com.myblog.utils.TaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description:进入后台页面
 * @author li192
 */
@Controller("backStageController")
@RequestMapping("/admin")
@Slf4j(topic = "backStageController")
public class IndexController extends BaseController {


    @Autowired
    private SiteService siteService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentService contentService;

    /**
     * 仪表盘
     * @param request
     * @return
     */
    @GetMapping(value = {"/", "index"})
    public String index(HttpServletRequest request) {
        // 获取最新的10条评论
        List<Comments> comments = siteService.recentComments(10);
        List<Contents> contents = siteService.getContents(Types.RECENT_ARTICLE, 10);
        int commentCount = commentService.selectCommentCount();
        int contentCount = contentService.getContentCount();
        Statistics statistics = new Statistics();
        statistics.setComments(commentCount);
        statistics.setArticles(contentCount);
        request.setAttribute("comments", comments);
        request.setAttribute("articles", contents);
        request.setAttribute("statistics", statistics);
        return "admin/index";
    }


    /**
     * 个人设置
     * @param request
     * @return
     */
    @GetMapping(value = "profile")
    public String profile(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", user);
        return "admin/profile";
    }

    /**
     * 保存个人信息
     * @param screenName
     * @param email
     * @param request
     * @param session
     * @return
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public RestResponseBo saveProfile(@RequestParam String screenName,
                                      @RequestParam String email,
                                      HttpServletRequest request,
                                      HttpSession session) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNotBlank(screenName) && StringUtils.isNotBlank(email)) {
            User temp = new User();
            temp.setId(user.getId());
            temp.setScreen_name(screenName);
            temp.setEmail(email);
            userService.updateUser(temp);
            // 更新session中的数据
            user.setScreen_name(screenName);
            user.setEmail(email);
            SecurityUtils.getSubject().getSession().setAttribute(WebConstant.LOGIN_SESSION_KEY, user);
        }
        return RestResponseBo.ok();
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param password
     * @param request
     * @param session
     * @return
     */
    @PostMapping(value = "/password")
    @ResponseBody
    public RestResponseBo upPwd(@RequestParam String oldPassword,
                                @RequestParam String password,
                                HttpServletRequest request,
                                HttpSession session) {
        User users = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)) {
            return RestResponseBo.fail("请确认信息输入完整");
        }

        if (!users.getPassword().equals(TaleUtils.MD5encode(users.getUsername() + oldPassword))) {
            return RestResponseBo.fail("旧密码错误");
        }
        if (password.length() < 6 || password.length() > 14) {
            return RestResponseBo.fail("请输入6-14位密码");
        }

        try {
            User temp = new User();
            temp.setId(users.getId());
            // 用户名和密码加密
            String pwd = TaleUtils.MD5encode(users.getUsername() + password);
            temp.setPassword(pwd);
            userService.updateUser(temp);

            //更新session中的数据
            User original = (User) session.getAttribute(WebConstant.LOGIN_SESSION_KEY);
            original.setPassword(pwd);
            session.setAttribute(WebConstant.LOGIN_SESSION_KEY, original);
            return RestResponseBo.ok();
        } catch (Exception e) {
            String msg = "密码修改失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            return RestResponseBo.fail(msg);
        }
    }

}
