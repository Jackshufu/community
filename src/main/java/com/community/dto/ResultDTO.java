package com.community.dto;

import com.community.exception.CustomErrorCodeEnumImp;
import com.community.exception.CustomException;
import lombok.Data;

/**
 * Created by 舒先亮 on 2019/9/9.
 */
@Data
public class ResultDTO {
    //    code是像code码一样，用来告诉前端当前是此状态码的状态
    private Integer code;
    //    message是用来提示的
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;

    }

    public static ResultDTO errorOf(CustomErrorCodeEnumImp noLogin) {
        return errorOf(noLogin.getCode(), noLogin.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomException ex) {
//        ResultDTO resultDTO = new ResultDTO();
//        resultDTO.setCode(ex.getCode());
//        resultDTO.setMessage(ex.getMessage());
        return errorOf(ex.getCode(),ex.getMessage());
    }
}
