# BigDataEcommercePlatform_Backend

## 项目简介

基于大数据的电商精准营销与风险控制平台后端，采用微服务架构，使用 Spring Boot、Spring Cloud 和 Spring Cloud Alibaba 技术栈。

## 技术栈

- Spring Boot 2.7.15
- Spring Cloud 2021.0.8
- Spring Cloud Alibaba 2021.0.5.0
- Nacos 服务注册与配置中心
- Kafka 消息队列
- Redis 缓存
- Elasticsearch 搜索引擎
- XXL-Job 任务调度
- JWT 认证

## 项目结构

```
BigDataEcommercePlatform_Backend/
├── api-gateway/          # 服务网关
├── auth-service/         # 认证服务
├── user-service/         # 用户服务
├── recommend-service/    # 推荐服务
├── risk-service/         # 风险控制服务
├── data-service/         # 数据服务
├── security-service/     # 安全服务
├── system-service/       # 系统服务
└── pom.xml               # 父项目依赖管理
```

## 所需组件

| 组件 | 版本/类型 | 配置信息 | 用途 |
|------|-----------|----------|------|
| **Nacos** | 服务注册与配置中心 | 地址: `localhost:8848` | 所有服务的服务发现和配置管理 |
| **Redis** | 缓存 | 地址: `localhost:6379`<br>端口: `6379` | 缓存、会话管理、令牌存储 |
| **Kafka** | 消息队列 | 地址: `localhost:9092` | 异步消息处理、事件总线 |
| **Elasticsearch** | 搜索引擎 | 地址: `localhost:9200`<br>端口: `9200`<br>协议: `http` | 数据搜索、日志分析 |
| **XXL-Job** | 任务调度 | 地址: `http://localhost:8080/xxl-job-admin`<br>执行器端口: `9999` | 定时任务、批处理 |
| **MySQL** | 关系型数据库 | (需要配置，默认端口: `3306`) | 持久化存储、业务数据 |

## Redis 数据库分配

- **auth-service**: database 0
- **user-service**: database 1
- **recommend-service**: database 2
- **risk-service**: database 3
- **data-service**: database 4
- **security-service**: database 5
- **system-service**: database 6

## Kafka 消费者组

- **user-service**: user-service-group
- **recommend-service**: recommend-service-group
- **risk-service**: risk-service-group
- **data-service**: data-service-group

## JWT 配置

- 密钥: `bigdata_ecommerce_secret_key`
- 访问令牌过期时间: 86400秒 (24小时)
- 刷新令牌过期时间: 604800秒 (7天)

## 服务端口分配

| 服务 | 端口 | 用途 |
|------|------|------|
| **api-gateway** | 8000 | 服务网关，统一入口 |
| **auth-service** | 8001 | 认证服务 |
| **user-service** | 8002 | 用户服务 |
| **recommend-service** | 8003 | 推荐服务 |
| **risk-service** | 8004 | 风险控制服务 |
| **data-service** | 8005 | 数据服务 |
| **security-service** | 8006 | 安全服务 |
| **system-service** | 8007 | 系统服务 |

## API 接口文档

### 认证服务 (auth-service)

| 方法 | 路径 | 功能描述 |
|------|------|----------|
| POST | /api/auth/login | 用户登录 |
| POST | /api/auth/refresh | 刷新访问令牌 |
| POST | /api/auth/logout | 用户登出 |

### 用户服务 (user-service)

| 方法 | 路径 | 功能描述 |
|------|------|----------|
| GET | /api/user/{id} | 获取用户信息 |
| POST | /api/user | 创建用户 |
| PUT | /api/user/{id} | 更新用户信息 |
| DELETE | /api/user/{id} | 删除用户 |
| POST | /api/user/behavior | 收集用户行为 |
| GET | /api/user/behavior/{userId} | 获取用户行为记录 |

### 推荐服务 (recommend-service)

| 方法 | 路径 | 功能描述 |
|------|------|----------|
| GET | /api/recommend/user/{userId} | 获取用户推荐 |
| POST | /api/recommend/feedback | 提交推荐反馈 |
| GET | /api/recommend/strategy/list | 获取推荐策略列表 |
| POST | /api/recommend/strategy/set | 设置推荐策略 |

### 数据服务 (data-service)

| 方法 | 路径 | 功能描述 |
|------|------|----------|
| POST | /api/data/query | 查询数据 |
| POST | /api/data/analyze | 分析数据 |
| POST | /api/data/export | 导出数据 |
| POST | /api/data/ingest | 数据导入 |

### 风险服务 (risk-service)

| 方法 | 路径 | 功能描述 |
|------|------|----------|
| POST | /api/risk/scan | 风险扫描 |
| GET | /api/risk/score/{userId} | 获取风险评分 |
| POST | /api/risk/case/create | 创建风险案例 |
| GET | /api/risk/case/{caseId} | 获取风险案例 |

### 安全服务 (security-service)

| 方法 | 路径 | 功能描述 |
|------|------|----------|
| POST | /api/security/encrypt | 加密数据 |
| POST | /api/security/decrypt | 解密数据 |
| POST | /api/security/mask | 数据脱敏 |
| POST | /api/security/audit | 审计操作 |

### 系统服务 (system-service)

| 方法 | 路径 | 功能描述 |
|------|------|----------|
| GET | /api/system/config/{key} | 获取系统配置 |
| POST | /api/system/config | 设置系统配置 |
| POST | /api/system/log | 记录操作日志 |
| POST | /api/system/alert | 创建系统告警 |
| POST | /api/system/task | 创建系统任务 |

## 部署注意事项

1. **环境准备**
   - 确保所有组件都已安装并运行
   - 配置各组件的网络访问权限

2. **配置文件修改**
   - 根据实际部署环境修改各服务的配置文件
   - 特别是组件的地址、端口和认证信息

3. **依赖关系**
   - Nacos 必须先启动，其他服务才能注册
   - Redis、Kafka 等基础组件必须在服务启动前就绪

4. **安全配置**
   - 修改 JWT 密钥为强密钥
   - 为各组件设置访问密码
   - 配置网络防火墙规则

5. **存储配置**
   - XXL-Job 需要确保日志目录存在: `/data/applogs/xxl-job/jobhandler`
   - 为 Elasticsearch 和 Kafka 配置适当的存储路径

## 启动顺序建议

1. Nacos (服务注册中心)
2. Redis (缓存)
3. Kafka (消息队列)
4. MySQL (数据库)
5. Elasticsearch (搜索引擎)
6. XXL-Job (任务调度)
7. 各微服务 (按任意顺序)
8. API 网关

## 构建与运行

### 构建项目

```bash
mvn clean package
```

### 运行服务

```bash
# 启动 Nacos
# 启动 Redis
# 启动 Kafka
# 启动 MySQL
# 启动 Elasticsearch
# 启动 XXL-Job

# 启动各微服务
java -jar api-gateway/target/api-gateway-1.0.0.jar
java -jar auth-service/target/auth-service-1.0.0.jar
java -jar user-service/target/user-service-1.0.0.jar
java -jar recommend-service/target/recommend-service-1.0.0.jar
java -jar risk-service/target/risk-service-1.0.0.jar
java -jar data-service/target/data-service-1.0.0.jar
java -jar security-service/target/security-service-1.0.0.jar
java -jar system-service/target/system-service-1.0.0.jar
```

## 开发环境要求

- JDK 17+
- Maven 3.9+
- IDE (IntelliJ IDEA 推荐)
- Git

## 项目维护

- 定期更新依赖版本
- 监控服务运行状态
- 优化系统性能
- 完善安全措施

## 许可证

MIT License