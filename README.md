# Wiki项目

## 1. HTTP CLIENT 测试工具

IDEA自带的测试接口工具

目前感觉还是可以的

但是没有图形化操作界面的便捷，各有优势

![image-20220720155751440](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720155751.png)

## 2. 配置文件

```
默认位置配置文件的优先级
1.–file:./config/
2.–file:./
3.–classpath:/config/
4.–classpath:/
```

![1760851-20210910155513532-946415570](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720160117.png)

## 3. 热部署功能

### 3.1 先添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

### 3.2 IDEA设置

![image-20220720160835454](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720160835.png)

### 3.3 注册表（Registry）

![image-20220720161046683](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720161046.png)

### 3.4 热部署

![image-20220720161229495](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720161229.png)