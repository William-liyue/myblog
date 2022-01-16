package com.myblog.mapper;

import com.myblog.entity.Blog;
import com.myblog.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 博客持久层接口
 * @author li192
 */
@Mapper
@Repository
public interface BlogMapper {

    ShowBlog getBlogById(Integer id);

    List<Blog> getAllBlog();

    List<BlogQuery> getAllBlogQuery();

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    void deleteBlog(Long id);

    List<BlogQuery> searchByTitleOrTypeRecommend(SearchBlog searchBlog);

    List<FirstPageBlog> getFirstPageBlog();

    List<RecommendBlog> getAllRecommendBlog();

    List<FirstPageBlog> getNewBlog();

    List<FirstPageBlog> getSearchBlog(String query);

    DetailedBlog getDetailedBlog(Long id);

    int updateViews(Long id);

    /**根据博客id查询出评论数量*/
    int getCommentCountById(Long id);

    /**根据TypeId获取农科列表，在分类进行的操作*/
    List<FirstPageBlog> getByTypeId(Integer typeId);

    Integer getBlogTotal();

    Integer getBlogViewTotal();

    Integer getBlogCommentTotal();

    Integer getBlogMessageTotal();
}
