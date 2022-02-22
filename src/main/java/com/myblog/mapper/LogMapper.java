package com.myblog.mapper;

import com.myblog.model.entity.Logs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author li192
 */
@Mapper
public interface LogMapper {

    void visitSetLog(Logs log);

    List<Logs> searchVisitLogByAction(@Param("action") String action);

    List<Logs> getLogsByAction(@Param("action") String action);

    void deleteLogById(@Param("id") Integer id);
}
