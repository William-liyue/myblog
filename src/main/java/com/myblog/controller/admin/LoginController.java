package com.myblog.controller.admin;

import com.myblog.entity.User;
import com.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * 用户登录控制器
 * @author li192
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginUrl(HttpServletRequest request) {
        // 获取当前session
        HttpSession session = request.getSession();
        // 随机产生字符串
        String state = getRandomString(Integer.parseInt("10"));
        session.setAttribute("state",state);
        // 重定向
        return "redirect:https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" +
                101533009 + "&redirect_uri=" + "http://127.0.0.1:8080/admin" + "&state" + state;
    }

    // length用户要求产生字符串的长度
    private String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < length;i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @Description: 跳转登录页面
     * @return: 返回登录页面
     */
    @GetMapping
    public String loginPage() {
        return "/admin/login";
    }

    /**
     * @Description: 登陆效验
     * @param username：用户名
     * @param password：密码
     * @param session：session域
     * @param attributes：返回页面消息
     * @return: 登录成功则跳转登录成功页面，登录失败则返回登陆失败页面
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.findOne(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "/admin/Admin";
        } else {
            attributes.addFlashAttribute("message","用户或密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    /**
     * 注册
     * @return
     */
    @GetMapping("register")
    public String registerPage() {
        return "redirect:/myblog/register";
    }

    /*@PostMapping("register")
    public Object register() {
        String mobile = "^1[34578]\\d{9}$";

        String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        System.out.println(match(regex, email));
        if (!match(regex, email) && !match(mobile, email)) {
            return new JSONResult(400, "邮箱或手机号格式错误!");

        } else {
            User user1 = userService.userP(email, password);
            log.info("user", user1);
            if (user1 == null) {
                return new JSONResult(400, "账号或密码错误！");
            } else {
                if (user1.getStatus() != 2) {
                    return new JSONResult(400, "邮箱未注册或未激活!");
                }
            }
        }
        return "/admin/login";
    }*/
}
