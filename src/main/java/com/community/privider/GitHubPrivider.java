package com.community.privider;

import com.alibaba.fastjson.JSON;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GitHubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by 舒先亮 on 2019/8/20.
 */
@Service
public class GitHubPrivider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
//        System.out.println("body = " + body);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String accessToken = response.body().string();
//                System.out.println("accessToken = " + accessToken);
//                String[] split = accessToken.split("=");
//                System.out.println("split =[2] " + split[1]);
//                String[] split1 = split[1].split("&");

//                组合
                String accessTokenResult = accessToken.split("=")[1].split("&")[0];
//                System.out.println("split1[0] = " + split1[0]);
                return accessTokenResult;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

    public GitHubUserDTO getUser(String accesstoken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accesstoken)
                .build();

        try (Response response = client.newCall(request).execute()) {
//          response返回的是字符串，我们可以用fastjson将字符串转换为UserDTO
            String string = response.body().string();
            GitHubUserDTO gitHubUserDTO = JSON.parseObject(string, GitHubUserDTO.class);
            System.out.println("gitHubUserDTO = " + gitHubUserDTO);
            return gitHubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
