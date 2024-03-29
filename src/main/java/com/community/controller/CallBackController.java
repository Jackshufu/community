package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.dto.GitHubUserDTO;
import com.community.model.User;
import com.community.privider.GitHubPrivider;
import com.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by 舒先亮 on 2019/8/20.
 */
@Controller
@Slf4j
public class CallBackController {

    @Autowired
    private GitHubPrivider gitHubPrivider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserService userService;


    /**
     * github回调我们注册App时填写的redirect-uri，并携带code
     */
    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse reaponse) {
        /**
         * 社区收到这个消息之后，再次调用github的access_token接口，并携带code
         * */
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubPrivider.getAccessToken(accessTokenDTO);
        System.out.println("accessToken = " + accessToken);
        GitHubUserDTO gitHubUser = gitHubPrivider.getUser(accessToken);
        System.out.println("gitHubUser = " + gitHubUser.getId() + " " + gitHubUser.getName() + " " + gitHubUser.getBio() + gitHubUser.getAvatarUrl());
        if (gitHubUser != null && gitHubUser.getId() != null) {
            User user = new User();
            user.setAccountId(gitHubUser.getId().toString());
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
//            String format = simpleDateFormat.format(System.currentTimeMillis());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userService.addOrUpdateUser(user);
//            登陆成功，写cookie和session
//            request.getSession().setAttribute("gitHubUser",gitHubUser);
            reaponse.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            log.error("callBack get github error,{}",gitHubUser);
//            登录失败，重新登录
            return "redirect:/";
        }
//        return "index";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
//        退出服务端session，删除cookie
        request.getSession().removeAttribute("userFindByToken");
        Cookie token = new Cookie("token", "");
        token.setMaxAge(0);
        response.addCookie(token);

        return "redirect:/";
    }
}
