package com.myblog.controller.admin;

import com.myblog.constant.MailConstant;
import com.myblog.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;

/**
 * Description: 邮件发送控制层
 * @author li192
 */
@Controller
@RequestMapping("/article")
@Slf4j(topic = "MailController")
public class MailController extends BaseController {


    @RequestMapping(value = "testMail")
    @ResponseBody
    public void goTest() {
        Context context = new Context();
        context.setVariable("id", "64");
        context.setVariable("replyContent","你好我是回复内容。");
        sendEmail(MailConstant.REPLY_NOTICE_TEMPLATE, "zhangjianbing777@gmail.com", MailConstant.MAIL_SUBJECT_1, context);
    }


}
