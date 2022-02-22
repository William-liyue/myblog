package com.myblog.service.impl;

import com.myblog.mapper.LogMapper;
import com.myblog.model.entity.Logs;
import com.myblog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author li192
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    @Transactional
    public void setUserLog(Logs log) {
        logMapper.visitSetLog(log);
    }

    @Override
    public List<Logs> searchVisitLogByAction(String action) {
        return logMapper.searchVisitLogByAction(action);
    }

    @Override
    public List<Logs> getLogsByAction(String action) {
        return logMapper.getLogsByAction(action);
    }

    @Override
    @Transactional
    public void deleteLogById(Integer id) {
        logMapper.deleteLogById(id);
    }

}
