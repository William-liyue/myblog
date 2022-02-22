package com.myblog.service;

import com.google.gson.Gson;
import com.myblog.constant.DateConst;
import com.myblog.constant.QinFileServerConstants;
import com.myblog.model.entity.Options;
import com.myblog.utils.DateKit;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 七牛云上传服务类
 * @author li192
 */
@Service
public class UploadService {

    private static final Logger log = LoggerFactory.getLogger(UploadService.class);

    private static final Map<String, String> optionMap = new HashMap<>();

    @Autowired
    private OptionService optionService;

    /**
     * 将文件上传到bucket
     */
    public Map<String, Object> upload(InputStream inputStream) {
        if (optionMap.isEmpty()) {
            List<Options> list = optionService.getOptions();
            for (Options option : list) {
                optionMap.put(option.getName(), option.getValue());
            }
        }
        // 构建一个带指定区域对象的配置类
        Configuration cfg = new Configuration(QinFileServerConstants.ZONE_AREA);
        UploadManager manage = new UploadManager(cfg);
        // 生成上传凭证,然后准备上传
        // 默认不指定key的情况下,以文件内容的hash值作为文件名
        String key = optionMap.get(QinFileServerConstants.LOCATION_1) + DateKit.date2Str(new Date(), DateConst.MILLISECOND);
        // 进行身份认证
        Auth upAuth = Auth.create(optionMap.get(QinFileServerConstants.ACCESSKEY), optionMap.get(QinFileServerConstants.SECRETKEY));
        // 生成token
        String upToken = upAuth.uploadToken(optionMap.get(QinFileServerConstants.BUCKET));
        try {
            Response response = manage.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            Map<String, Object> map = new HashMap();
            // -- image.nmyswls.com/nmyswls/article/image/xxxxxxxxxx
            map.put("fileUrl", optionMap.get(QinFileServerConstants.DOMIAN_NAME) + putRet.key);
            map.put("fileNameHash", putRet.hash);
            map.put("upToken", upToken);
            log.info("==========七牛上传文件成功==========");
            return map;
        } catch (QiniuException e) {
            Response r = e.response;
            log.error("==========七牛上传文件失败==========" + r.toString());
        }
        return null;
    }

    public String getURL(String key) {
        //1.构建公开空间访问链接
        try {
            String url = "";
            url = "http://" + QinFileServerConstants.DOMIAN_NAME + "/" + key;
            //2.进行私有授权签名
            Auth auth = Auth.create(QinFileServerConstants.ACCESSKEY, QinFileServerConstants.SECRETKEY);
            //自定义链接过期时间(单位s)
            long expireInSeconds = 3600;//1小时
            //生成下载链接
            String finalUrl = auth.privateDownloadUrl(url, expireInSeconds);
            return finalUrl;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    public String defineFileUrl(String path, String fileName) {
        String resLocation = path + fileName;
        return resLocation;
    }

    /**
     * 删除七牛上的图片
     */
    public void deleteQiniuPicByKey(String path) throws QiniuException {
        String key = path.substring(optionMap.get(QinFileServerConstants.DOMIAN_NAME).length(), path.length());
        Auth upAuth = Auth.create(optionMap.get(QinFileServerConstants.ACCESSKEY), optionMap.get(QinFileServerConstants.SECRETKEY));
        Configuration cfg = new Configuration(QinFileServerConstants.ZONE_AREA);
        BucketManager manage = new BucketManager(upAuth, cfg);
        manage.delete(optionMap.get(QinFileServerConstants.BUCKET), key);
    }

}
