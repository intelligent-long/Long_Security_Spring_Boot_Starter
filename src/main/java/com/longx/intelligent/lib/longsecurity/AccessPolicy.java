package com.longx.intelligent.lib.longsecurity;

/**
 * Created by LONG on 2026/8/30 at 23:10.
 */
public record AccessPolicy(AllowType allowType, String... roles) {
    public enum AllowType { ALL, AUTHED, HAS_ROLE }
}
