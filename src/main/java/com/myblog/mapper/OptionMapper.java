package com.myblog.mapper;

import com.myblog.model.entity.Options;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论管理
 * @author li192
 */
@Mapper
public interface OptionMapper {

    Options getOptionByName(@Param("name") String name);

    List<Options> getOptions();

    void saveOption(Options options);

    void updateByName(Options options);
}
