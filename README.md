# modern-framework

现代框架（Modern Framework）是一个基于 Spring Boot 的企业级 Java 开发框架，旨在为开发者提供一套完整、高效、可扩展的解决方案，用于快速构建现代化的企业级应用程序。

## 项目概述

Modern Framework 是一个模块化的 Java 框架，整合了现代企业级开发的核心组件，包括 ORM 框架集成、安全模块、基础工具库、核心功能库等。它主要面向中大型企业级应用开发，提供开箱即用的解决方案，减少项目搭建时间，规范开发流程。

## 架构模块

### 1. modern-core（核心模块）
- 提供框架的基础核心功能
- 包含注解（anno）、常量（constant）、类型转换（convert）、函数式接口（func）、语言工具（lang）、映射工具（map）、通用工具（utils）等功能
- 作为整个框架的基石，为其他模块提供基础支持

### 2. modern-base（基础模块）
- 依赖于 `modern-core` 模块
- 集成第三方库：Lombok、Log4j2、Apache Commons、Jackson JSON 处理库等
- 提供增强的工具类和基础功能
- 使用 TransmittableThreadLocal 解决线程池中 ThreadLocal 值传递问题

### 3. modern-orm（对象关系映射模块）
- 包含两个子模块：
  - `modern-orm-api`: ORM 抽象接口和规范
  - `modern-orm-mp`: 基于 MyBatis-Plus 的具体实现
- 提供与数据库交互的标准化方式
- 支持多数据源、动态数据源配置
- 集成了 MyBatis-Plus 的增强功能

### 4. modern-security（安全模块）
- 包含两个子模块：
  - `modern-security-api`: 安全框架的抽象接口
  - `modern-security-spring`: 基于 Spring Security 的具体实现
- 提供权限查询与控制功能
- 实现用户认证、授权、会话管理等安全功能

### 5. modern-boot（启动模块）
- 基于 Spring Boot 构建
- 集成所有其他模块
- 提供自动配置和启动器（Starter）
- 包含 Web 支持、AOP、验证器、执行器等 Spring Boot 核心功能
- 集成 Hibernate Validator 用于参数校验

### 6. modern-dependencies（依赖管理模块）
- 统一管理所有第三方依赖的版本
- 采用 BOM（Bill of Materials）模式确保依赖版本一致性
- 支持多种数据库：MySQL、PostgreSQL、H2、SQL Server、Oracle 等
- 包含 JSON 处理、JWT、MyBatis Plus、Apache Commons 等常用库

## 功能特性

- **模块化设计**：采用多模块架构，可按需引入相关模块
- **ORM 集成**：集成 MyBatis-Plus，提供强大的数据库操作能力
- **安全性**：提供企业级安全解决方案，支持认证授权
- **日志系统**：集成 Log4j2 日志框架
- **JSON 处理**：支持 Jackson 和 FastJSON，提供高效的 JSON 序列化/反序列化
- **多数据源支持**：支持动态数据源切换
- **企业级特性**：包括执行器（Actuator）等生产就绪特性

## 快速开始

### 环境要求
- Java 11+ (支持 Java 8, 11, 17+)
- Maven 3.6+

### 构建项目
```bash
mvn clean install
```

### 使用示例

在你的项目中引入框架：
```xml
<dependency>
    <groupId>com.modernframework</groupId>
    <artifactId>modern-boot</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 依赖关系图

```
modern-parent (pom)
├── modern-core (jar)
├── modern-dependencies (pom)
├── modern-base (jar) → modern-core
├── modern-orm (pom)
│   ├── modern-orm-api (jar) → modern-base
│   └── modern-orm-mp (jar) → modern-orm-api, mybatis-plus
├── modern-security (pom)
│   ├── modern-security-api (jar) → modern-base
│   └── modern-security-spring (jar) → modern-security-api, spring-security
└── modern-boot (jar) → modern-base, modern-orm-mp, spring-boot
```

## 贡献指南

请参阅 [CONTRIBUTING.md](CONTRIBUTING.md) 文件了解如何参与项目贡献。

## 许可证

本项目基于 [LICENSE](LICENSE) 许可证发布。