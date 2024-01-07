package com.twotwo.common;

import com.twotwo.entity.User;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */
public class UserContext {
    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     */
    public static void setUser(User user) {
        threadLocal.set(user);
    }

    /**
     * 获取值
     */
    public static Integer getId() {
        return threadLocal.get().getId();
    }

    /**
     * 移除
     */
    public static void remove() {
        threadLocal.remove();
    }
    public static User getUser() {
        return threadLocal.get();
    }
}