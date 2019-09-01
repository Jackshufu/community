package com.community.interceptor;

import com.community.mapper.UserMapper;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 舒先亮 on 2019/9/1.
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //        当访问首页的时候，先获取请求中的cookies
        Cookie[] cookies = request.getCookies();
        System.out.println("11111111111133");
        /*if(cookies == null){
            return "index";
        }else*/
        if (cookies != null ) {
//         遍历cookies，查找键为token的session,如果某一个cookie的键等于token，则获取它的值，为session
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
//                这个token是数据库中存的值
                    String token = cookie.getValue();
                    System.out.println(" 数据库中找到和token相等的了 ");
//                通过找到数据库中存的token，再通过它查找user的全部信息
                    User userFindByToken = userMapper.findUserByToken(token);
                    if (userFindByToken != null) {

                        request.getSession().setAttribute("userFindByToken", userFindByToken);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
