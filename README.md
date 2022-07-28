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

## 4. 配置Lombok插件

### 4.1 安装插件

![image-20220720164551662](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720164551.png)

### 4.2 添加依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.22</version>
</dependency>
```

## 5. Mybatis配置

### 5.1 添加依赖

```xml
<!--Mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.3</version>
</dependency>

<!--Mysql For Java-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.22</version>
</dependency>
```

### 5.2 修改配置文件

```xml
# 增加数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/wiki?characterEncoding=UTF8&autoReconnect=true&serverTimezone=Asia/Shanghai&allowMultiQueries=true
spring.datasource.username=wiki
spring.datasource.password=******
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#配置mybatis所有Mapper.xml所在的路径
mybatis.mapper-locations=classpath:/mapper/**/*.xml
```

### 5.3 Mybatis Generator配置

#### 1）添加依赖插件

```xml
<!-- mybatis generator 自动生成代码插件 -->
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.4.0</version>
    <configuration>
        <configurationFile>src/main/resources/generator/generator-config.xml</configurationFile>
        <overwrite>true</overwrite>
        <verbose>true</verbose>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.22</version>
        </dependency>
    </dependencies>
</plugin>
```

#### 2）创建相关配置文件

![image-20220720213631598](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720213631.png)

#### 3）运行配置文件

![image-20220720214036770](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220720214036.png)

## 6. 通用返回配置类

```JAVA
@Data
public class CommonResp<T> {

    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T content;
}
```

根据我的经验，还可以进一步进行改造完善

## 7. CORS跨域问题

```JAVA
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }
}
```

## 8. 前端图标导入

```javascript
import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import * as Icons from '@ant-design/icons-vue'

const app = createApp(App);
app.use(store).use(router).use(Antd)
    .mount('#app')

//全局使用图标
const icons: any = Icons
for (const i in icons) {
    app.component(i, icons[i]);
}
```

![image-20220721160136248](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220721160136.png)

## 9. Swagger

### 9.1 添加依赖

后两者都是UI，二者任选其一即可，推荐`swagger-bootstrap-ui`

![image-20220722110633643](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220722110633.png)

```XML
<!--Swagger-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.7.0</version>
</dependency>

<!--Swagger UI-->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>swagger-bootstrap-ui</artifactId>
    <version>1.8.7</version>
</dependency>
```

### 9.2 属性配置

配置之后打开路径是`baseURL+/doc.html`

`@Profile({"dev", "test", "pre", "prod"})`配置作用路径

```JAVA
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2UiConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger演示")
                .description("API文档展示")
                .version("0.0.1")
                .build();
    }
}
```

### 9.3 常用注解

![image-20220722111021034](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220722111021.png)

## 10. Redis

### 10.1 添加依赖

```XML
<!--Redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 10.2 配置编码

```JAVA
@Configuration
public class RedisTemplateBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(RedisTemplate.class.isAssignableFrom(bean.getClass())) {
            RedisTemplate redisTemplate = (RedisTemplate)bean;
            StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringRedisSerializer);
            redisTemplate.setValueSerializer(stringRedisSerializer);
        }
        return bean;
    }
}
```

## 11. WebSocket

### 11.1 添加依赖

```XML
<!--WebSocket-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

### 11.2 添加配置文件

```JAVA
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

### 11.3 服务端

```JAVA
@Component
@ServerEndpoint("/ws/{token}")
public class WebSocketServer {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 每个客户端一个token
     */
    private String token = "";

    private static HashMap<String, Session> map = new HashMap<>();

    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        map.put(token, session);
        this.token = token;
        LOG.info("有新连接：token：{}，session id：{}，当前连接数：{}", token, session.getId(), map.size());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        map.remove(this.token);
        LOG.info("连接关闭，token：{}，session id：{}！当前连接数：{}", this.token, session.getId(), map.size());
    }

    /**
     * 收到消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("收到消息：{}，内容：{}", token, message);
    }

    /**
     * 连接错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("发生错误", error);
    }

    /**
     * 群发消息
     */
    public void sendInfo(String message) {
        for (String token : map.keySet()) {
            Session session = map.get(token);
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                LOG.error("推送消息失败：{}，内容：{}", token, message);
            }
            LOG.info("推送消息：{}，内容：{}", token, message);
        }
    }
}
```

### 11.4 VUE配置

![image-20220727211148168](https://echochao.oss-cn-hangzhou.aliyuncs.com/img/20220727211148.png)

### 11.5 前端连接WebSocket

```javascript
<script lang="ts">
import { defineComponent, computed, onMounted } from 'vue';
import store from "@/store";
import {Tool} from "@/util/tool";
import { notification } from 'ant-design-vue';

export default defineComponent({
  name: 'the-footer',
  setup() {
    const user = computed(() => store.state.user);

    let websocket: any;
    let token: any;
    const onOpen = () => {
      console.log('WebSocket连接成功，状态码：', websocket.readyState)
    };
    const onMessage = (event: any) => {
      console.log('WebSocket收到消息：', event.data);
      notification['info']({
        message: '收到消息',
        description: event.data,
      });
    };
    const onError = () => {
      console.log('WebSocket连接错误，状态码：', websocket.readyState)
    };
    const onClose = () => {
      console.log('WebSocket连接关闭，状态码：', websocket.readyState)
    };
    const initWebSocket = () => {
      // 连接成功
      websocket.onopen = onOpen;
      // 收到消息的回调
      websocket.onmessage = onMessage;
      // 连接错误
      websocket.onerror = onError;
      // 连接关闭的回调
      websocket.onclose = onClose;
    };

    onMounted(() => {
      // WebSocket
      if ('WebSocket' in window) {
        token = Tool.uuid(10);
        // 连接地址：ws://127.0.0.1:8880/ws/xxx
        websocket = new WebSocket(process.env.VUE_APP_WS_SERVER + '/ws/' + token);
        initWebSocket()

        // 关闭
        // websocket.close();
      } else {
        alert('当前浏览器 不支持')
      }
    });

    return {
      user
    }
  }
});
</script>
```

## 12. RocketMQ

### 12.1 添加依赖

```XML
<!--RocketMQ-->
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-spring-boot-starter</artifactId>
    <version>2.0.2</version>
</dependency>
```

### 12.2 添加配置

```properties
#rocketmq
rocketmq.name-server=127.0.0.1:9876
rocketmq.producer.group=default
```

### 12.3 生产者

```JAVA
@Service
public class DocService {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void vote(Long id) {
        rocketMQTemplate.convertAndSend("VOTE_TOPIC", "【" + docDb.getName() + "】被点赞！");
    }
}
```

### 12.4 消费者

```JAVA
@Service
@RocketMQMessageListener(consumerGroup = "default", topic = "VOTE_TOPIC")
public class VoteTopicConsumer implements RocketMQListener<MessageExt> {

    private static final Logger LOG = LoggerFactory.getLogger(VoteTopicConsumer.class);

    @Resource
    public WebSocketServer webSocketServer;

    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        LOG.info("ROCKETMQ收到消息：{}", new String(body));
        webSocketServer.sendInfo(new String(body));
    }
}
```

