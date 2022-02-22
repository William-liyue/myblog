package com.myblog.mapper;

import com.myblog.model.dto.Archive;
import com.myblog.model.entity.Contents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author li192
 */
@Mapper
public interface ContentsMapper {

    /**
     * 根据类型查询最新发布的文章
     * @param type
     * @param status
     * @return
     */
    List<Contents> getContentsByType(@Param("type") String type, @Param("status") String status);

    /**
     * 更新文章
     * @param content
     * @return
     */
    boolean updateContent(Contents content);

    /**
     * 根据id获取文章
     * @param cid
     * @return
     */
    Contents getContentById(@Param("cid") Integer cid);

    /**
     * 查询归档文章
     * @return
     */
    List<Archive> selectArchive();

    /**
     * 查询文章by特定条件
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<Contents> getContentsByConditions(@Param("type") String type, @Param("status") String status, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);

    /**
     * 根据文章缩略名来查询文章
     * @param slug
     * @return
     */
    Contents getContentBySlug(@Param("slug") String slug);

    /**
     * 根据特定条件来查询文章列表
     * @param type
     * @param userId
     * @return
     */
    List<Contents> getContentsConditions(@Param("type") String type, @Param("userId") Integer userId);

    /**
     * 保存文章
     * @param content
     */
    void saveContent(Contents content);

    /**
     * 根据条件查询文章的个数
     * @param type
     * @param slug
     * @return
     */
    long selectContentByConditions(@Param("type") String type, @Param("slug") String slug);

    /**
     * 根据id删除文章
     * @param cid
     */
    void delContentById(@Param("cid") Integer cid);

    /**
     * 根据mid来查询标签下的所有文章
     * @param mid
     * @return
     */
    List<Contents> getContentsListByMetaId(@Param("mid") Integer mid);

    List<Contents> searchContentByTitle(@Param("title") String title, @Param("type") String type, @Param("status") String publish);

    List<Contents> getarticlesByConditions(@Param("type") String type, @Param("tag") String tag, @Param("status") String status, @Param("userId") Integer userId);

    int getContentCount();
}
