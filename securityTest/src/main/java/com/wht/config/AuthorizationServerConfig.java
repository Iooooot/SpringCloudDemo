package com.wht.config;

import com.wht.service.UserDetailsServiceImpl;
import com.wht.util.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author wht
 * @date 2022/11/24 21:13
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;


    @Autowired
    @Qualifier("jwtAccessTokenConverter")
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;



    /**
     * 用来配置客户端详情服务
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 自动从数据库查询出来进行校验
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置从数据库查询客户端信息
     * @param dataSource
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }



    /**
     * 配置令牌访问管理
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 添加增强器
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer,accessTokenConverter));
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                // 用户信息
                .userDetailsService(userDetailsService)
                // 配置令牌存储策略
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain)
                // 允许post提交
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }


    /**
     * 令牌端点的安全约束配置
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                // 允许公钥获取端点
                .tokenKeyAccess("permitAll()")
                // 允许检查token
                .checkTokenAccess("permitAll()")
                // 允许表单认证
                .allowFormAuthenticationForClients();
    }
}


// @Bean
// public AuthorizationServerTokenServices tokenService() {
//
//     DefaultTokenServices service=new DefaultTokenServices();
//     //客户端信息服务
//     service.setClientDetailsService(clientDetailsService);
//     // 是否产生刷新令牌
//     service.setSupportRefreshToken(true);
//     // 令牌存储策略
//     service.setTokenStore(tokenStore);
//     TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//     tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
//     service.setTokenEnhancer(tokenEnhancerChain);
//     // 令牌默认有效期2小时
//     service.setAccessTokenValiditySeconds(7200);
//     // 刷新令牌默认有效期3天
//     service.setRefreshTokenValiditySeconds(259200);
//     return service;
// }