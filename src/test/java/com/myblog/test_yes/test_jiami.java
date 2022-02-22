package com.myblog.test_yes;

import com.myblog.MyBlogApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description: SpringBoot的单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBlogApplication.class)
public class test_jiami {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        String result = stringEncryptor.encrypt("root");
        System.out.println(result);
    }

}
