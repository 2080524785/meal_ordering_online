# 软件工程课程设计——网上订餐系统

## 该仓库为系统的后端部分
http://localhost:8080/backend/index.html
### 后端框架：SpringBoot
### 数据库：Mysql8.0
### 缓存：redis、SpringCache
### 日志：Slf4j
## 简要
* 采用前后端分离架构
* 请求路径使用RestURL风格
* mybatis-plus代码生成器生成代码
* swagger2 3.0版本生成API开发文档
* 配置文件为[application.yml](src%2Fmain%2Fresources%2Fapplication.yml)

## 配置 [config](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fconfig)
### [MybatisPlusCodeGenerator.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2FMybatisPlusCodeGenerator.java)
* 使用Freemarker引擎模板
* 设置作者为 pro
* 对数据库表进行自动生成代码
### [MybatisPlusConfig.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fconfig%2FMybatisPlusConfig.java)
* 配置分页插件Page
### [SwaggerConfig.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fconfig%2FSwaggerConfig.java)
* 默认生成
* 调节拦截器，避免影响传输参数
* 通过该网址查看 http://localhost:8080/swagger-ui/index.html
### [WebConfig.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fconfig%2FWebConfig.java)
* addResourceHandlers，添加资源拦截器，设置静态资源映射
* extendMessageConverters，添加额外的中间消息转换器，并放置第一优先

## 公用 [common](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fcommon)
### [BaseContext.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fcommon%2FBaseContext.java)
* 用户登录时设置 id，在相同进程中 id通用，便于项目的运行

## [dto](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fdto)
### [R.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fdto%2FR.java)
* 结果类，用于前后端交互时的数据传输
* 定义了success方法和error方法
### [SMS.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fdto%2FSMS.java)
* 短信服务类，用于登录短信验证
* 设置sendMsg函数（发送短信）和sendTestMsg测试函数（跳过发送短信）

## 过滤器 [filter](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Ffilter)
### [LoginCheckFilter.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Ffilter%2FLoginCheckFilter.java)
* 对未登录用户作拦截处理
* 对登录用户记录 id，放入BaseContext

## 拦截器 handler
### [DataMetaObjectHandler.java](src%2Fmain%2Fjava%2Fcom%2Fpro%2Fwww%2Fhandler%2FDataMetaObjectHandler.java)
* 在与数据库进行交互时启动
* 不同实体类之间有公共字段，进行公共字段的填充
