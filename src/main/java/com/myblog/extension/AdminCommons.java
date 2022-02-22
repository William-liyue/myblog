package com.myblog.extension;

import com.myblog.model.entity.Metas;
import com.myblog.model.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 后台公共函数
 * <p>
 * @author li192
 */
@Component
public final class AdminCommons {

    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    /**
     * 判断category和cat的交集
     *
     * @param cats
     * @return
     */
    public static boolean exist_cat(Metas category, String cats) {
        String[] arr = null != cats ? cats.split(",") : null;
        if (null != arr && arr.length > 0) {
            for (String c : arr) {
                if (c.trim().equals(category.getName())) {
                    return true;
                }
            }
        }
        return false;
    }


    public static String rand_color() {
        Random rand = new Random();
        int r = rand.nextInt(9);
        return COLORS[r];
    }

    /**
     * 获取登录人姓名
     * @return
     */
    public static String getLoginUserName() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (null != user) {
            return user.getUsername();
        }
        return "guy";
    }


}
