package com.myblog.controller;

import com.github.pagehelper.PageInfo;
import com.myblog.constant.MailConstant;
import com.myblog.constant.WebConstant;
import com.myblog.model.bo.RestResponseBo;
import com.myblog.model.dto.Archive;
import com.myblog.model.dto.Types;
import com.myblog.model.entity.Comments;
import com.myblog.model.entity.Contents;
import com.myblog.model.entity.Metas;
import com.myblog.model.entity.User;
import com.myblog.service.*;
import com.myblog.utils.IPKit;
import com.myblog.utils.TaleUtils;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * 博客首页控制层
 * @author li192
 */
@Controller("frontStageController")
@Slf4j(topic = "frontStageController")
public class IndexController extends BaseController {


    @Autowired
    private SiteService siteService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MetaService metaService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private MetaService metasService;

    /**
     * 博客首页
     * @param request
     * @param limit
     * @return
     */
    @GetMapping(value = "/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        // 访问次数统计(自定义注解方式？？？)
        visitCount(request);
        return this.index(request, 1, limit);
    }


    public synchronized void visitCount(HttpServletRequest request) {
        // 来访ip
        String val = IPKit.getIpAddrByRequest(request);
        Integer times = visitService.getCountById(1).getCount();
        // 缓存中是否存在
        Integer count = cache.hget(Types.VISIT_COUNT, val);
        if (null != count && count > 0) {
            // 存在，不记录访问次数
        } else {
            // 存入缓存中，并设置超时时间
            cache.hset(Types.VISIT_COUNT, val, 1, WebConstant.VISIT_COUNT_TIME);
            // 入库
            visitService.updateCountById(times + 1);
        }
    }


    /**
     * 博客首页分页
     * @param request
     * @param p
     * @param limit
     * @return
     */
    @GetMapping(value = "page/{p}")
    public String index(HttpServletRequest request,
                        @PathVariable int p,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        p = p < 0 || p > WebConstant.MAX_PAGE ? 1 : p;
        PageInfo<Contents> articles = contentService.getContentsByPageInfo(p, limit);
        request.setAttribute("articles", articles);
        if (p > 1) {
            this.title(request, "第" + p + "页");
        }
        return this.render("index");
    }


    /**
     * 文章详情页
     * @param request
     * @param cid
     * @param coid
     * @return
     */
    @GetMapping(value = {"article/{cid}/{coid}", "article/{cid}/{coid}.html"})
    public String getArticle(HttpServletRequest request,
                             @PathVariable String cid,
                             @PathVariable String coid) {
        Contents contents = contentService.getContentById(Integer.parseInt(cid));
        if (null == contents || "draft".equals(contents.getStatus())) {
            return this.render_404();
        }
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
        // 评论
        completeArticle(coid, request, contents);
        // 15分钟内重复点击不会更新文章浏览量
        if (!checkHitsFrequency(request, String.valueOf(contents.getCid()))) {
            // 更新文章点击量
            updateArticleHit(contents.getCid(), contents.getHits());
        }
        return this.render("post");
    }


    /**
     * 标签页
     * @param request
     * @param name
     * @param limit
     * @return
     */
    @GetMapping(value = "tag/{name}")
    public String tags(HttpServletRequest request,
                       @PathVariable String name,
                       @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.tags(request, name, 1, limit);
    }

    /**
     * 标签的前台分页
     * @param request
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "tag/{name}/{page}")
    public String tags(HttpServletRequest request,
                       @PathVariable String name,
                       @PathVariable int page,
                       @RequestParam(value = "limit", defaultValue = "12") int limit) {

        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        // 对于空格的特殊处理
        name = name.replaceAll("\\+", " ");
        Metas metaDto = metaService.getMeta(Types.TAG, name);
        if (null == metaDto) {
            return this.render_404();
        }

        PageInfo<Contents> contentsPaginator = contentService.getTagArticles(metaDto.getMid(), page, limit);
        request.setAttribute("articles", contentsPaginator);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "标签");
        request.setAttribute("keyword", name);

        return this.render("page-category");
    }


    /**
     * 归档页面
     * @param request
     * @return
     */
    @GetMapping(value = "archives")
    public String archives(HttpServletRequest request) {
        List<Archive> list = siteService.getArchives();
        request.setAttribute("archives", list);
        return this.render("archives");
    }

    /**
     * 前台首页分类页面
     * @param pagename
     * @param request
     * @return
     */
    @GetMapping(value = "/custom/{pagename}")
    public String page(@PathVariable String pagename,
                       HttpServletRequest request) {
        return page(pagename, "1", request);
    }

    /**
     * 自定义页面,如：前台关于页面
     * @param pagename
     * @param coid
     * @param request
     * @return
     */
    @GetMapping(value = "/{pagename}/{coid}")
    public String page(@PathVariable String pagename,
                       @PathVariable String coid,
                       HttpServletRequest request) {
        // 根据文章缩略名来查询
        Contents contents = contentService.getContentBySlug(pagename);
        if (null == contents) {
            return this.render_404();
        }
        // 该文章是否允许评论
        if (contents.getAllowComment() == 1) {
            // 评论集合
            PageInfo<Comments> commentsPaginator = commentService.getCommentsListByContentId(contents.getCid(), Integer.parseInt(coid), 6);
            request.setAttribute("comments", commentsPaginator);
        }
        request.setAttribute("article", contents);
        if (!checkHitsFrequency(request, String.valueOf(contents.getCid()))) {
            // 更新文章点击量
            updateArticleHit(contents.getCid(), contents.getHits());
        }
        return this.render("page");
    }


    /**
     * 友链
     * @param request
     * @return
     */
    @GetMapping(value = "links")
    public String links(HttpServletRequest request) {
        List<Metas> links = metaService.getMetasByType(Types.LINK);
        request.setAttribute("links", links);
        return this.render("links");
    }

    /**
     * 前台文章详情页注销操作
     * @return
     */
    @GetMapping(value = "/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        // 如果已经登录，则跳转到管理首页
        if (subject != null) {
            subject.logout();
        }
        return this.redirectBack("index", THEME);
    }


    /**
     * 前台评论操作
     * @param request
     * @param response
     * @param cid
     * @param coid
     * @param author
     * @param mail
     * @param url
     * @param text
     * @param _csrf_token
     * @return
     */
    @PostMapping(value = "comment")
    @ResponseBody
    public RestResponseBo comment(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam Integer cid,
                                  @RequestParam Integer coid,
                                  @RequestParam String author,
                                  @RequestParam String mail,
                                  @RequestParam String url,
                                  @RequestParam String text,
                                  @RequestParam String _csrf_token) {

        // if login
        Comments comments = new Comments();
        User user = (User) SecurityUtils.getSubject().getPrincipal();


        /*String ref = request.getHeader("Referer");
        if (StringUtils.isBlank(ref) || StringUtils.isBlank(_csrf_token)) {
            return RestResponseBo.fail(ErrorCode.BAD_REQUEST);
        }*/

        if (null == cid || StringUtils.isBlank(text)) {
            return RestResponseBo.fail("请输入完整后评论~");
        }

        if (StringUtils.isNotBlank(author) && author.length() > 20) {
            return RestResponseBo.fail("姓名过长~");
        }

        if (StringUtils.isNotBlank(mail) && !TaleUtils.isEmail(mail)) {
            return RestResponseBo.fail("请输入正确的邮箱格式~");
        }

        if (text.length() > 200) {
            return RestResponseBo.fail("请输入200个字符以内的评论~");
        }
        String ip = IPKit.getIpAddrByRequest(request);
        String val = ip + ":" + cid;
        Integer count = cache.hget(Types.COMMENTS_FREQUENCY, val);
        if (null != count && count > 0) {
            // 1分钟可评论一次
            return RestResponseBo.fail("您发表评论太快了，请过会再试");
        }
        author = TaleUtils.cleanXSS(author);
        text = TaleUtils.cleanXSS(text);
        // 评论人的姓名
        author = EmojiParser.parseToAliases(author);
        // 评论的内容
        text = EmojiParser.parseToAliases(text);
        if (null != user) {
            comments.setAuthor(user.getScreen_name());
            comments.setMail(user.getEmail());
            comments.setUrl(user.getHome_url());
            // 评论所属作者id
            comments.setAuthorId(user.getId());
            // 作者回复后，发送邮件(如果是异步的会更好)
            try {
                sendReplyNoticeMail(coid, cid, text);
            } catch (Exception e) {
                log.error("作者回复后，发送邮件发生异常", e.getMessage());
            }
        } else {
            comments.setAuthor(author);
            comments.setMail(mail);
            comments.setUrl(url);
            // 未登录，即游客评论
            comments.setAuthorId(WebConstant.USER_VISITOR);
        }
        comments.setCid(cid);
        comments.setIp(ip);
        comments.setContent(text);
        comments.setParent(null == coid ? 0 : coid);
        try {
            String result = commentService.insertComment(comments);
            // 此处增加cookie是为了不让用户再次输入评论头部
            if (StringUtils.isNotBlank(author)) {
                cookie("tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            if (StringUtils.isNotBlank(mail)) {
                cookie("tale_remember_mail", URLEncoder.encode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            if (StringUtils.isNotBlank(url)) {
                cookie("tale_remember_url", URLEncoder.encode(url, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            // 设置对每个文章1分钟可以评论一次
            cache.hset(Types.COMMENTS_FREQUENCY, val, 1, 60);
            if (!WebConstant.SUCCESS_RESULT.equals(result)) {
                return RestResponseBo.fail(result);
            }
            return RestResponseBo.ok();
        } catch (Exception e) {
            String msg = "评论发布失败";
            log.error(msg, e);
            return RestResponseBo.fail(msg);
        }
    }

    /**
     * Description: 发送邮件通知方法
     * @param coid 当前评论的id
     * @param cid  当前文章id
     * @param text 评论的内容
     */
    public void sendReplyNoticeMail(Integer coid, Integer cid, String text) {
        if (null != coid) {
            Comments currentComment = commentService.selectCommentByid(coid);
            String currentCommentMail = currentComment.getMail();
            if (StringUtils.isNotBlank(currentCommentMail)) {
                Context context = new Context();
                context.setVariable("id", cid);
                context.setVariable("replyContent", text);
                sendEmail(MailConstant.REPLY_NOTICE_TEMPLATE, currentCommentMail, MailConstant.MAIL_SUBJECT_1, context);
            }
        }
    }

    /**
     * 设置cookie
     * @param name
     * @param value
     * @param maxAge
     * @param response
     */
    private void cookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    /**
     * 文章详情页面评论分页
     * @param coid
     * @param request
     * @param contents
     */
    private void completeArticle(String coid, HttpServletRequest request, Contents contents) {
        // 允许评论
        if (contents.getAllowComment() == 1) {
            // 每页6条评论
            PageInfo<Comments> commentsPaginator = commentService.getCommentsListByContentId(contents.getCid(), Integer.parseInt(coid), 6);
            request.setAttribute("comments", commentsPaginator);
        }
    }


    /**
     * 更新文章点击量
     * @param cid
     * @param chits
     */
    private void updateArticleHit(Integer cid, Integer chits) {
        if (chits == 0 || chits == null) {
            chits = 0;
        }
        chits = chits + 1;
        Contents temp = new Contents();
        temp.setCid(cid);
        temp.setHits(chits);
        contentService.updateContent(temp);
    }

    /**
     * 检查同一个ip在15分钟之内是否访问同一篇文章
     * @param request
     * @param cid
     * @return
     */
    private boolean checkHitsFrequency(HttpServletRequest request, String cid) {
        String val = IPKit.getIpAddrByRequest(request) + ":" + cid;
        Integer count = cache.hget(Types.HITS_FREQUENCY, val);
        if (null != count && count > 0) {
            return true;
        }
        cache.hset(Types.HITS_FREQUENCY, val, 1, WebConstant.HITS_LIMIT_TIME);
        return false;
    }

    /**
     * 前台分类页(如:默认分类等)
     * @param request
     * @param keyword
     * @param limit
     * @return
     */
    @GetMapping(value = "category/{keyword}")
    public String categories(HttpServletRequest request,
                             @PathVariable String keyword,
                             @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.categories(request, keyword, 1, limit);
    }

    @GetMapping(value = "category/{keyword}/{page}")
    public String categories(HttpServletRequest request,
                             @PathVariable String keyword,
                             @PathVariable int page,
                             @RequestParam(value = "limit", defaultValue = "12") int limit) {
        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        Metas metaDto = metaService.getMeta(Types.CATEGORY, keyword);
        if (null == metaDto) {
            return this.render_404();
        }

        PageInfo<Contents> contentsPaginator = contentService.getTagArticles(metaDto.getMid(), page, limit);

        request.setAttribute("articles", contentsPaginator);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "分类");
        request.setAttribute("keyword", keyword);

        return this.render("page-category");
    }

    /**
     * 首页顶部搜索功能
     * @param request
     * @param keyword
     * @param limit
     * @return
     */
    @GetMapping(value = "search/{keyword}")
    public String search(HttpServletRequest request,
                         @PathVariable String keyword,
                         @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.search(request, keyword, 1, limit);
    }

    @GetMapping(value = "search/{keyword}/{page}")
    public String search(HttpServletRequest request,
                         @PathVariable String keyword,
                         @PathVariable int page,
                         @RequestParam(value = "limit", defaultValue = "12") int limit) {
        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        PageInfo<Contents> articles = contentService.searchContentByTitle(keyword, page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "搜索");
        request.setAttribute("keyword", keyword);
        return this.render("page-category");
    }

    /**
     * 首页Tag页面
     * @param request
     * @return
     */
    @GetMapping(value = "/about/searchPage")
    public String searchTags(HttpServletRequest request) {
        List<Metas> tags = metasService.getMetaList(Types.TAG, null, WebConstant.MAX_POSTS);
        request.setAttribute("tagList", tags);
        return "themes/front/search";
    }

    /**
     * 灵魂画师
     * @return
     */
    @GetMapping(value = "soulPainter.html")
    public String soulPainter() {
        return "themes/painter/soulpainter";
    }

}