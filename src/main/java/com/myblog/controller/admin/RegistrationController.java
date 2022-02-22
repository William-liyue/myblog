package com.myblog.controller.admin;

import com.myblog.constant.WebConstant;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.entity.Role;
import com.myblog.model.entity.User;
import com.myblog.service.RoleService;
import com.myblog.service.UserRoleService;
import com.myblog.service.UserService;
import com.myblog.utils.DateKit;
import com.myblog.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description 后台用户注册控制器
 * @author li192
 */
@Controller
// @RequestMapping("/admin") 关闭注册功能
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 注册页初始化
     * @return
     */
    @GetMapping("/registry")
    public String initRegist() {
        return "admin/registry";
    }

    /**
     * 用户开始注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/registry")
    @ResponseBody
    public RestResponseBo doRegist(@RequestParam String username, @RequestParam String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return RestResponseBo.fail("用户名或密码不能为空！");
        }
        User user = userService.selectUserByUsername(username);
        if (null != user) {
            return RestResponseBo.fail("该用户已存在！");
        }
        try {
            // 创建用户
            String encryptpwd = MD5Utils.encrypt(username, password);
            User users = new User();
            user.setUsername(username);
            user.setPassword(encryptpwd);
            user.setStatus(WebConstant.STATUS_1);
            user.setCreated(DateKit.getCurrentUnixTime());
            user.setScreen_name("MyStory普通用户");
            userService.saveUser(user);

            // 给用户分配角色
            Role role = roleService.getRoleByRoleName(WebConstant.USER_ROLE);
            User usertemp = userService.selectUserByUsername(username);
            userRoleService.addRoleForUser(usertemp.getId().toString(),role.getId().toString());
        } catch (Exception e) {
            log.error("注册用户失败！" + e.getMessage());
            return RestResponseBo.fail("注册用户失败！");
        }
        return RestResponseBo.ok();
    }

}
