package com.myblog.service.Impl;

import com.myblog.entity.Article;
import com.myblog.mapper.ArticleMapper;
import com.myblog.mapper.CommentMapper;
import com.myblog.mapper.TagMapper;
import com.myblog.service.ArticleService;
import com.myblog.util.Page;
import com.myblog.util.RelativeDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li192
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    @Cacheable(cacheNames = "articleList",key = "T(String).valueOf(#pageNow).concat(T(String).valueOf(#pageSize))")
    public Page<Article> findAll(int pageNow, int pageSize) {
        int startLimit =(pageNow - 1) * pageSize;
        List<Article> articles = articleMapper.findAll(startLimit, pageSize);
        for (Article art : articles) {
            art.setCreateAt(RelativeDateFormat.showTime(art.getCreateTime()));
        }
        int totalCount = articleMapper.articleCount();
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
        Page<Article> page = new Page<Article>();
        page.setData(articles);
        page.setTotalPage(totalPage);
        page.setCurrentPage(pageNow);
        return page;
    }

    @Override
    @Cacheable()
    public Page<Article> search(String keyword, int pageNow, int pageSize) {
        return null;
    }

    @Override
    public int getArticleCount() {
        return 0;
    }

    @Override
    public List<Article> getHotArticle() {
        return null;
    }

    @Override
    @Cacheable(value = "articlesDetail",key = "#id")
    public Article findArticle(Integer id) {
        return articleMapper.findArticleById(id);
    }

    @Override
    @CacheEvict(value = "articlesList",allEntries = true,beforeInvocation = true)
    public boolean addArticle(Article article) {
        try {

        }
        return articleMapper;
    }

    @Override
    public void updateBrows(Integer id) {

    }

    @Override
    public void deleteComments(Integer id) {

    }
}
