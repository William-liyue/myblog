package com.myblog.controller.admin;

import com.myblog.constant.WebConstant;
import com.myblog.controller.BaseController;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.entity.User;
import com.myblog.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Description:登录 / 退出
 * @author li192
 */
@Controller
@RequestMapping("/admin")
@Api(value = "用户登录登出Controller")
@Slf4j(topic = "AuthController")
public class LoginController extends BaseController {

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    @ApiOperation(value = "后台登录页面", notes = "后台登录页面初始化")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    @ApiOperation(value = "后台登录具体实现", notes = "后台登录具体实现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "remeber_me", value = "记住我", required = false, dataType = "String")
    })
    @ResponseBody
    public RestResponseBo doLogin(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam(required = false) String remeber_me) {
        // 密码MD5加密
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, StringUtils.isNotBlank(remeber_me));
        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            // 30分钟失效
            Session session = subject.getSession();
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            session.setTimeout(30 * 60 * 1000);
            session.setAttribute(WebConstant.LOGIN_SESSION_KEY, user);
            return RestResponseBo.ok();
        } catch (UnknownAccountException e) {
            return RestResponseBo.fail(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return RestResponseBo.fail(e.getMessage());
        } catch (LockedAccountException e) {
            return RestResponseBo.fail(e.getMessage());
        } catch (AuthenticationException e) {
            return RestResponseBo.fail("认证失败！");
        }
    }

    /**
     * 重写logout方法，shiro默认跳到首页
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "后台用户注销", notes = "后台用户注销")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        // 如果已经登录，则跳转到管理首页
        if (subject != null) {
            subject.logout();
        }
        return this.redirectBack("login", ADMIN);
    }

}
