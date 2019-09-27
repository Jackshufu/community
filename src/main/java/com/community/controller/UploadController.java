package com.community.controller;

import com.community.dto.upLoadImgDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 舒先亮 on 2019/9/25.
 */
@Controller
public class UploadController {

    @RequestMapping(value = "upload/image")
    @ResponseBody
    public upLoadImgDTO upLoadImg(){
        upLoadImgDTO upLoadImgDTO = new upLoadImgDTO();
        upLoadImgDTO.setSuccess(1);
        upLoadImgDTO.setUrl("/images/img/wechat.png");
        return upLoadImgDTO;
    }
}
