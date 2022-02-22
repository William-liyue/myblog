package com.myblog.service;

import com.github.pagehelper.PageInfo;
import com.myblog.model.entity.Contents;

import java.util.List;

/**
 * Description:文章内容接口
 * @author li192
 */
public interface ContentService {

    /**
     * 根据类型获取文章列表
     * @param type
     * @param status
     * @return
     */
    List<Contents> getContentsByType(String type, String status);

    /**博客前台文章分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Contents> getContentsByPageInfo(Integer page, Integer limit);

    /**
     * 更新文章内容
     * @param content
     * @return
     */
    boolean updateContent(Contents content);

    /**
     *
     * @param id
     * @return
     */
    Contents getContentById(Integer id);

    /**
     * 根据文章缩略名来查询文章
     * @param slug
     * @return
     */
    Contents getContentBySlug(String slug);

    /**
     * 根据特定条件来查询文章
     * @param type
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Contents> getContentsConditions(String type, Integer page, Integer limit);

    /**
     * 保存文章
     * @param content
     * @return
     */
    String saveContent(Contents content);

    /**
     *
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Contents> getArticlesWithpage(Integer page, Integer limit);


    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    String delContentById(Integer id);


    /**
     * 查询标签下面的所属文章
     * @param mid
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Contents> getTagArticles(Integer mid, int page, int limit);

    /**
     * 根据title来搜索文章
     * @param title
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Contents> searchContentByTitle(String title, int page, int limit);

    /**
     * 修改文章标签关系表
     * @param cid
     * @param tags
     * @param categories
     */
    void updateRelationShips(Integer cid, String tags, String categories);

    PageInfo<Contents> getArticlesByConditions(String article, String tag, String status, int page, int limit);

    /**
     * 查询文章数量
     * @return
     */
    int getContentCount();
}
