package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.dto.GitHubUserDTO;
import com.community.privider.GitHubPrivider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 舒先亮 on 2019/8/20.
 */
@Controller
public class CallBackController {

    @Autowired
    private GitHubPrivider gitHubPrivider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    /**
     * github回调我们注册App时填写的redirect-uri，并携带code
     * */
    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
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
        GitHubUserDTO user = gitHubPrivider.getUser(accessToken);
        System.out.println("user = " + user.getId()+" "+user.getName()+" "+user.getBio());
        return "index";
    }
}
