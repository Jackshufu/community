package com.community.cache;

import com.community.dto.tagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 舒先亮 on 2019/9/18.
 */
public class tagCache {
    public static List<tagDTO> getTages() {
        List<tagDTO> tagDTOS = new ArrayList<>();
        tagDTO program = new tagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "c#", "swift", "sass", "bash", "ruby", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        tagDTO frameWork = new tagDTO();
        frameWork.setCategoryName("平台框架");
        frameWork.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(frameWork);

        tagDTO service = new tagDTO();
        service.setCategoryName("服务器");
        service.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(service);

        tagDTO db = new tagDTO();
        db.setCategoryName("数据库和缓存");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql", "memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(db);

        tagDTO tools = new tagDTO();
        tools.setCategoryName("开发工具");
        tools.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode", "intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom", "emacs", "textmate", "hg"));
        tagDTOS.add(tools);

        tagDTO system = new tagDTO();
        system.setCategoryName("系统设备");
        system.setTags(Arrays.asList("android", "ios", "chrome", "windows", "iphone", "firefox", "internet-explorer", "safari", "ipad", "opera", "apple-watch"));
        tagDTOS.add(system);

        return tagDTOS;
    }

    public static String filterInValid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<tagDTO> tages = getTages();
//        新方法：通过lambda表达式拿到外层数组的里层数组的值
//        flatMap()将二维数组拍平成一尾数组
        List<String> tagList = tages.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}
