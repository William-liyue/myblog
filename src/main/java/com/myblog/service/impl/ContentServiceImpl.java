package com.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.constant.WebConstant;
import com.myblog.mapper.ContentsMapper;
import com.myblog.mapper.MetaMapper;
import com.myblog.model.dto.Types;
import com.myblog.model.entity.Contents;
import com.myblog.model.entity.User;
import com.myblog.service.ContentService;
import com.myblog.service.MetaService;
import com.myblog.service.RelationshipService;
import com.myblog.utils.DateKit;
import com.myblog.utils.TaleUtils;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author li192
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentsMapper contentsMapper;

    @Autowired
    private MetaService metaService;

    @Autowired
    private MetaMapper metaMapper;

    @Autowired
    private RelationshipService relationshipService;

    @Override
    public List<Contents> getContentsByType(String type, String status) {
        return contentsMapper.getContentsByType(type, status);
    }

    @Override
    public PageInfo<Contents> getContentsByPageInfo(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        // 查询出所有文章
        List<Contents> list = contentsMapper.getContentsByType(Types.ARTICLE, Types.PUBLISH);
        PageInfo<Contents> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    @Transactional
    public boolean updateContent(Contents content) {
        return contentsMapper.updateContent(content);
    }

    @Override
    public Contents getContentById(Integer id) {
        return contentsMapper.getContentById(id);
    }

    @Override
    public Contents getContentBySlug(String slug) {
        return contentsMapper.getContentBySlug(slug);
    }

    @Override
    public PageInfo<Contents> getContentsConditions(String type, Integer page, Integer limit) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();

        PageHelper.startPage(page, limit);
        List<Contents> list = contentsMapper.getContentsConditions(type, userId == 1 ? -1 : userId);
        PageInfo<Contents> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public String saveContent(Contents contents) {

        if (null == contents) {
            return "文章对象为空";
        }
        if (StringUtils.isBlank(contents.getTitle())) {
            return "文章标题不能为空";
        }
        if (StringUtils.isBlank(contents.getContent())) {
            return "文章内容不能为空";
        }
        int titleLength = contents.getTitle().length();
        if (titleLength > WebConstant.MAX_TITLE_COUNT) {
            return "文章标题过长";
        }
        int contentLength = contents.getContent().length();
        if (contentLength > WebConstant.MAX_TEXT_COUNT) {
            return "文章内容过长";
        }
        if (null == contents.getAuthorId()) {
            return "请登录后发布文章";
        }
        if (StringUtils.isNotBlank(contents.getSlug())) {
            if (contents.getSlug().length() < 5) {
                return "路径太短了";
            }
            if (!TaleUtils.isPath(contents.getSlug())) {
                return "您输入的路径不合法";
            }

            // 查询该文章路径是否存在<类型和缩略名>
            long count = contentsMapper.selectContentByConditions(contents.getType(), contents.getSlug());
            if (count > 0) {
                return "该路径已经存在，请重新输入";
            }
        } else {
            contents.setSlug(null);
        }
        if (StringUtils.isBlank(contents.getTags())) {
            return "文章标签不能为空";
        }

        // 文章内容
        contents.setContent(EmojiParser.parseToAliases(contents.getContent()));
        // 初始化文章创建时间
        int time = DateKit.getCurrentUnixTime();
        contents.setCreated(time);
        contents.setModified(time);
        contents.setHits(0);
        contents.setCommentsNum(0);

        String tags = contents.getTags();
        String categories = contents.getCategories();
        // 保存文章
        contentsMapper.saveContent(contents);
        Integer cid = contents.getCid();
        // 保存标签
        metaService.saveMetas(cid, tags, Types.TAG);
        // 保存分类
        metaService.saveMetas(cid, categories, Types.CATEGORY);
        return WebConstant.SUCCESS_RESULT;

    }

    @Override
    public PageInfo<Contents> getArticlesWithpage(Integer page, Integer limit) {
        // limit9999
        PageHelper.startPage(page, limit);
        // 查询出所有类型为page的文章
        List<Contents> list = contentsMapper.getContentsConditions(Types.PAGE, 1);
        PageInfo<Contents> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public String delContentById(Integer cid) {
        Contents contents = contentsMapper.getContentById(cid);
        if (null != contents) {
            // 根据文章id来删除文章
            contentsMapper.delContentById(cid);
            // 删除对应关系
            relationshipService.delRelationshipByCId(cid);
            return WebConstant.SUCCESS_RESULT;
        }
        return "数据为空";
    }

    @Override
    public PageInfo<Contents> getTagArticles(Integer mid, int page, int limit) {
        // 查询标签下的文章数量
        int total = metaMapper.countWithSql(mid);
        PageHelper.startPage(page, limit);
        List<Contents> list = contentsMapper.getContentsListByMetaId(mid);
        PageInfo<Contents> pageInfo = new PageInfo(list);
        pageInfo.setTotal(total);
        return pageInfo;
    }

    @Override
    public PageInfo<Contents> searchContentByTitle(String title, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Contents> list = contentsMapper.searchContentByTitle(title, Types.ARTICLE, Types.PUBLISH);
        PageInfo<Contents> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    @Transactional
    public void updateRelationShips(Integer cid, String tags, String categories) {
        // 首先清空一下文章id下的关系
        relationshipService.delRelationshipByCId(cid);
        // 更新标签
        metaService.saveMetas(cid, tags, Types.TAG);
        // 更新分类
        metaService.saveMetas(cid, categories, Types.CATEGORY);
    }

    @Override
    public PageInfo<Contents> getArticlesByConditions(String type, String tag, String status, int page, int limit) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        PageHelper.startPage(page, limit);
        List<Contents> list = contentsMapper.getarticlesByConditions(type, tag, status, userId == 1 ? -1 : userId);
        PageInfo<Contents> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int getContentCount() {
        return contentsMapper.getContentCount();
    }
}
