package com.myblog.service;

import com.myblog.entity.Article;
import com.myblog.util.Page;

import java.util.List;

/**
 * @author li192
 */
public interface ArticleService {

    /**
     * 以分页形式显示文章列表
     * @param pageNow
     * @param pageSize
     * @return
     */
    Page<Article> findAll(int pageNow,int pageSize);

    /**
     * 根据关键词进行全文搜索
     * @param keyword
     * @param pageNow
     * @param pageSize
     * @return
     */
    Page<Article> search(String keyword,int pageNow,int pageSize);

    /**
     * 获取文章总数
     * @return
     */
    @Deprecated
    int getArticleCount();

    /**
     * 获取热门文章排行
     * @return
     */
    List<Article> getHotArticle();

    Article findArticle(Integer id);

    boolean addArticle(Article article);

    void updateBrows(Integer id);

    void deleteComments(Integer id);
}
