package com.longx.intelligent.lib.longsecurity;

import jakarta.servlet.http.HttpSession;

/**
 * Created by LONG on 2026/8/30 at 23:09.
 * 抽象获取当前会话用户的接口
 * 接入方需要实现此接口并注册为 Spring Bean
 */
public interface SecuritySessionProvider {
    /**
     * 根据 Session 获取当前登录用户
     */
    SecurityUser getUserOfSession(HttpSession session);
}
