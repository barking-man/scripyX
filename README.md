## 2 bug修复记录
### 2024.5.6
- 修复netty服务端无法接收客户端消息的bug（客户端的handler需要主动使用ctx.write()方法将数据写入channel）
### 2024.9.2
- springboot2.x.x版本以后，nacos需要使用bootstrap作为服务配置文件，使用application.yml会导致无法读取配置
### 2024.9.3
- springboot版本小于2.4.0版本，需要添加如下依赖，才能识别bootstrap中的配置
```xml
- <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-context</artifactId>
    <version>2.2.5.RELEASE</version>
  </dependency>
```
- spring boot admin 2.x.x的版本需要使用@EnableAdminServer注解（加在启动类上），然后通过localhost:PORT访问admin的server端
