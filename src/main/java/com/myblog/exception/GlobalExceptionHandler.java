package com.myblog.exception;

import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:全局异常处理
 * @author li192
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = TipException.class)
    public String tipException(Exception e) {
        logger.error("find exception:e={}", e.getMessage());
        e.printStackTrace();
        return "comm/error_500";
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public RestResponseBo handleAuthorizationException1() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logger.error("用户：" + user.getUsername() + "进行了一次无权限操作！");
        return RestResponseBo.fail("Sorry！您无此权限！");
    }


}
