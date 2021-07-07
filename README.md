<p align="center">
	<img src="https://img.shields.io/badge/JDK-1.8+-orange">
	<img src="https://img.shields.io/badge/SpringBoot-2.3.0.RELEASE-brightgreen">
	<img src="https://img.shields.io/badge/MyBatisPlus-3.3.2-red">
	<img src="https://img.shields.io/badge/Vue-2.6.12-brightgreen">
	<img src="https://img.shields.io/badge/license-MIT-blue">	
</p>

## 博客预览
[参见博客](39.98.113.38)
ps:服务器性能太弱，加载可能比较慢



## 简介

项目是基于 Spring Boot + Vue 的前后端分离博客系统。

## 前端

核心框架：
 - Vue框架：Vue
 - UI框架：Element UI框架
 - 异步请求：axios

## 后端

- 核心框架：Spring Boot
- 安全框架：shiro
- Token 认证：jwt
- 持久层框架：MyBatisPlus
- java版本：JDK8


## 功能

- 登录功能
- 注册功能
- 博客管理：
    - 博客发布
    - 博客编辑
    - 博客删除
    
    
### 尚未填坑
- 文章分类展示
- 导航栏
- 博主介绍展示
- 评论功能
- 游客统计
- 样式美化
- 主题可换
- 后端优化

## 项目快速开始
1. 执行myblog.sql创建数据库 
2. 开启redis
3. idea中修改yml配置，然后运行后端项目
4. 安装npm 在idea中启动前端页面


## 项目线上部署
1. 使用docker-compose来编排 
2. 需要nginx、redis、后端项目、mysql
