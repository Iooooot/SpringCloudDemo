# SpringCloudDemo

## 项目简介

`SpringCloudDemo`是一个基于`SpringCloudAlibaba`的微服务Demo。

本项目集成了整套`SpringCloudAlibaba`环境：

- 注册中心/配置中心：`Nacos`
- 服务调用：`OpenFeign`
- 服务熔断和降级：`Sentinal`
- 服务网关：`SpringCloud Gateway`
- 分布式事务：`Seata`

## 开发环境

- JDK 1.8 +
- Maven 3.5 +
- IntelliJ IDEA
- MySQL 8.0 

## Module介绍

| Module名称      | Module介绍                                                   |
| --------------- | ------------------------------------------------------------ |
| ServiceConsumer | 一个微服务消费模块，集成了Nacos、OpenFeign；用于接收外部请求，远程调用其他模块 |
| ServiceProvider | 一个微服务接口提供模块，提供接口给消费者远程调用             |
| config          | 用于测试Nacos做配置中心的小demo，并将配置持久化到本地        |
| sentinelTest    | 一个限流模块，使用Sentinel进行接口的熔断限流测试             |
| orderTest       | 一个订单模块，用于模拟订单生成的事务问题，并用seata分布式事务进行解决 |
| accountProvider | 一个账户模块，用于测试分布式事务，事务远程调用的模块         |
| securityTest    | 一个分布式鉴权中心，基于Oauth2的鉴权协议，负责整个微服务的验证鉴权 |
| oauth2-gateway  | 整个项目的网关，用于拦截外部请求，首先在鉴权中心进行鉴权，通过后再将请求分发对应模块 |

