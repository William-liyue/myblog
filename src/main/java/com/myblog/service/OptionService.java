package com.myblog.service;

import com.myblog.model.bo.BackResponseBo;
import com.myblog.model.entity.Options;

import java.util.List;
import java.util.Map;

public interface IOptionService {

    /**
     * Description:根据名字查询配置
     * Author:70kg
     * Param [name]
     * Return Options
     * Date 2018/5/11 13:43
     */
    Options getOptionByName(String name);

    /**
     * Description:所有options
     * Author:70kg
     * Param []
     * Return java.util.List<Options>
     * Date 2018/5/15 15:05
     */
    List<Options> getOptions();

    /**
     * Description:保存或者更新设置
     * Author:70kg
     * Param [querys]
     * Return void
     * Date 2018/5/21 10:37
     */
    void saveOrUpdateOptions(Map<String,String> options);

    void insertOption(String name, String value);

    /**
     * Description:备份数据库
     * Author:70kg
     * Param [bk_type, bk_path, expression]
     * Return BackResponseBo
     * Date 2018/5/31 10:14
     */
    BackResponseBo backup(String bk_type, String bk_path, String expression) throws Exception;
}
