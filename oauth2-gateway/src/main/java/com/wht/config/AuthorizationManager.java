package com.wht.config;

import cn.hutool.core.convert.Convert;
import com.wht.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author wht
 * 鉴权管理器，用于判断是否有资源的访问权限
 * @date 2022/12/6 19:53
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //获取请求uri
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        // 白名单路径直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //对应跨域的预检请求直接放行
        if(request.getMethod()== HttpMethod.OPTIONS){
            return Mono.just(new AuthorizationDecision(true));
        }
        //不同用户体系登录不允许互相访问
        // try {
        //     // 获取token
        //     String token = request.getHeaders().getFirst("Authorization");
        //     if(StrUtil.isEmpty(token)){
        //         return Mono.just(new AuthorizationDecision(false));
        //     }
        //     String realToken = token.replace("Bearer ", "");
        //     // 解析token
        //     JWSObject jwsObject = JWSObject.parse(realToken);
        //     String userStr = jwsObject.getPayload().toString();
        //     UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
        //     // 是否后台管理客服端
        //     if (AuthConstant.ADMIN_CLIENT_ID.equals(userDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
        //         // 规定后台只能是后台的路径，不然不放行
        //         return Mono.just(new AuthorizationDecision(false));
        //     }
        //     if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
        //         return Mono.just(new AuthorizationDecision(false));
        //     }
        // } catch (ParseException e) {
        //     e.printStackTrace();
        //     return Mono.just(new AuthorizationDecision(false));
        // }

        // 进行校验权限
        Map<Object, Object> resourceRolesMap = redisUtil.hmget("auth:resourceRolesMap");
        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
        // 获取该路径所需权限
        List<String> authorities = new ArrayList<>();
        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            if (pathMatcher.match(pattern, uri.getPath())) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
            }
        }
        // authorities = authorities.stream().map(i -> i = "ROLE_" + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                // 判断是否认证成功
                .filter(Authentication::isAuthenticated)
                // 获取全部角色权限
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                // 判断是否包含
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
