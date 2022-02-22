package com.myblog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.mapper.ContentsMapper;
import com.myblog.model.dto.Archive;
import com.myblog.model.dto.Types;
import com.myblog.model.entity.Comments;
import com.myblog.model.entity.Contents;
import com.myblog.utils.DateKit;
import com.myblog.utils.MapCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:站点service
 * @author li192
 */
@Service
public class SiteService {

    public MapCache mapCache = new MapCache();

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentsMapper contentsMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 查询归档
     * @return
     */
    public List<Archive> getArchives() {
        // 首先从redis中获取
        String redisArchives = (String) redisTemplate.opsForValue().get("archives");
        if (StringUtils.isNotBlank(redisArchives)) {
            return JSONObject.parseArray(redisArchives, Archive.class);
        }
        // 从数据库中获取归档大类
        List<Archive> databaseArchives = contentsMapper.selectArchive();
        if (null != databaseArchives) {
            for (Archive archive : databaseArchives) {
                String date = archive.getDate();
                Date sd = DateKit.dateFormat(date, "yyyy年MM月");
                // 开始时间结束时间
                int start = DateKit.getUnixTimeByDate(sd);
                int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_MONTH, sd, 1)) - 1;
                // 查询符合条件的文章
                List<Contents> contentList = contentsMapper.getContentsByConditions(Types.ARTICLE, Types.PUBLISH, start, end);
                archive.setArticles(contentList);
            }
        }
        String jsonStr = JSON.toJSONString(databaseArchives);
//        // 有效期暂定一天
//        redisTemplate.opsForValue().set("archives", jsonStr, WebConstant.ARCHIVE_EXPIRETIME, TimeUnit.DAYS);
        return databaseArchives;
    }


    /**
     * 最新收到的评论
     *
     * @param limit 评论数
     */
    public List<Comments> recentComments(int limit) {
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        // 查询最新10条评论
        PageInfo<Comments> commentsPageInfo = commentService.selectNewComments(1, limit);
        return commentsPageInfo.getList();
    }

    /**
     * 根据类型获取文章列表
     *
     * @param type  最新,随机
     * @param limit 获取条数
     */
    public List<Contents> getContents(String type, int limit) {
        if (limit < 0 || limit > 20) {
            limit = 10;
        }
        // 最新最近文章
        if (Types.RECENT_ARTICLE.equals(type)) {
            PageHelper.startPage(1, limit);
            // 文章类型和发布状态
            List<Contents> contentList = contentService.getContentsByType(Types.ARTICLE, Types.PUBLISH);
            PageInfo<Contents> pageInfo = new PageInfo(contentList);
            return pageInfo.getList();
        }
        return new ArrayList();
    }


    /**
     * 查询一条评论
     * @param coid
     * @return
     */
    public Comments getComment(Integer coid) {
        if (null != coid) {
            Comments comments = commentService.selectCommentByid(coid);
            return comments;
        }
        return null;
    }

}
