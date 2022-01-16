package com.myblog.service.Impl;

import com.myblog.entity.Blog;
import com.myblog.mapper.BlogMapper;
import com.myblog.service.BlogService;
import com.myblog.util.MarkdownUtils;
import com.myblog.vo.*;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 博客列表业务层接口实现类
 * @author li192
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public ShowBlog getBlogById(Integer id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<BlogQuery> getAllBlog() {
        return blogMapper.getAllBlogQuery();
    }

    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        return blogMapper.saveBlog(blog);
    }

    @Override
    public int updateBlog(ShowBlog showBlog) {
        showBlog.setUpdateTime(new Date());
        return blogMapper.updateBlog(showBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public List<BlogQuery> getBlogBySearch(SearchBlog searchBlog) {
        return blogMapper.searchByTitleOrTypeRecommend(searchBlog);
    }

    @Override
    public List<FirstPageBlog> getAllFirstPageBlog() {
        return blogMapper.getFirstPageBlog();
    }

    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        List<RecommendBlog> allRecommendBlog = blogMapper.getAllRecommendBlog();
        return allRecommendBlog;
    }

    @Override
    public List<FirstPageBlog> getNewBlog() {
        return blogMapper.getNewBlog();
    }

    @Override
    public List<FirstPageBlog> getSearchBlog(String query) {
        return blogMapper.getSearchBlog(query);
    }

    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        System.out.println(id);
        DetailedBlog detailedBlog = blogMapper.getDetailedBlog(id);
        /*if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }*/
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        // 文章访问数量自增
        blogMapper.updateViews(id);
        // 文章评论数量更新
        blogMapper.getCommentCountById(id);
        return detailedBlog;
    }

    @Override
    public List<FirstPageBlog> getByTypeId(Integer typeId) {
        return blogMapper.getByTypeId(typeId);
    }

    @Override
    public Integer getBlogTotal() {
        return blogMapper.getBlogTotal();
    }

    @Override
    public Integer getBlogViewTotal() {
        return getBlogViewTotal();
    }

    @Override
    public Integer getBlogCommentTotal() {
        return blogMapper.getBlogCommentTotal();
    }

    @Override
    public Integer getBlogMessageTotal() {
        return blogMapper.getBlogMessageTotal();
    }
}
