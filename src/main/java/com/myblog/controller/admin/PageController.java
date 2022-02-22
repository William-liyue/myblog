package com.myblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.myblog.constant.WebConstant;
import com.myblog.controller.BaseController;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.dto.Types;
import com.myblog.model.entity.Contents;
import com.myblog.model.entity.User;
import com.myblog.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:页面管理
 * @author li192
 */
@Controller()
@RequestMapping("admin/page")
@Slf4j(topic = "PageController")
public class PageController extends BaseController {

    @Autowired
    private ContentService contentsService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        PageInfo<Contents> contentsPaginator = contentsService.getArticlesWithpage(1, WebConstant.MAX_POSTS);
        request.setAttribute("articles", contentsPaginator);
        return "admin/page_list";
    }

    /**
     * 跳转新增
     * @param request
     * @return
     */
    @GetMapping(value = "page_edit")
    public String newPage(HttpServletRequest request) {
        return "admin/page_edit";
    }

    /**
     * 跳转编辑
     * @param cid
     * @param request
     * @return
     */
    @GetMapping(value = "/{cid}")
    public String editPage(@PathVariable Integer cid, HttpServletRequest request) {
        Contents contents = contentsService.getContentById(cid);
        request.setAttribute("contents", contents);
        return "admin/page_edit";
    }

    /**
     * 新增文章页面
     * @param title
     * @param content
     * @param status
     * @param slug
     * @param tags
     * @param allowComment
     * @param allowPing
     * @param request
     * @return
     */
    @PostMapping(value = "publish")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo publishPage(@RequestParam String title,
                                      @RequestParam String content,
                                      @RequestParam String status,
                                      @RequestParam String slug,
                                      @RequestParam String tags,
                                      @RequestParam(required = false) Integer allowComment,
                                      @RequestParam(required = false) Integer allowPing,
                                      HttpServletRequest request) {
        User users = (User) SecurityUtils.getSubject().getPrincipal();
        Contents contents = new Contents();
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setTags(tags);
        contents.setType(Types.PAGE);
        // 是否允许评论
        if (null != allowComment) {
            contents.setAllowComment(allowComment);
        }
        // 是否允许ping
        if (null != allowPing) {
            contents.setAllowPing(allowPing);
        }
        contents.setAuthorId(users.getId());
        String result = contentsService.saveContent(contents);
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }

    /**
     * 更新文章页面
     * @param cid
     * @param title
     * @param content
     * @param status
     * @param slug
     * @param allowComment
     * @param allowPing
     * @param request
     * @return
     */
    @PostMapping(value = "modify")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo modifyArticle(@RequestParam Integer cid, @RequestParam String title,
                                        @RequestParam String content, @RequestParam String status,
                                        @RequestParam String slug, @RequestParam String tags,
                                        @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing,
                                        HttpServletRequest request) {

        User users = (User) SecurityUtils.getSubject().getPrincipal();
        Contents contents = new Contents();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setTags(tags);
        contents.setType(Types.PAGE);
        if (null != allowComment) {
            contents.setAllowComment(allowComment);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing);
        }
        contents.setAuthorId(users.getId());
        // 更新文章
        String result = "";
        boolean flag = contentsService.updateContent(contents);
        if (flag) {
            result = "SUCCESS";
        } else {
            result = "FAIL";
        }
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }

    /**
     * 删除文章页面
     * @param cid
     * @param request
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo delete(@RequestParam int cid, HttpServletRequest request) {
        // 删除文章
        String result = contentsService.delContentById(cid);
        // 插入日志
//        logService.insertLog(LogActions.DEL_ARTICLE, cid + "", request.getRemoteAddr(), this.getUid(request));
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }
}
