package com.myblog.config;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * myblog应用上下文
 * @author li192
 */
public class MyBlogContext {

    public static final ThreadLocal<Map<String, Object>> CONTEXT_DATA = new ThreadLocal<>();

    public static final String MENU_LIST = "menuList";

    /**
     * 将数据存入上下文中
     * @param key   指定的key
     * @param value 上下文的内容
     */
    public static void put(String key, Object value) {
        Map<String, Object> data = CONTEXT_DATA.get();
        if (data == null) {
            data = Maps.newHashMap();
            CONTEXT_DATA.set(data);
        }
        data.put(key, value);
    }

    /**
     * 根据key获取上下文中的内容
     *
     * @param clazz 对象类型
     * @param key   指定的key
     * @param <T>   对象泛型
     * @return T
     */
    public static <T> T getObj(Class<T> clazz, String key) {
        if (CONTEXT_DATA.get() == null) {
            return null;
        }
        Object value = CONTEXT_DATA.get().get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    /**
     * 清除上下文
     */
    public static void remove() {
        CONTEXT_DATA.remove();
    }


}
