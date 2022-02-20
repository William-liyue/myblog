package com.myblog.service;

import com.myblog.model.entity.Log;

import java.util.List;

public interface ILogService {

    void setUserLog(Log log);

    /**
     * Description: 查询访客记录
     * author: itachi
     * Date: 2018/6/7 下午8:30
     */
    List<Log> searchVisitLogByAction(String action);

    /**
     * Description: 根据行为查询日志
     * author: 70KG
     */
    List<Log> getLogsByAction(String action);

    void deleteLogById(Integer id);

}
