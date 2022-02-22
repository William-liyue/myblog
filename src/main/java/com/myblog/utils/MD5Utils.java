package com.myblog.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author li192
 */
public class MD5Utils {

    private static final String SALT = "MYSQL";

    private static final String ALGORITH_NAME = "MD5";

    /** 哈希次数 **/
    private static final int HASH_ITERATIONS = 2;

    public static String encrypt(String pwd) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pwd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
        return newPassword;
    }

    public static String encrypt(String username, String pwd) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pwd, ByteSource.Util.bytes(username + SALT),
                HASH_ITERATIONS).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        System.out.println(MD5Utils.encrypt("admin", "admin"));
    }

}
