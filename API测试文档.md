# 后端API测试文档

## 测试环境准备

### 1. 环境配置

| 组件 | 地址 | 端口 |
|------|------|------|
| API网关 | localhost | 8080 |
| Nacos | localhost | 8848 |
| Redis | localhost | 6379 |
| Kafka | localhost | 9092 |

### 2. 服务启动顺序

1. 启动 Nacos 服务注册中心
2. 启动 Redis 缓存
3. 启动 Kafka 消息队列
4. 启动各个微服务
5. 启动 API 网关

### 3. Apifox 配置

1. 新建项目：BigDataEcommercePlatform
2. 环境配置：
   - 基础URL：http://localhost:8080
   - 认证方式：Bearer Token

## API接口测试用例

### 1. 认证服务 (auth-service)

#### 1.1 登录接口

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/auth/login |
| 请求方法 | POST |
| 请求体 | `{"username": "admin", "password": "123456"}` |
| 预期状态码 | 200 |
| 预期响应 | `{"accessToken": "...", "refreshToken": "..."}` |
| 测试步骤 | 1. 发送登录请求<br>2. 保存返回的token |

#### 1.2 刷新令牌接口

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/auth/refresh |
| 请求方法 | POST |
| 请求体 | `{"refreshToken": "..."}` |
| 预期状态码 | 200 |
| 预期响应 | `{"accessToken": "...", "refreshToken": "..."}` |
| 测试步骤 | 1. 使用登录返回的refreshToken<br>2. 发送刷新请求 |

#### 1.3 登出接口

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/auth/logout |
| 请求方法 | POST |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Logout successful" |
| 测试步骤 | 1. 使用登录返回的accessToken<br>2. 发送登出请求 |

### 2. 用户服务 (user-service)

#### 2.1 获取用户信息

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/user/{id} |
| 请求方法 | GET |
| 请求参数 | id=1 |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `{"id": 1, "username": "admin", ...}` |
| 测试步骤 | 1. 发送GET请求获取用户信息 |

#### 2.2 创建用户

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/user |
| 请求方法 | POST |
| 请求体 | `{"username": "testuser", "password": "123456", "email": "test@example.com"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `{"id": 2, "username": "testuser", ...}` |
| 测试步骤 | 1. 发送POST请求创建用户 |

#### 2.3 更新用户信息

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/user/{id} |
| 请求方法 | PUT |
| 请求参数 | id=2 |
| 请求体 | `{"username": "updateduser", "email": "updated@example.com"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `{"id": 2, "username": "updateduser", ...}` |
| 测试步骤 | 1. 发送PUT请求更新用户信息 |

#### 2.4 删除用户

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/user/{id} |
| 请求方法 | DELETE |
| 请求参数 | id=2 |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "User deleted successfully" |
| 测试步骤 | 1. 发送DELETE请求删除用户 |

#### 2.5 收集用户行为

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/user/behavior |
| 请求方法 | POST |
| 请求体 | `{"userId": 1, "behaviorType": "浏览", "behaviorData": "{\"productId\": 101}", "ipAddress": "192.168.1.1"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Behavior collected successfully" |
| 测试步骤 | 1. 发送POST请求收集用户行为 |

#### 2.6 获取用户行为记录

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/user/behavior/{userId} |
| 请求方法 | GET |
| 请求参数 | userId=1 |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `[{"id": 1, "userId": 1, "behaviorType": "浏览", ...}]` |
| 测试步骤 | 1. 发送GET请求获取用户行为记录 |

### 3. 推荐服务 (recommend-service)

#### 3.1 获取用户推荐

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/recommend/user/{userId} |
| 请求方法 | GET |
| 请求参数 | userId=1&limit=5 |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `[101, 102, 103, 104, 105]` |
| 测试步骤 | 1. 发送GET请求获取推荐 |

#### 3.2 提交推荐反馈

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/recommend/feedback |
| 请求方法 | POST |
| 请求体 | `{"userId": 1, "itemId": 101, "action": "点击", "score": 5}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Feedback submitted successfully" |
| 测试步骤 | 1. 发送POST请求提交反馈 |

#### 3.3 获取推荐策略列表

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/recommend/strategy/list |
| 请求方法 | GET |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `["协同过滤", "内容推荐", "热门推荐"]` |
| 测试步骤 | 1. 发送GET请求获取策略列表 |

#### 3.4 设置推荐策略

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/recommend/strategy/set |
| 请求方法 | POST |
| 请求体 | `{"userId": 1, "strategy": "协同过滤"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Strategy set successfully" |
| 测试步骤 | 1. 发送POST请求设置策略 |

### 4. 数据服务 (data-service)

#### 4.1 查询数据

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/data/query |
| 请求方法 | POST |
| 请求体 | `{"queryType": "user_stats", "parameters": {"userId": 1, "timeRange": "7d"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `{"totalOrders": 10, "totalAmount": 1000, ...}` |
| 测试步骤 | 1. 发送POST请求查询数据 |

#### 4.2 分析数据

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/data/analyze |
| 请求方法 | POST |
| 请求体 | `{"analyzeType": "user_behavior", "parameters": {"userId": 1, "timeRange": "30d"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `{"browseCount": 50, "purchaseCount": 5, ...}` |
| 测试步骤 | 1. 发送POST请求分析数据 |

#### 4.3 导出数据

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/data/export |
| 请求方法 | POST |
| 请求体 | `{"exportType": "user_report", "parameters": {"userId": 1, "format": "csv"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "http://localhost:8080/exports/user_report_1.csv" |
| 测试步骤 | 1. 发送POST请求导出数据 |

#### 4.4 数据导入

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/data/ingest |
| 请求方法 | POST |
| 请求体 | `{"dataType": "user_data", "data": {"userId": 3, "username": "newuser", "email": "new@example.com"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Data ingested successfully" |
| 测试步骤 | 1. 发送POST请求导入数据 |

### 5. 风险服务 (risk-service)

#### 5.1 风险扫描

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/risk/scan |
| 请求方法 | POST |
| 请求体 | `{"userId": 1, "action": "登录", "context": {"ipAddress": "192.168.1.1", "deviceId": "abc123"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `{"riskLevel": "低", "score": 0.2, ...}` |
| 测试步骤 | 1. 发送POST请求进行风险扫描 |

#### 5.2 获取风险评分

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/risk/score/{userId} |
| 请求方法 | GET |
| 请求参数 | userId=1 |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `0.2` |
| 测试步骤 | 1. 发送GET请求获取风险评分 |

#### 5.3 创建风险案例

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/risk/case/create |
| 请求方法 | POST |
| 请求体 | `{"userId": 1, "riskType": "异常登录", "description": "异地登录", "evidence": {"ipAddress": "192.168.1.1"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `1` |
| 测试步骤 | 1. 发送POST请求创建风险案例 |

#### 5.4 获取风险案例

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/risk/case/{caseId} |
| 请求方法 | GET |
| 请求参数 | caseId=1 |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `{"id": 1, "userId": 1, "riskType": "异常登录", ...}` |
| 测试步骤 | 1. 发送GET请求获取风险案例 |

### 6. 安全服务 (security-service)

#### 6.1 加密数据

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/security/encrypt |
| 请求方法 | POST |
| 请求体 | `{"data": "敏感数据", "key": "encryption_key"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "加密后的字符串" |
| 测试步骤 | 1. 发送POST请求加密数据 |

#### 6.2 解密数据

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/security/decrypt |
| 请求方法 | POST |
| 请求体 | `{"data": "加密后的字符串", "key": "encryption_key"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "敏感数据" |
| 测试步骤 | 1. 发送POST请求解密数据 |

#### 6.3 数据脱敏

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/security/mask |
| 请求方法 | POST |
| 请求体 | `{"data": "13812345678", "maskType": "phone"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "138****5678" |
| 测试步骤 | 1. 发送POST请求脱敏数据 |

#### 6.4 审计操作

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/security/audit |
| 请求方法 | POST |
| 请求体 | `{"userId": 1, "action": "登录", "details": {"ipAddress": "192.168.1.1", "timestamp": "2026-04-04T10:00:00Z"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Audit record created successfully" |
| 测试步骤 | 1. 发送POST请求记录审计日志 |

### 7. 系统服务 (system-service)

#### 7.1 获取系统配置

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/system/config/{key} |
| 请求方法 | GET |
| 请求参数 | key=system.version |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "1.0.0" |
| 测试步骤 | 1. 发送GET请求获取系统配置 |

#### 7.2 设置系统配置

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/system/config |
| 请求方法 | POST |
| 请求体 | `{"key": "system.version", "value": "1.0.1"}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Config set successfully" |
| 测试步骤 | 1. 发送POST请求设置系统配置 |

#### 7.3 记录操作日志

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/system/log |
| 请求方法 | POST |
| 请求体 | `{"userId": 1, "operation": "修改配置", "details": {"key": "system.version", "oldValue": "1.0.0", "newValue": "1.0.1"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Log recorded successfully" |
| 测试步骤 | 1. 发送POST请求记录操作日志 |

#### 7.4 创建系统告警

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/system/alert |
| 请求方法 | POST |
| 请求体 | `{"level": "WARN", "message": "系统负载过高", "details": {"load": "80%", "timestamp": "2026-04-04T10:00:00Z"}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | "Alert created successfully" |
| 测试步骤 | 1. 发送POST请求创建系统告警 |

#### 7.5 创建系统任务

| 测试项 | 内容 |
|--------|------|
| 请求URL | /api/system/task |
| 请求方法 | POST |
| 请求体 | `{"name": "数据清理任务", "cron": "0 0 0 * * ?", "params": {"days": 30}}` |
| 请求头 | Authorization: Bearer {accessToken} |
| 预期状态码 | 200 |
| 预期响应 | `1` |
| 测试步骤 | 1. 发送POST请求创建系统任务 |

## 测试流程

### 1. 准备工作

1. 启动所有必要的服务组件
2. 打开 Apifox 并创建测试项目
3. 配置测试环境和认证信息

### 2. 执行测试

1. 首先测试认证服务的登录接口，获取访问令牌
2. 使用获取的令牌测试其他需要认证的接口
3. 按照服务模块顺序执行测试用例
4. 记录测试结果和响应时间

### 3. 测试验证

1. 验证每个接口的响应状态码是否符合预期
2. 验证响应数据格式和内容是否正确
3. 验证错误处理是否合理
4. 验证性能是否满足要求

## 预期结果

1. 所有接口都能正常响应
2. 认证和授权机制正常工作
3. 数据处理和业务逻辑正确
4. 系统性能满足要求

## 测试报告模板

| 接口名称 | 请求方法 | URL | 状态码 | 响应时间 | 测试结果 | 备注 |
|---------|---------|-----|--------|----------|---------|------|
| 登录 | POST | /api/auth/login | 200 | 100ms | 通过 | - |
| 刷新令牌 | POST | /api/auth/refresh | 200 | 80ms | 通过 | - |
| 登出 | POST | /api/auth/logout | 200 | 50ms | 通过 | - |
| 获取用户信息 | GET | /api/user/1 | 200 | 60ms | 通过 | - |
| 创建用户 | POST | /api/user | 200 | 120ms | 通过 | - |
| ... | ... | ... | ... | ... | ... | ... |

## 注意事项

1. 测试前确保所有服务都已启动并正常运行
2. 测试过程中注意观察服务日志，及时发现问题
3. 对于需要认证的接口，确保使用有效的访问令牌
4. 测试数据应具有代表性，覆盖正常和异常场景
5. 测试完成后清理测试数据，保持系统清洁

## 故障排查

1. **服务未启动**：检查服务状态和日志
2. **认证失败**：检查令牌是否有效，是否过期
3. **数据库连接问题**：检查数据库服务是否正常运行
4. **网络问题**：检查服务间网络连接是否正常
5. **配置问题**：检查服务配置文件是否正确

通过以上测试文档，可以系统性地对后端API进行测试，确保系统功能正常、性能稳定。