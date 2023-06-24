package com.wht.filter;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author wht
 * 将登录用户的JWT转化成用户信息的全局过滤器
 * @date 2022/12/6 21:42
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StrUtil.isEmpty(token)) {
            // jwt为空直接放行
            return chain.filter(exchange);
        }
        //从token中解析用户信息并设置到Header中去
        String realToken = StrUtil.subAfter(token, "Bearer ", false);
        Claims body = Jwts.parser()
                .setSigningKey("wht".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(realToken)
                .getBody();
        String userStr = body.toString();
        LOGGER.info("AuthGlobalFilter.filter() user:{}",userStr);
        ServerHttpRequest request = exchange.getRequest().mutate().header("Authorization", userStr).build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
