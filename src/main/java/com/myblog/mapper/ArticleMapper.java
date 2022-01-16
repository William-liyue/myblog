package com.myblog.mapper;

import com.myblog.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章列表
 * @author li192
 */
public interface ArticleMapper {

    /**
     *
     * @return
     */
    int articleCount();

    /**
     * 分页显示文章列表
     * @param pageNow
     * @param pageSize
     * @return
     */
    List<Article> findAll(@Param("pageNow") int pageNow,@Param("pageSize") int pageSize);

    /**
     * 根据id获取某篇文章
     * @param id
     * @return
     */
    Article findArticleById(Integer id);

    /**
     * 获取热门文章
     * @return
     */
    List<Article> getHotArticle();

    /**
     * 发布新文章
     * @param article
     * @return
     */
    boolean addArticle(Article article);

    /**
     * 更新文章浏览数
     * @param id
     */
    void updateBrows(Integer id);

    /**
     * 更新评论次数
     * @param id
     */
    void updateComments(Integer id);
}
