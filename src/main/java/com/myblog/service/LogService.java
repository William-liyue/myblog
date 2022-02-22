package com.myblog.service;


import com.myblog.model.entity.Logs;

import java.util.List;

public interface LogService {

    void setUserLog(Logs log);

    /**
     * 查询访客记录
     * @param action
     * @return
     */
    List<Logs> searchVisitLogByAction(String action);

    /**
     * 根据行为查询日志
     * @param action
     * @return
     */
    List<Logs> getLogsByAction(String action);

    /**
     * 删除日志
     * @param id
     */
    void deleteLogById(Integer id);

}
