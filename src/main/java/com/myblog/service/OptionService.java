package com.myblog.service;


import com.myblog.model.bo.BackResponseBo;
import com.myblog.model.entity.Options;

import java.util.List;
import java.util.Map;

/**
 * @author li192
 */
public interface OptionService {

    /**
     * 根据名字查询配置
     * @param name
     * @return
     */
    Options getOptionByName(String name);

    /**
     * 所有options
     * @return
     */
    List<Options> getOptions();

    /**
     * 保存或者更新设置
     * @param options
     */
    void saveOrUpdateOptions(Map<String,String> options);

    void insertOption(String name, String value);

    /**
     * 备份数据库
     * @param bk_type
     * @param bk_path
     * @param expression
     * @return
     * @throws Exception
     */
    BackResponseBo backup(String bk_type, String bk_path, String expression) throws Exception;

    BackResponseBo exports(String ep_type, String ep_path, String expression) throws Exception;
}
