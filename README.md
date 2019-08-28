## 启动bootstrap和Springboot组合的项目
访问https://v3.bootcss.com/css/ 下载BootStrap
1.解压，并将其中的css,fonts,js文件夹复制到项目的/resources/static文件夹下
2.resources文件夹下新建一个templates文件夹
3.在thymeleaf官网下载一个index.html的使用模板，放在templates文件夹下；网址https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
# 第一步：
```html
    <!DOCTYPE html>

  <html xmlns:th="http://www.thymeleaf.org">

  <head>
      <title>亮亮社区</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <!--三个资源文件的引入-->
      <link rel="stylesheet" href="css/bootstrap.min.css">
      <link rel="stylesheet" href="css/bootstrap-theme.min.css">
      <script src="js/bootstrap.min.js" type="application/javascript"></script>

  </head>
  <body>
  </body>
  </html>
```


# 第二步：
 在bootstrap官网找一个导航栏样式，放入index.html的body中去

# 第三步：写一个controller
    注意这里的注解用的是@Controller,我使@RestController的时候，访问浏览器返回的是字符串：index，原因还没查：
```java
    package com.community.commucity;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    
    /**
     * Created by 舒先亮 on 2019/8/20.
     */
    @Controller
    public class CommucityController {
    
        @GetMapping("/")
        public String index(){
            return "index";
        }
    }

```

    此时我以为能够返回"index.html",但是由于没有在pom.xml文件中加入thymeleaf的依赖：
```xml
            <dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-thymeleaf</artifactId>
    		</dependency>
```
             

# 第四步：看一下github主页的api,主页拉到最下面有一个API接口
    找到:Building a GitHub App or an OAuth App     创建一个GitHub的APP，或者授权APP

# 第五步：git操作
    在这之前，由于没有操作过git的提交，所以尝试先把我的workspace“springboot”初始化为管理库repository，
    windows下，先进入springboot文件夹，按住Ctrl+鼠标右键，点击进入git bash
    命令：git init             https://www.liaoxuefeng.com/wiki/896043488029600/896827951938304
    添加：git add .
    提交：git commit -m "add master of workspace"

    设置身份认证：我是谁  git config --global user.email "1610700471@qq.com"
                        git config --global user.name "Jackshufu"

# 第六步：git初始化并提交远程仓库
    在新建master的时候，发现其实只要进入到某个project文件夹，就可以使用git init，然后初始化好后就能git add . ,最后再提交
    当git push之前，还需要配置远程仓库的目的地
    1.先在github上创建一个仓库，然后获得一个访问地址，再然后使用命令git remote add origincommunity https://github.com/Jackshufu/community.git
    在本地关联远程仓库
    2.使用 git push -u origincommunity master  上传至github，蛋疼的是需要输入github密码，用户名

## 工具
   [visual-paradigm（画UML图工具）](https://www.visual-paradigm.com/cn/)
   
# 第七步：申请github的APP，用于github授权APP登录
        进入github->settings->Developer settings->New Github App
        设置APP name（Community），网址http://community.shu.com
        webhook,我写的是本地关联远程仓库的网址：https://github.com/Jackshufu/community.git
```
        Owned by: @Jackshufu
        
        App ID: 39020
        
        Client ID: Iv1.c83b663ef84a8086
        
        Client secret: 8c41ac8a3ab868d25fbb36f8977172cbb45ad2b7
```
        
    
    GitHub授权登录的时序：
    1.用户访问我的社区，在社区上处理自己的登录逻辑，然后调用authorize接口去访问github，然后github回调我们注册APP时填写的
    redirect-uri并携带code，当社区接收到这个信息后，会再次调用githubde access_token接口，并携带code，这步为了获取github
    的token，然后当github验证成功后，返回access_token给社区，社区可以通过access_token来调用GitHub的userAPI，github返回user信息
    此时可以更新登录状态，把获取到的user信息保存到数据库，最后返回用户登录成功

# 步骤八：添加fastjson依赖
```xml
    <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.59</version>
    </dependency>
```


    添加okhttp依赖
```xml
        <dependency>
			<groupId>com.squareup.okhttp3</groupId>
			<artifactId>okhttp</artifactId>
			<version>3.14.1</version>
		</dependency>
```
		
    json格式的数据转换成对象

# 步骤九：当github通过验证并发放认证令牌后，社区如何通过access_token发起请求访问userAPI拿到user信息？
    首先在github的Personal access tokens新建一个私人访问token，在官网API的描述下，模拟一下
     Google打开匿名窗口：win   Ctrl+shift+N
     拼接如下：https://api.github.com/user?access_token=f47c9b74247c9342f4183c0543c704733ab5441e
     能够看到用户相关信息
        其中我可以关注id ,name , bio  
    
# 步骤十：整个代码写完了，诸如client_id,client_secret,redirect_uri这些参数写在代码中不合适，我将他配置在application.properties
 中，如下(注意配置文件值的末端不能添加;号)：
 ```properties
            github.client.id = Iv1.c83b663ef84a8086
            github.client.secret = 8c41ac8a3ab868d25fbb36f8977172cbb45ad2b7
            github.redirect.uri = http://localhost:8080/callback
```
            
 
    代码中依赖注入用@Value，其中${}中填的是配置文件中的key
 ```java
            @Value("${github.client.id}")
            private String clientId;
            @Value("${github.client.secret}")
            private String clientSecret;
            @Value("${github.redirect.uri}")
            private String redirectUri;
 ```
           
# 步骤十一：什么是session，什么是cookie？
    session就相当于银行账户，cookie就相当于银行卡，拿来银行卡，才能知道银行账户
    HTTP是无状态的，怎样让服务器记住我的状态，每次带一个cookie来就好了
     1.在CallBackController的形参中加入
     
     
     菜鸟教程：https://www.runoob.com
# 步骤十二：
    1.在application.properties中配置MySQL数据库：
```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/store?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
        spring.datasource.username=root
        spring.datasource.password=root
```
    2.添加JPA、mysql、jdbc依赖
```xml
        <dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
```
        
        
 1. 思考关于cookie的问题，之所以能够在浏览器能找到服务器端，是因为页面发送那个了一个cookie给服务器，服务器端通过框架的解释将cookie路由到
 了session，我们不使用框架为我们提供的session值，我们需要自己设置session值,登录成功之后，手动将session值设置到cookie里，这个session就是一个
 token，访问数据库的时候，我们可以拿着这个token去数据库里查。
 2. 将自己设置的session(token)存入数据库后，我们可以手动模拟cookie和session之间的交互，保证服务器宕机或者重启服务器的时候，
    都会使得用户重新登录，并保有登录态->登录成功后，通过java代码往前端写cookie，以token为依据，来绑定前端和后端的登录状态。
 3. 把java代码中的UUID.randomUUID().toString()抽取出来，来代替曾经的session
```java
     String token = UUID.randomUUID().toString()
```

    
    
    int     对应java中的int
    bigint  对应java中的long类型
    gmt     格林威治时间
    
# 步骤十三：集成mybatis,百度搜索Springboot mybatis，http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
    1.添加MyBatis依赖：(这样才能够在Mapper类中使用@Mapper注解)
```xml
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
```
        
     2.@Insert注解和里面的sql就相当于xml中的映射配置了，注解写在哪，就相当于于映射谁
     
    
     备注：
        1.类类之间，对象在网络中传输使用DTO;
        2.对象在数据库中的话，我们叫他model
        3.service层需要抛出异常，controller层可以同一个异常，多处try-catch
        
# 步骤十四：还是要手动下的热部署
    1. 在application.properties文件中配置 ：spring.thymeleaf.cache=false
    此步骤先清除thymeleaf的缓存
    1.1：重新启动一下主程序(run一下)，然后关闭主程序（否则执行1.2报错占用端口） 之后修改html，js，css等相关文件无需重启，按 Ctrl+f9 手动加载一下资源后直接刷新页面就可以看到可以修改，这样热部署就算成功了
    1.2:使用maven方式启动：mvn spring-boot:run
    2. 使用devtools方式
    增加maven的devtools依赖
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
```
    3.java代码热加载
```xml
    <plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<fork>true</fork>
				</configuration>
			</plugin>
```

# 步骤十五：引用jQuery和bootstrap.min.js顺序
    如果bootstrap.min.js在jQuery的前面，则浏览器报错
    Uncaught Error: Bootstrap's JavaScript requires jQuery at bootstrap.min.js:6
    百度找到一个jQuery文件，问题解决：
```html
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
```

# 步骤十六：提交问题到数据库
    1. 建表
```mysql
      CREATE TABLE `community`.`question` (
      `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键自增',
      `title` VARCHAR(50) NULL COMMENT '问题标题',
      `description` TEXT NULL COMMENT '问题的详细描述，放在文本编辑框，文本类型是TEXT类型',
      `gmt_create` BIGINT NULL,
      `gmt_modified` BIGINT NULL,
      `creator` INT NULL,
      `comment_count` INT NULL DEFAULT 0,
      `view_count` INT NULL DEFAULT 0,
      `like_count` INT NULL DEFAULT 0,
      `tag` VARCHAR(256) NULL,
      PRIMARY KEY (`id`))
    COMMENT = '用于提交问题的表';
```

    2. 写一个QuestionMapper
```java
    package com.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 舒先亮 on 2019/8/24.
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert int question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title}),#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag}")
    void insertQuestion();
}

```

    3.后台通过Model传值给thymeleaf模板中的<input>输入框的时候，用的是th:value = "${Model的属性}"
      <textarea>中用的是th:text ;其中要注意name的值要和Model的属性值一致
          
# 步骤十七：当用户登录返回一个头像
```mysql
  ALTER TABLE `community`.`user` 
ADD COLUMN `avatar_url` VARCHAR(100) NULL AFTER `gmt_modified`;
```
    1.一个新知识点，如何使用Lombok？去官网查询，添加依赖,IDEA还需要下载一个插件
```xml
    <dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<version>1.18.8</version>
		<scope>provided</scope>
	</dependency>
```
    2.将后台获取的头像URL，放在首页亮亮的位置(还未完成)

# 步骤十八：遍历数据库question表，展示在首页
    1.需要根据question表获取展示问题所需要的关键字，还需要根据用户的信息获取用户的头像，这些的实现需要我新建一个
    对象类QuestionDTO，用来实现数据在网络中传输。
    2.为了将数据库表question和user的属性查找出来，我们创建了一个QuestionDTO的类，这两个整合的model对象，我们使用
    service层对其进行处理
    3.将查出来的两个集合对象揉合到一起，使用方法BeanUtils方法，将对象属性（第一个参数）转换称第二个对象属性（其实这里可以用级联查询）
    4.遍历用th:each   th:each="question : ${questions}"；传后台图片用th:src  th:src="${question.user.avatar_url}"
    将文本展示在页面上用th:text = "${model里传过来值.属性}"  进行展示。
    5.mybatis驼峰命名法则在application.properties配置：mybatis.configuration.map-underscore-to-camel-case=true
    
# 步骤十九：thymeleaf+pagehelper实现分页查询
[Pagehelper 官网](https://pagehelper.github.io/docs/howtouse/)
    1.先引入依赖
```xml
<!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper-spring-boot-starter -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.12</version>
</dependency>

```
    2.
    
    
    

    
   


