package com.longx.intelligent.lib.longsecurity;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by LONG on 2026/8/30 at 23:12.
 */
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);
    private final AccessEvaluator accessEvaluator;
    private final SecuritySessionProvider sessionProvider;

    public SecurityFilter(AccessEvaluator accessEvaluator, SecuritySessionProvider sessionProvider) {
        this.accessEvaluator = accessEvaluator;
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String servletPath = httpServletRequest.getServletPath();
        HttpSession session = httpServletRequest.getSession();
        // 调用接口方法获取用户信息，而不是具体的 SessionService
        SecurityUser currentUser = sessionProvider.getUserOfSession(session);
        boolean access = accessEvaluator.access(servletPath, currentUser);
        if (access) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (session != null) {
                session.invalidate();
            }
            sendAccessDenied(httpServletRequest, httpServletResponse);
        }
    }

    private void sendAccessDenied(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURL().toString();
        HttpSession session = request.getSession(false);
        String sessionId = session != null ? session.getId() : "null";
        LOGGER.error("Access Denied > URL: {}, Session ID: {}", url, sessionId);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}