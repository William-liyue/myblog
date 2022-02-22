package com.myblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.constant.WebConstant;
import com.myblog.controller.BaseController;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.entity.Comments;
import com.myblog.model.entity.User;
import com.myblog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: 博客后台评论管理
 * @author li192
 */
@Controller
@RequestMapping("admin/comments")
@Slf4j(topic = "CommentController")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 评论管理
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping(value = "")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int limit,
                        HttpServletRequest request) {
        // 获取登录人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(page, limit);
        // 查询非登录人的评论
        List<Comments> commentsList = commentService.selectCommentsByAuthorId(user.getId());
        PageInfo<Comments> pageInfo = new PageInfo(commentsList);
        request.setAttribute("comments", pageInfo);
        return "admin/comment_list";
    }

    /**
     * 删除评论
     * @param coid
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo delCommentById(@RequestParam Integer coid, HttpServletRequest request) {
        String result = commentService.delCommentById(coid);
        if (WebConstant.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.ok();
        }
        return RestResponseBo.fail("系统异常，删除失败！");
    }

}
