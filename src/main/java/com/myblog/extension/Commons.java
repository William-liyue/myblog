package com.myblog.extension;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.utils.DateKit;
import com.myblog.utils.TaleUtils;
import com.myblog.utils.UUID;
import com.myblog.model.dto.Types;
import com.myblog.model.entity.Comments;
import com.myblog.model.entity.Contents;
import com.myblog.model.entity.Options;
import com.myblog.service.CommentService;
import com.myblog.service.ContentService;
import com.myblog.service.OptionService;
import com.myblog.service.VisitService;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:公共主题函数
 * @author li192
 */
@Component
public final class Commons {

    public static String THEME = "themes/front";

    private static OptionService optionService;

    @Autowired
    public void setOptionService(OptionService optionService) {
        Commons.optionService = optionService;
    }

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private VisitService visitService;

    /**
     * 判断分页中是否有数据
     *
     * @param paginator
     * @return
     */
    public static boolean is_empty(PageInfo paginator) {
        return paginator == null || (paginator.getList() == null) || (paginator.getList().size() == 0);
    }

    /**
     * 网站链接
     *
     * @return
     */
    public static String site_url() {
        return site_url("");
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return
     */
    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }

    /**
     * 网站标题
     *
     * @return
     */
    public static String site_title() {
        return site_option("site_title");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @return
     */
    public static String site_option(String key) {
        return site_option(key, "");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String site_option(String key, String defaultValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        // 从数据库读
        Options option = optionService.getOptionByName(key);
        String str = option.getValue();
        if (StringUtils.isNotBlank(str)) {
            return str;
        } else {
            return defaultValue;
        }
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param len
     * @return
     */
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

    /**
     * 返回主题URL
     *
     * @return
     */
    public static String theme_url() {
        return site_url(Commons.THEME);
    }

    /**
     * 返回主题下的文件路径
     *
     * @param sub
     * @return
     */
    public static String theme_url(String sub) {
        return site_url(Commons.THEME + sub);
    }

    /**
     * 根据用户的ip来返回cute头像
     * @param ip
     * @return
     */
    public static String gravatar(String ip) {
        String avatarUrl = "https://dn-qiniu-avatar.qbox.me/avatar/";
        if (StringUtils.isBlank(ip)) {
            ip = "1927457049@qq.com";
        }
        String hash = TaleUtils.MD5encode(ip.trim().toLowerCase());
        return avatarUrl + hash + ".png";
    }

    /**
     * 返回文章链接地址
     * @param contents
     * @return
     */
    public static String permalink(Contents contents) {
        return permalink(contents.getCid(), contents.getSlug());
    }


    /**
     * 获取随机数
     *
     * @param max
     * @param str
     * @return
     */
    public static String random(int max, String str) {
        return UUID.random(1, max) + str;
    }

    /**
     * 返回文章链接地址
     * @param cid
     * @param slug
     * @return
     */
    public static String permalink(Integer cid, String slug) {
        return site_url("/article/" + (StringUtils.isNotBlank(slug) ? slug : cid.toString()));
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @return
     */
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @param patten
     * @return
     */
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }

    /**
     * 显示分类
     *
     * @param categories
     * @return
     */
    public static String show_categories(String categories) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(categories)) {
            String[] arr = categories.split(",");
            StringBuilder buf = new StringBuilder();
            for (String c : arr) {
                buf.append("<a href=\"/category/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return buf.toString();
        }
        return show_categories("默认分类");
    }

    /**
     * 显示标签
     *
     * @param tags
     * @return
     */
    public static String show_tags(String tags) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(tags)) {
            String[] arr = tags.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<a href=\"/tag/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return "";
    }

    /**
     * 首页截取文章摘要
     * @param content
     * @param len
     * @return
     */
    public static String intro(Contents content, int len) {
        String value = content.getContent();
        int pos = value.indexOf("<!--more-->");
        if (pos != -1) {
            String html = value.substring(0, pos);
            String text = TaleUtils.htmlToText(TaleUtils.mdToHtml(html));
            if (text.length() > len) {
                return text.substring(0, len);
            }
            return text;
        } else {
            String text = TaleUtils.htmlToText(TaleUtils.mdToHtml(value));
            if (text.length() > len) {
                return text.substring(0, len);
            }
            return text;
        }
    }

    /**
     * 显示文章内容，转换markdown为html
     *
     * @param value
     * @return
     */
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return TaleUtils.mdToHtml(value);
        }
        return "";
    }

    /**
     * 显示文章缩略图，顺序为：文章第一张图 -> 随机获取
     *
     * @return
     */
    public static String show_thumb(Contents contents) {
        int cid = contents.getCid();
        int size = cid % 40;
        size = size == 0 ? 10 : size;
        return "/user/img/picture/" + size + ".jpg";
    }

    /**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     * @param value
     * @return
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

    /**
     * 获取文章第一张图片
     * @return
     */
    public static String show_thumb(String content) {
        content = TaleUtils.mdToHtml(content);
        if (content.contains("<img>")) {
            String img = "";
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            Matcher m_image = p_image.matcher(content);
            if (m_image.find()) {
                img = img + "," + m_image.group();
                //匹配src
                Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(img);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return "";
    }

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    /**
     * 显示文章图标
     * @param cid
     * @return
     */
    public static String show_icon(int cid) {
        return ICONS[cid % ICONS.length];
    }

    /**
     * 获取社交的链接地址
     *
     * @return
     */
    public Map<String, String> social() {
        final String prefix = "social_";
        Map<String, String> map = new HashMap<>();
        map.put("weibo", optionService.getOptionByName(prefix + "weibo").getValue());
        map.put("zhihu", optionService.getOptionByName(prefix + "zhihu").getValue());
        map.put("github", optionService.getOptionByName(prefix + "github").getValue());
        map.put("mayun", optionService.getOptionByName(prefix + "mayun").getValue());
        return map;
    }

    /**
     * 获取最近8篇文章
     * @return
     */
    public List<Contents> getContents() {
        PageInfo<Contents> pageInfo = contentService.getContentsConditions(Types.ARTICLE, 1, 8);
        return pageInfo.getList();
    }

    /**
     * 获取最近8条评论
     * @return
     */
    public List<Comments> getComments() {
        PageHelper.startPage(1, 8);
        List<Comments> commentsList = commentService.selectCommentsByAuthorId(1);
        PageInfo<Comments> pageInfo = new PageInfo(commentsList);
        return pageInfo.getList();
    }

    /**
     * 访客次数
     * @return
     */
    public Integer getVisitCount() {
        return visitService.getCountById(1).getCount();
    }

}
