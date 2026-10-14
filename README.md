# idle-goods —— 二手商店（第一个独立 Spring Boot 项目）

我学习 Spring Boot 初期独立完成的第一个项目：一个二手商品交易平台后端。

## 功能

- 用户注册 / 登录（JWT 令牌认证）
- 商品发布、浏览、管理（我的商品）
- 站内消息
- 头像上传（阿里云 OSS）

## 技术栈

Spring Boot · MyBatis（手写 Mapper，未使用代码生成器）· MySQL · JWT · 阿里云 OSS · Maven

## 运行

1. 创建 MySQL 数据库 `idlegoods`（utf8mb4）；
2. 配置数据库账号密码（默认 `root` / `123456`，见 `application.yml`）；
3. OSS 相关配置当前为占位符（`your-access-key-id`），头像上传功能需替换为真实密钥
   （建议以环境变量方式注入，参考 Spring Boot 多环境配置最佳实践）；
4. 运行 `IdleGoodsApplication`。

## 项目说明

- 这是我的"处女作"：Controller / Service / Mapper 全部手写，踩过的坑都变成了经验值
- 学习价值 > 工程价值，README 保持诚实，不夸大
