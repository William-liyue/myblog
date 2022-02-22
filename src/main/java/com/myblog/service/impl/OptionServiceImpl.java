package com.myblog.service.impl;

import com.myblog.mapper.OptionMapper;
import com.myblog.model.bo.BackResponseBo;
import com.myblog.model.entity.Options;
import com.myblog.service.OptionService;
import com.myblog.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author li192
 */
@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionMapper optionMapper;

    @Override
    public Options getOptionByName(String name) {
        return optionMapper.getOptionByName(name);
    }

    @Override
    public List<Options> getOptions() {
        return optionMapper.getOptions();
    }

    @Override
    public void saveOrUpdateOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            BiConsumer<String, String> biConsumer = this::insertOption;
            options.forEach(biConsumer);
        }
    }


    @Transactional
    public void insertOption(String name, String value) {
        Options option = new Options();
        option.setName(name);
        option.setValue(value);
        if (optionMapper.getOptionByName(name) == null) {
            // 新增设置
            optionMapper.saveOption(option);
        } else {
            // 更新设置
            optionMapper.updateByName(option);
        }
    }

    @Override
    public BackResponseBo backup(String bk_type, String bk_path, String expression) throws Exception {
        BackResponseBo backResponse = new BackResponseBo();
        // 命令行用户名以及密码
        String command = PropertyUtil.getProperty("mysqldump_command");
        // 存放sql文件的路径以及格式
        String fPath = bk_path + "/" + new SimpleDateFormat(expression).format(new Date()) + ".sql";
        Runtime rt = Runtime.getRuntime();
        Process child = rt.exec(command);
        InputStream in = child.getInputStream();
        InputStreamReader input = new InputStreamReader(in, "utf8");
        String inStr;
        StringBuffer sb = new StringBuffer("");
        String outStr;
        BufferedReader br = new BufferedReader(input);
        while ((inStr = br.readLine()) != null) {
            sb.append(inStr + "\r\n");
        }
        outStr = sb.toString();
        FileOutputStream fout = new FileOutputStream(fPath);
        OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
        writer.write(outStr);
        writer.flush();
        in.close();
        input.close();
        br.close();
        writer.close();
        fout.close();
        backResponse.setSqlPath(fPath);
        System.out.println("==========MYSQL导出成功==========");
        return backResponse;
    }

    @Override
    public BackResponseBo exports(String ep_type, String ep_path, String expression) throws Exception {
        BackResponseBo backResponseBo = new BackResponseBo();
        String command = PropertyUtil.getProperty("type_command");
        String fPath = ep_path + "/" + new SimpleDateFormat(expression).format(new Date()) + ".text";
        Runtime runtime = Runtime.getRuntime();
        Process child = runtime.exec(command);
        InputStream in = child.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(in, "utf8");
        String inStr;
        StringBuffer sb = new StringBuffer("");
        String outStr;
        BufferedReader br = new BufferedReader(inputStreamReader);
        while ((inStr = br.readLine()) != null) {
            sb.append(inStr + "\r\n");
        }
        outStr = sb.toString();
        FileOutputStream fileOutputStream = new FileOutputStream(fPath);
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream,"utf8");
        writer.write(outStr);
        writer.flush();
        in.close();
        inputStreamReader.close();
        br.close();
        writer.close();
        fileOutputStream.close();
        backResponseBo.setTypePath(fPath);
        System.out.println("==========MYSQL备份成功==========");
        return backResponseBo;
    }
}
