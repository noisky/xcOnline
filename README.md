# xcOnline
学成在线学习平台，包括门户、学习中心、教学管理中心、社交系统、系统管理等功能模块

### 项目简介

#### 1 功能架构图

![软件架构图](https://img.ffis.me/images/2019/11/22/1532072105254.png)

#### 2 技术架构

![技术架构图](https://img.ffis.me/images/2019/11/22/1-.jpg)

**业务流程举例**：

1、用户可以通过PC、手机等客户端访问系统进行在线学习。

2、 系统应用CDN技术，对一些图片、CSS、视频等资源从CDN调度访问。

3、所有的请求全部经过负载均衡器。

4、对于PC、H5等客户端请求，首先请求UI层，渲染用户界面。

5、客户端UI请求服务层获取进行具体的业务操作。

6、服务层将数据持久化到数据库。

####  3 项目技术架构简图 

![技术架构简图](https://img.ffis.me/images/2019/11/22/1532072294112.png)

**微服务技术栈：**

学成在线服务端基于Spring Boot构建，采用Spring Cloud微服务框架。

持久层：MySQL、MongoDB、Redis、ElasticSearch

数据访问层：使用Spring Data JPA 、Mybatis、Spring Data Mongodb等

业务层：Spring IOC、Aop事务控制、Spring Task任务调度、Feign、Ribbon、Spring AMQP、Spring Data Redis等。

控制层：Spring MVC、FastJSON、RestTemplate、Spring Security Oauth2+JWT等

微服务治理：Eureka、Zuul、Hystrix、Spring Cloud Config等