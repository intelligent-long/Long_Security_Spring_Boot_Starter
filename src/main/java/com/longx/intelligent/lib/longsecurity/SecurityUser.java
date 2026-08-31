package com.longx.intelligent.lib.longsecurity;

import java.util.List;

/**
 * Created by LONG on 2026/8/30 at 23:07.
 * 抽象用户信息接口
 * 接入方需要让自己的 User 实体类实现此接口
 */
public interface SecurityUser {
    /**
     * 获取当前用户的角色列表
     */
    List<String> getRoles();
}
