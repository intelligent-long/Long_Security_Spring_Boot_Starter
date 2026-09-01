# Long Security Spring Boot Starter
Spring Boot 权限控制库

# 使用步骤
1. 实现 SecurityUser 和 SecuritySessionProvider 接口，将实现类注入 Spring 容器
```java
public interface SecurityUser {
    List<String> getRoles();
}
```
```java
public interface SecuritySessionProvider {
    SecurityUser getUserOfSession(HttpSession var1);
}
```
2. 配置 Security
```java
@Configuration
public class SecurityConfig {
    @Autowired
    public void configureSecurity(AccessPolicyMapper accessPolicyMapper) {
        accessPolicyMapper
                .antMatch("/ws")
                .permitAll()
                .antMatch("/auth")
                .permitAll()
                .antMatch("/url/**")
                .permitAll()
                .antMatch("/**")
                .authed();
    }
}
```
