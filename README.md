# 软件工程课程设计——网上订餐系统

该仓库为系统的后端部分，主要使用技术为  

使用mybatis-plus代码生成器生成代码，使用swagger2 3.0版本生成API开发文档
## 配置 Config
### MybatisPlusCodeGenerator
* 使用Freemarker引擎模板
* 设置作者为 pro
* 对数据库表进行自动生成代码
### SwaggerConfig
* 默认生成
* 调节拦截器，避免影响传输参数
* 通过该网址查看 http://localhost:8080/swagger-ui/index.html
### WebConfig
* addResourceHandlers，添加资源拦截器，设置静态资源映射
* extendMessageConverters，添加额外的中间消息转换器，并放置第一优先


## 员工 Employee

### 登录

* 输入账号密码，查询数据库进行匹配
* 设置过滤器，拦截跳过登录的请求

### 添加
* 对员工账户进行唯一性判断

### 修改
* 根据ID寻找员工信息，实现前端回显
* 提交修改进行账号唯一性判断

### 禁用
* 查询数据库中status信息，依托前端代码完成