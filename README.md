##启动bootstrap和Springboot组合的项目
访问https://v3.bootcss.com/css/ 下载BootStrap
1.解压，并将其中的css,fonts,js文件夹复制到项目的/resources/static文件夹下
2.resources文件夹下新建一个templates文件夹
3.在thymeleaf官网下载一个index.html的使用模板，放在templates文件夹下；网址https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
第一步：
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

# 第二步：
 在bootstrap官网找一个导航栏样式，放入index.html的body中去

 第三步：写一个controller,注意这里的注解用的是@Controller,我使@RestController的时候，访问浏览器返回的是字符串：index，原因还没查：
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

此时我以为能够返回"index.html",但是由于没有在pom.xml文件中加入thymeleaf的依赖：
             <dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-thymeleaf</artifactId>
    		</dependency>

第四步：看一下github主页的api,主页拉到最下面有一个API接口
    找到:Building a GitHub App or an OAuth App     创建一个GitHub的APP，或者授权APP

第五步：在这之前，由于没有操作过git的提交，所以尝试先把我的workspace“springboot”初始化为管理库repository，
    windows下，先进入springboot文件夹，按住Ctrl+鼠标右键，点击进入git bash
    命令：git init             https://www.liaoxuefeng.com/wiki/896043488029600/896827951938304
    添加：git add .
    提交：git commit -m "add master of workspace"

    设置身份认证：我是谁  git config --global user.email "1610700471@qq.com"
                        git config --global user.name "Jackshufu"

第六步：在新建master的时候，发现其实只要进入到某个project文件夹，就可以使用git init，然后初始化好后就能git add . ,最后再提交
    当git push之前，还需要配置远程仓库的目的地
    1.先在github上创建一个仓库，然后获得一个访问地址，再然后使用命令git remote add origincommunity https://github.com/Jackshufu/community.git
    在本地关联远程仓库
    2.使用 git push -u origincommunity master  上传至github，蛋疼的是需要输入github密码，用户名

## 工具
   [visual-paradigm](https://www.visual-paradigm.com/cn/)
   
第七步：申请github的APP，用于github授权APP登录
        进入github->settings->Developer settings->New Github App
        设置APP name（Community），网址http://community.shu.com
        webhook,我写的是本地关联远程仓库的网址：https://github.com/Jackshufu/community.git
        Owned by: @Jackshufu
        
        App ID: 39020
        
        Client ID: Iv1.c83b663ef84a8086
        
        Client secret: 8c41ac8a3ab868d25fbb36f8977172cbb45ad2b7
    
GitHub授权登录的时序：
1.用户访问我的社区，在社区上处理自己的登录逻辑，然后调用authorize接口去访问github，然后github回调我们注册APP时填写的
redirect-uri并携带code，当社区接收到这个信息后，会再次调用githubde access_token接口，并携带code，这步为了获取github
的token，然后当github验证成功后，返回access_token给社区，社区可以通过access_token来调用GitHub的userAPI，github返回user信息
此时可以更新登录状态，把获取到的user信息保存到数据库，最后返回用户登录成功

步骤八；添加fastjson依赖
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.59</version>
</dependency>

添加okhttp依赖
        <dependency>
			<groupId>com.squareup.okhttp3</groupId>
			<artifactId>okhttp</artifactId>
			<version>3.14.1</version>
		</dependency>
		
json格式的数据转换成对象

步骤九：当github通过验证并发放认证令牌后，社区如何通过access_token发起请求访问userAPI拿到user信息？
首先在github的Personal access tokens新建一个私人访问token，在官网API的描述下，模拟一下
 Google打开匿名窗口：win   Ctrl+shift+N
 拼接如下：https://api.github.com/user?access_token=f47c9b74247c9342f4183c0543c704733ab5441e
 能够看到用户相关信息
    其中我可以关注id ,name , bio 
    
 
 
 
   


