package com.myblog.service;

import com.myblog.entity.Blog;
import com.myblog.vo.*;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

/**
 * 博客列表业务层接口
 * @author li192
 */
public interface BlogService {

    ShowBlog getBlogById(Integer id);

    List<BlogQuery> getAllBlog();

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    void deleteBlog(Long id);

    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);

    List<FirstPageBlog> getAllFirstPageBlog();

    List<RecommendBlog> getRecommendedBlog();

    List<FirstPageBlog> getNewBlog();

    List<FirstPageBlog> getSearchBlog(String query);

    DetailedBlog getDetailedBlog(Long id);

    /**根据TypeId获取农科列表，在分类进行的操作*/
    List<FirstPageBlog> getByTypeId(Integer typeId);

    Integer getBlogTotal();

    Integer getBlogViewTotal();

    Integer getBlogCommentTotal();

    Integer getBlogMessageTotal();

}
