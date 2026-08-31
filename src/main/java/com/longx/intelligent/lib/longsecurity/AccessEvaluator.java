package com.longx.intelligent.lib.longsecurity;

import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2026/8/30 at 23:11.
 */
public class AccessEvaluator {
    private final AccessPolicyMapper accessPolicyMapper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public AccessEvaluator(AccessPolicyMapper accessPolicyMapper) {
        this.accessPolicyMapper = accessPolicyMapper;
    }

    public boolean access(String path, SecurityUser user) {
        for (Map.Entry<String, AccessPolicy> entry : accessPolicyMapper.getAccessControlMap().entrySet()) {
            String antPattern = entry.getKey();
            AccessPolicy accessPolicy = entry.getValue();
            if (pathMatcher.match(antPattern, path)) {
                switch (accessPolicy.allowType()) {
                    case ALL -> {
                        return true;
                    }
                    case AUTHED -> {
                        return user != null;
                    }
                    case HAS_ROLE -> {
                        if (user == null) return false;
                        List<String> userRoles = user.getRoles();
                        if (userRoles == null || userRoles.isEmpty()) return false;
                        // 只要拥有所需的任意一个角色，即放行
                        for (String requiredRole : accessPolicy.roles()) {
                            if (userRoles.contains(requiredRole)) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
            }
        }
        return false; // 如果都不匹配，默认拒绝访问
    }
}