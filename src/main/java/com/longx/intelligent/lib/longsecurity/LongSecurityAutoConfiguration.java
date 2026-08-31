package com.longx.intelligent.lib.longsecurity;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LongSecurityAutoConfiguration {

    @Bean
    public AccessPolicyMapper accessPolicyMapper() {
        return new AccessPolicyMapper();
    }

    @Bean
    public AccessEvaluator accessEvaluator(AccessPolicyMapper accessPolicyMapper) {
        return new AccessEvaluator(accessPolicyMapper);
    }

    @Bean
    @ConditionalOnBean(SecuritySessionProvider.class)
    public FilterRegistrationBean<SecurityFilter> securityFilterRegistration(AccessEvaluator accessEvaluator, SecuritySessionProvider sessionProvider) {
        FilterRegistrationBean<SecurityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SecurityFilter(accessEvaluator, sessionProvider));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}